import React, { useEffect } from 'react';
import { Toaster } from 'react-hot-toast';
import { Redirect, Route, Switch } from 'react-router';
import { useDispatch } from '../..';
import { userLogin } from '../../slices/userSlice';
import { RoutePaths } from '../constants/routes';
import { ModalArticle } from '../partials/ModalArticle';
import { AppWrapper } from './App.style';
import Home from './Home';
import Login from './Login';
import { HomeRoutes } from './pageElements/HomeRoutes';
import Register from './Register';
import User from './User';
import { UserEdit } from './UserEdit';

function App(): JSX.Element {
  const dispatch = useDispatch();

  useEffect(() => {
    if (process.env.REACT_APP_DUMMY_USER === 'true') {
      dispatch(
        userLogin({
          email: 'thomas@gmx.de',
          id: '1234',
          image:
            'https://stickershop.line-scdn.net/stickershop/v1/product/13815473/LINEStorePC/main.png;compress=true',
          username: 'Thomas Gottschalk',
          token: 'abcdefghijklmnopqrstuvwxyz',
        }),
      );
    }
  }, [dispatch]);

  return (
    <AppWrapper>
      <HomeRoutes />
      <Switch>
        <Route path={RoutePaths.Home} component={Home} />
        <Route path={RoutePaths.Login} component={Login} />
        <Route path={RoutePaths.Register} component={Register} />
        <Route path={RoutePaths.UserEdit} component={UserEdit} />
        <Route path={RoutePaths.User} component={User} />
        <Redirect to={RoutePaths.Home} />
      </Switch>
      <ModalArticle />
      <Toaster position="bottom-center" reverseOrder={true} />
    </AppWrapper>
  );
}

export default App;
