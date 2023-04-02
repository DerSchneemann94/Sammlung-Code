import { H4PNewsAPIORG, H4PNewsAPIORGArticle, News, NewsArticle } from './newsAPI.org';

function createSearchableContent(article: H4PNewsAPIORGArticle) {
  return [
    article.source.name,
    article.author,
    article.title,
    article.description,
    article.url,
    article.content,
    new Date(article.publishedAt).toLocaleDateString('de', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    }),
  ]
    .join('\n')
    .toLowerCase();
}

function cleanNewsAPIORGArticle(article: H4PNewsAPIORGArticle): NewsArticle {
  const ret = {
    _id: article._id,
    newsOutletId: article.source.id,
    newsOutletName: article.source.name,
    author: article.author,
    title: article.title,
    description: article.description,
    articleUrl: article.url,
    articleImageUrl: article.urlToImage,
    publishedAtDate: new Date(article.publishedAt),
    content: article.content,
    likes: article.likes,
    dislikes: article.dislikes,
    searchableContent: createSearchableContent(article),
  };
  return ret;
}

export function cleanNewsAPIORG(news: H4PNewsAPIORG): News {
  return {
    totalResults: news.length,
    articles: news
      .map((article) => {
        if (Object.keys(article).length < 10) {
          console.error('DOMME fix die Articles in der DB!');
          return undefined;
        }
        return cleanNewsAPIORGArticle(article);
      })
      .filter((article) => {
        return article !== undefined;
      }) as Array<NewsArticle>,
  };
}
