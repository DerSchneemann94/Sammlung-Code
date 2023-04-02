import React, { useEffect } from 'react';
import { history, useDispatch, useSelector } from '../..';
import { fetchNews } from '../../api/api';
import { selectNews } from '../../selectors/newsSelectors';
import { newsSet } from '../../slices/newsSlice';
import {
  HomeArticleContent,
  HomeArticleImage,
  HomeArticleWrapper,
  NewsOutletAndDate,
  NewsTitle,
  HomeArticleContentWrapper,
  NewsOutletName,
} from './Home.style';
import { modalArticleSet } from '../../slices/modalSlice';
import { stopEvent } from '../../utility/helper';
import { UpDownVoter } from '../elements/UpDownVoter';
import { RoutePaths } from '../constants/routes';
import { useIncreaseOnScrollEnd } from '../hooks/useIncreaseOnScrollEnd';

export default function Home(): JSX.Element {
  const dispatch = useDispatch();
  const news = useSelector(selectNews);
  const [maxArticles] = useIncreaseOnScrollEnd(20);

  useEffect(() => {
    if (news.totalResults > 0) return;
    fetchNews()
      .then((news) => {
        dispatch(newsSet(news));
      })
      .catch(() => {
        history.push(RoutePaths.User);
      });
  }, [news.totalResults, dispatch]);

  return (
    <div>
      {news.filteredArticles.map((article, index) => {
        if (index >= maxArticles) return null;
        function openArticleModal() {
          dispatch(modalArticleSet(article._id));
        }

        return (
          <div style={{ display: 'flex', justifyContent: 'center' }} key={article._id}>
            <HomeArticleWrapper onClick={openArticleModal}>
              <HomeArticleContentWrapper>
                <UpDownVoter article={article} />
                <HomeArticleContent>
                  <NewsTitle>{article.title}</NewsTitle>
                  <NewsOutletAndDate
                    href={article.articleUrl}
                    target="_blank"
                    rel="noopener"
                    onClick={stopEvent}
                  >
                    <NewsOutletName>{article.newsOutletName} </NewsOutletName>
                    &nbsp;
                    {article.publishedAtDate.toLocaleDateString('de', {
                      day: '2-digit',
                      month: '2-digit',
                      year: 'numeric',
                    })}
                  </NewsOutletAndDate>
                  <div>{article.description}</div>
                </HomeArticleContent>
              </HomeArticleContentWrapper>
              <HomeArticleImage src={article.articleImageUrl} alt="" />
            </HomeArticleWrapper>
          </div>
        );
      })}
    </div>
  );
}
