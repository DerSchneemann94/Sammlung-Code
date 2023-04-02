import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { News, NewsArticle } from '../types/api/newsAPI.org';

export type NewsState = {
  content: News & { filteredArticles: Array<NewsArticle>; userNews: Array<NewsArticle> };
};

const initialState: NewsState = {
  content: {
    totalResults: 0,
    articles: [],
    filteredArticles: [],
    userNews: [],
  },
};

export const newsSlice = createSlice({
  name: 'news',
  initialState,
  reducers: {
    newsSet(state, action: PayloadAction<News>) {
      state.content = {
        ...action.payload,
        filteredArticles: action.payload.articles,
        userNews: action.payload.articles,
      };
    },
    newsArticleUpVote(state, action: PayloadAction<{ articleId: string; userId: string }>) {
      const index = state.content.articles.findIndex(
        (article) => article._id === action.payload.articleId,
      );
      if (index >= 0) {
        const likedIndex = state.content.articles[index].likes.findIndex(
          (like) => like === action.payload.userId,
        );
        if (likedIndex >= 0) {
          //remove like
          state.content.articles[index].likes.splice(likedIndex, 1);
          return;
        }
        const dislikedIndex = state.content.articles[index].dislikes.findIndex(
          (dislike) => dislike === action.payload.userId,
        );
        if (dislikedIndex >= 0) {
          state.content.articles[index].dislikes.splice(dislikedIndex, 1);
        }
        state.content.articles[index].likes.push(action.payload.userId);
      }
    },
    newsArticleDownVote(state, action: PayloadAction<{ articleId: string; userId: string }>) {
      const index = state.content.articles.findIndex(
        (article) => article._id === action.payload.articleId,
      );
      if (index >= 0) {
        const dislikedIndex = state.content.articles[index].dislikes.findIndex(
          (dislike) => dislike === action.payload.userId,
        );
        if (dislikedIndex >= 0) {
          //remove Dislike
          state.content.articles[index].dislikes.splice(dislikedIndex, 1);
          return;
        }
        const likedIndex = state.content.articles[index].likes.findIndex(
          (like) => like === action.payload.userId,
        );
        if (likedIndex >= 0) {
          state.content.articles[index].likes.splice(likedIndex, 1);
        }
        state.content.articles[index].dislikes.push(action.payload.userId);
      }
    },
    newsSetFilteredArticles(state, action: PayloadAction<Array<NewsArticle>>) {
      state.content.filteredArticles = action.payload;
    },
    newsSetUserNews(state, action: PayloadAction<Array<NewsArticle>>) {
      state.content.userNews = action.payload;
    },
  },
});

export const {
  newsSet,
  newsArticleUpVote,
  newsArticleDownVote,
  newsSetFilteredArticles,
  newsSetUserNews,
} = newsSlice.actions;

export default newsSlice.reducer;
