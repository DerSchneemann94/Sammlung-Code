import { store } from '..';
import news from '../constants/newsAPI.orgTopHeadlinesGermany.json';
import { Comment, CommentAPI } from '../slices/commentsSlice';
import { cleanNewsAPIORG } from '../types/api/cleanNewsAPI.org';
import { H4PNewsAPIORG, News } from '../types/api/newsAPI.org';
export type UserInfo = {
  username: string;
  image?: string;
};

export type UserData = UserInfo & {
  id: string;
  email: string;
};

type LoginData = {
  access_token: string;
} & UserData;

function getUserToken(): string {
  return store.getState().user.token;
}

function createBackendCall(endpoint: string): string {
  return process.env.REACT_APP_BACKEND_URL + endpoint;
}

export async function fetchNews(): Promise<News> {
  if (process.env.REACT_APP_TARGET === 'development') {
    return cleanNewsAPIORG(news.articles as H4PNewsAPIORG);
  }
  const headers = {
    Authorization: `Bearer ${getUserToken()}`,
    accept: 'application/json',
  };
  const response = await fetch(createBackendCall('/articles'), { method: 'GET', headers });
  if (response.status !== 200) {
    throw new Error(`Could not fetch News: ${response.statusText}`);
  }
  return cleanNewsAPIORG(await response.json());
}

export async function requestLogin(mail: string, password: string): Promise<LoginData> {
  if (process.env.REACT_APP_DUMMY_USER === 'true') {
    return { access_token: 'DUMMY_TOKEN', username: 'nö', id: '1', email: 'dummy@mail.de' };
  }

  const body = new window.URLSearchParams({ email: mail, password });
  const headers = {
    'content-Type': 'application/x-www-form-urlencoded',
    accept: 'application/json',
  };
  const response = (await fetch(createBackendCall('/authentication/signin'), {
    body,
    method: 'POST',
    headers,
  })) as Response;

  if (response.status !== 201) {
    const resMessage = ((await response.json()) as { message: Array<string> }).message;
    throw new Error(resMessage.join('\n'));
  }
  return await response.json();
}

export async function requestRegister(
  mail: string,
  password: string,
  username: string,
): Promise<LoginData> {
  if (process.env.REACT_APP_DUMMY_USER === 'true') {
    return {
      access_token: '',
      email: 'dummy@mail.de',
      username: 'dummy',
      id: 'noId',
    };
  }

  const body = new window.URLSearchParams({ email: mail, password, username });
  const headers = {
    'content-Type': 'application/x-www-form-urlencoded',
    accept: 'application/json',
  };
  const response = await fetch(createBackendCall('/authentication/signup'), {
    body,
    method: 'POST',
    headers,
  });

  if (response.status !== 201) {
    const resMessage = ((await response.json()) as { message: Array<string> }).message;
    throw new Error(resMessage.join('\n'));
  }
  return await response.json();
}

export async function requestUserEdit(image: string): Promise<void> {
  const headers = {
    Authorization: `Bearer ${getUserToken()}`,
    accept: 'application/json',
  };
  const body = new window.URLSearchParams({ image });
  const response = await fetch(createBackendCall('/user/'), {
    method: 'PUT',
    headers,
    body,
  });
  if (response.status !== 200) {
    const resMessage = ((await response.json()) as { message: string }).message;
    throw new Error(resMessage);
  }
}

export async function requestUserInfo(userId: string): Promise<UserInfo> {
  const headers = {
    accept: 'application/json',
  };

  const response = await fetch(createBackendCall(`/user/${userId}`), {
    method: 'GET',
    headers,
  });
  if (response.status !== 200) {
    const resMessage = ((await response.json()) as { message: Array<string> }).message;
    throw new Error(resMessage.join('\n'));
  }
  return await response.json();
}

export const enum ArticleRating {
  up = 'UP',
  down = 'DOWN',
}

export const enum CommentChoice {
  up = 'UP',
  neutral = 'NEUTRAL',
  down = 'DOWN',
}

export async function requestRateArticle(articleId: string, rating: ArticleRating) {
  const headers = {
    Authorization: `Bearer ${getUserToken()}`,
    accept: 'application/json',
  };
  const body = new window.URLSearchParams({ choice: rating });
  const response = await fetch(createBackendCall(`/articles/${articleId}/rate`), {
    method: 'POST',
    headers,
    body,
  });
  if (response.status !== 201) {
    const resMessage = ((await response.json()) as { message: Array<string> }).message;
    if (response.status === 401) throw new Error('You have to login');
    throw new Error(resMessage.join('\n'));
  }
  return await response.json();
}

type CommentPost = {
  articleId: string;
  userId: string;
  comment: string;
  parentId?: string;
  createdAt: any;
};

export async function requestAddComment(comment: CommentPost): Promise<{ id: string }> {
  const headers = {
    Authorization: `Bearer ${getUserToken()}`,
    accept: 'application/json',
  };
  const body = new window.URLSearchParams({ ...comment });

  const response = await fetch(createBackendCall(`/comments/article/${comment.articleId}`), {
    method: 'POST',
    headers,
    body,
  });
  if (response.status !== 201) {
    const resMessage = ((await response.json()) as { message: Array<string> }).message;
    if (response.status === 401) throw new Error('You have to login');
    throw new Error(resMessage.join('\n'));
  }
  return await response.json();
}

export async function fetchArticleComments(articleId: string): Promise<Array<CommentAPI>> {
  const headers = {
    accept: 'application/json',
  };
  const response = await fetch(createBackendCall(`/comments/article/${articleId}`), {
    method: 'GET',
    headers,
  });
  if (response.status !== 200) {
    throw new Error(`Could not fetch Comments on Article: ${response.statusText}`);
  }
  const json = await response.json();
  console.log('fetch Comments', json);
  return json;
}

export async function fetchUserComments(userId: string): Promise<Array<Comment>> {
  const headers = {
    accept: 'application/json',
  };
  const response = await fetch(createBackendCall(`/comments/user/${userId}`), {
    method: 'GET',
    headers,
  });
  if (response.status !== 200) {
    throw new Error(`Could not fetch Comments from User: ${response.statusText}`);
  }
  const json = await response.json();
  console.log('fetch Comments', json);
  return json;
}

export async function requestRateComment(
  commentId: string,
  rating: CommentChoice,
): Promise<Comment> {
  const headers = {
    Authorization: `Bearer ${getUserToken()}`,
    accept: 'application/json',
  };
  const body = new window.URLSearchParams({ rating });

  const response = await fetch(createBackendCall(`/comments/rate/${commentId}`), {
    method: 'PUT',
    headers,
    body,
  });

  if (response.status !== 200) {
    throw new Error(`Could not rate Comment: ${response.statusText}`);
  }
  const json = await response.json();
  console.log('rated Comment', json);
  return json;
}
