export type H4PNewsAPIORGArticle = {
  _id: string;
  author: string;
  content: string;
  description: string;
  publishedAt: string;
  source: { id: null | string; name: string };
  title: string;
  url: string;
  urlToImage: string;
  likes: Array<string>;
  dislikes: Array<string>;
};

export type H4PNewsAPIORG = Array<H4PNewsAPIORGArticle>;

export type NewsArticle = {
  _id: string;
  articleImageUrl: string;
  articleUrl: string;
  author: string;
  content: string;
  description: string;
  newsOutletId: null | string;
  newsOutletName: string;
  publishedAtDate: Date;
  title: string;
  likes: Array<string>;
  dislikes: Array<string>;
  searchableContent: string;
};

export type News = {
  totalResults: number;
  articles: Array<NewsArticle>;
};
