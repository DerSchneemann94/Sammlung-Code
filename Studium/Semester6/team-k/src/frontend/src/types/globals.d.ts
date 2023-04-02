declare global {
  namespace NodeJS {
    interface ProcessEnv {
      REACT_APP_TARGET: 'development' | 'production';
      REACT_APP_DUMMY_USER?: 'true';
      REACT_APP_BACKEND_URL: string;
      REACT_APP_MOBILE?: 'true';
    }
  }
}

export {};
