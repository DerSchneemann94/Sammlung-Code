import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { UserData } from '../api/api';
import { DEFAULT_USER_IMAGE } from '../components/constants/globalConstants';
import { LocalStorage } from '../components/constants/localStorage';

export type UserState = {
  email: string;
  id: string;
  image: string;
  username: string;
  token: string;
};

const initialState: UserState = {
  email: '',
  id: '',
  image: DEFAULT_USER_IMAGE,
  username: '',
  token: '',
};

export const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    userLogin(state, action: PayloadAction<UserState>) {
      return action.payload;
    },
    userLogout() {
      localStorage.removeItem(LocalStorage.token);
      return initialState;
    },
    userSetToken(state, action: PayloadAction<string>) {
      state.token = action.payload;
    },
    userSetImage(state, action: PayloadAction<string>) {
      state.image = action.payload;
    },
    userSetData(state, action: PayloadAction<UserData>) {
      return { ...state, ...action.payload, image: action.payload.image ?? DEFAULT_USER_IMAGE };
    },
  },
});

export const { userLogin, userLogout, userSetToken, userSetImage, userSetData } = userSlice.actions;

export default userSlice.reducer;
