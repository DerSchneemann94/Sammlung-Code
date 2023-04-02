import React, { useRef } from 'react';
import { Link } from 'react-router-dom';
import { history } from '../..';
import { requestRegister } from '../../api/api';
import { loginUser } from '../../utility/helper';
import { DEFAULT_USER_IMAGE } from '../constants/globalConstants';
import { BoldTitle } from '../constants/globalStyles';
import { RoutePaths } from '../constants/routes';
import {
  LoginRegisterButton,
  LoginRegisterContentWrapper,
  LoginRegisterInput,
  LoginRegisterPage,
} from './Login.Register.style';
import toast from 'react-hot-toast';

export default function Register(): JSX.Element {
  const usernameRef = useRef<HTMLInputElement>(null);
  const mailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const passwordConfirmRef = useRef<HTMLInputElement>(null);

  async function register() {
    if (
      !usernameRef.current ||
      !mailRef.current ||
      !passwordRef.current ||
      !passwordConfirmRef.current ||
      passwordRef.current.value !== passwordConfirmRef.current.value
    )
      return;
    try {
      const response = await requestRegister(
        mailRef.current.value,
        passwordRef.current.value,
        usernameRef.current.value,
      );
      loginUser({
        username: response.username,
        email: mailRef.current.value,
        id: response.id,
        image: response.image || DEFAULT_USER_IMAGE,
        token: response.access_token,
      });
      history.push(RoutePaths.User);
    } catch (err) {
      console.error('Registration failed', err);
      if (!mailRef.current) return;
      mailRef.current.value = '';
      toast.error('' + err);
    }
  }
  return (
    <LoginRegisterPage>
      <LoginRegisterContentWrapper>
        <BoldTitle>REGISTER</BoldTitle>
        <LoginRegisterInput placeholder="Name" ref={usernameRef} />
        <LoginRegisterInput placeholder="Email" ref={mailRef} />
        <LoginRegisterInput type="password" placeholder="Password" ref={passwordRef} />
        <LoginRegisterInput
          type="password"
          placeholder="Confirm Password"
          ref={passwordConfirmRef}
        />
        <LoginRegisterButton onClick={register}>REGISTER</LoginRegisterButton>
        <Link to={RoutePaths.Login}>Login</Link>
      </LoginRegisterContentWrapper>
    </LoginRegisterPage>
  );
}
