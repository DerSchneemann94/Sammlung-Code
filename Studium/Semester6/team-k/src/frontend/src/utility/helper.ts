import { store } from '..';
import { LocalStorage } from '../components/constants/localStorage';
import { userLogin, UserState } from '../slices/userSlice';
import { Buffer } from 'buffer';

window.Buffer = Buffer;

export function stopEvent(e: React.MouseEvent) {
  e.stopPropagation();
}

export function remToPixel(rem: number): number {
  return parseInt(getComputedStyle(document.documentElement).fontSize) * rem;
}

export function loginUser(loginData: UserState) {
  localStorage.setItem(LocalStorage.token, loginData.token);
  store.dispatch(userLogin(loginData));
}

export function bearerJWTTokenToJSON(bearerJWTToken: string): JSON {
  const base64Url = bearerJWTToken.split('.')[1];
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    Buffer.from(base64, 'base64')
      .toString('latin1')
      .split('')
      .map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join(''),
  );
  return JSON.parse(jsonPayload);
}

let nextId = 0;
export function getNextId(): string {
  return `${nextId++}`;
}
