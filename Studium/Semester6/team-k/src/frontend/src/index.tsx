import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/pages/App';
import { createHashHistory } from 'history';
import { configureStore } from '@reduxjs/toolkit';
import { ConnectedRouter, connectRouter, routerMiddleware } from 'connected-react-router';
import {
  Provider as ReduxProvider,
  useDispatch as reduxUseDispatch,
  useSelector as reduxUseSelector,
} from 'react-redux';
import { RoutePaths } from './components/constants/routes';
import newsSlice from './slices/newsSlice';
import modalArticleSlice from './slices/modalSlice';
import userSlice, { userSetData, userSetToken } from './slices/userSlice';
import commentsSlice from './slices/commentsSlice';
import { LocalStorage } from './components/constants/localStorage';
import { requestUserInfo } from './api/api';
import { bearerJWTTokenToJSON } from './utility/helper';

import * as rdd from 'react-device-detect';
import toast from 'react-hot-toast';

if (process.env.REACT_APP_MOBILE === 'true') {
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  //@ts-expect-error
  rdd.isMobile = true;
}

export const history = createHashHistory();

export const store = configureStore({
  reducer: {
    comments: commentsSlice,
    news: newsSlice,
    modal: modalArticleSlice,
    router: connectRouter(history),
    user: userSlice,
  },
  middleware: [routerMiddleware(history)],
});

{
  const userToken = localStorage.getItem(LocalStorage.token);
  if (userToken) {
    (async () => {
      store.dispatch(userSetToken(userToken));
      const tokenPayload = bearerJWTTokenToJSON(userToken) as unknown as {
        userId: string;
        email: string;
      };
      try {
        const response = await requestUserInfo(tokenPayload.userId);
        store.dispatch(
          userSetData({ ...response, id: tokenPayload.userId, email: tokenPayload.email }),
        );
      } catch (error) {
        toast.error('' + error);
      }
    })();
  }
}

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useSelector: <T>(selector: (state: RootState) => T) => T = reduxUseSelector;
export const useDispatch: () => AppDispatch = reduxUseDispatch;
export const Provider = ReduxProvider;

export function historyGoBack() {
  if (history.location.pathname === RoutePaths.Home) return;
  history.goBack();
}

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <ConnectedRouter history={history}>
        <App />
      </ConnectedRouter>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root'),
);
