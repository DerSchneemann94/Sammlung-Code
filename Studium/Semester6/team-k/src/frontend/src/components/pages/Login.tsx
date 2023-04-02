import React, { useRef } from 'react';
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router';
import { useSelector } from '../..';
import { selectUser } from '../../selectors/userSelectors';
import { RoutePaths } from '../constants/routes';
import {
  LoginRegisterButton,
  LoginRegisterContentWrapper,
  LoginRegisterInput,
  LoginRegisterPage,
} from './Login.Register.style';
import { requestLogin } from '../../api/api';
import { BoldTitle } from '../constants/globalStyles';
import { DEFAULT_USER_IMAGE } from '../constants/globalConstants';
import { loginUser } from '../../utility/helper';
import toast from 'react-hot-toast';

export default function Login(): JSX.Element {
  const { token } = useSelector(selectUser);
  const mailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

  if (token.length > 0) {
    return <Redirect to={RoutePaths.User} />;
  }

  async function login() {
    if (!mailRef.current || !passwordRef.current) return;
    try {
      const response = await requestLogin(mailRef.current.value, passwordRef.current.value);
      if (!mailRef.current || !passwordRef.current) return;
      loginUser({
        token: response.access_token,
        email: mailRef.current.value,
        id: response.id,
        image: response.image ?? DEFAULT_USER_IMAGE,
        username: response.username,
      });
    } catch (err) {
      console.error('Login failed', err);
      if (!passwordRef.current) return;
      passwordRef.current.value = '';

      toast.error('' + err);
    }
  }

  return (
    <LoginRegisterPage>
      <LoginRegisterContentWrapper>
        <BoldTitle>WELCOME BACK</BoldTitle>
        <LoginRegisterInput ref={mailRef} placeholder="Email" />
        <LoginRegisterInput ref={passwordRef} placeholder="Password" type="password" />
        <LoginRegisterButton onClick={login}>LOGIN</LoginRegisterButton>
        <Link to={RoutePaths.Register}>Register</Link>
      </LoginRegisterContentWrapper>
    </LoginRegisterPage>
  );
}
