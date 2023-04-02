import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { Redirect } from 'react-router';
import { history, useSelector } from '../..';
import { selectUser } from '../../selectors/userSelectors';
import { userLogout } from '../../slices/userSlice';
import { UserProfileImage } from '../constants/globalStyles';
import { RoutePaths } from '../constants/routes';
import useToggleState from '../hooks/useToggleState';
import { modalArticleSet } from '../../slices/modalSlice';
import {
  UserPage,
  UserProfileImageEditIcon,
  UserProfileImageWrapper,
  UserArticleImage,
  UserArticleGridWrapper,
  UserArticle,
  UserArticleImageWrapper,
  LogoutButton,
} from './User.style';
import { fetchNews, fetchUserComments } from '../../api/api';
import { newsSet, newsSetUserNews } from '../../slices/newsSlice';
import { selectNews } from '../../selectors/newsSelectors';
import { NewsOutletAndDate, NewsOutletName, NewsTitle } from './Home.style';
import { stopEvent } from '../../utility/helper';
import { useIncreaseOnScrollEnd } from '../hooks/useIncreaseOnScrollEnd';
import useAsyncEffect from '../hooks/useAsyncEffect';
import { Comment } from '../../slices/commentsSlice';
import { NewsArticle } from '../../types/api/newsAPI.org';

export default function User(): JSX.Element {
  const dispatch = useDispatch();
  const news = useSelector(selectNews);
  const [hoverImage, toggleHoverImage] = useToggleState(false);
  const { username, token, image, id } = useSelector(selectUser);
  const [maxArticles] = useIncreaseOnScrollEnd(20);
  const [userComments, setUserComments] = useState([]);

  useAsyncEffect(
    async (stopped) => {
      if (news.totalResults <= 0) {
        const fetchedNews = await fetchNews();
        dispatch(newsSet(fetchedNews));
      }
      if (!id) return;
      const comments = await fetchUserComments(id);
      if (stopped()) return;
      dispatch(newsSetUserNews(getNewsArticleFromComments(comments)));
    },
    [news.totalResults, dispatch],
  );

  function logout() {
    dispatch(userLogout());
  }

  function getNewsArticleFromComments(comments: Array<Comment>): Array<NewsArticle> {
    return news.articles.filter((article: NewsArticle) => {
      return comments.some((comment: Comment) => {
        return comment.articleId === article._id;
      });
    });
  }

  function goToUserEditPage() {
    history.push(RoutePaths.UserEdit);
  }

  function sortArticles() {
    return [...news.userNews].sort((lhs, rhs) => {
      return (rhs.articleImageUrl ? 1 : 0) - (lhs.articleImageUrl ? 1 : 0);
    });
  }

  if (token.length <= 0) {
    return <Redirect to={RoutePaths.Login} />;
  }

  return (
    <div>
      <UserPage>
        <UserProfileImageWrapper>
          <UserProfileImage
            src={image}
            onClick={goToUserEditPage}
            onMouseEnter={toggleHoverImage}
            onMouseLeave={toggleHoverImage}
          />
          {!hoverImage ? null : <UserProfileImageEditIcon />}
        </UserProfileImageWrapper>
        <div>{username}</div>
        <LogoutButton onClick={logout}>Logout</LogoutButton>
      </UserPage>
      <NewsTitle style={{ padding: '2rem' }}>Commented Articles</NewsTitle>
      <UserArticleGridWrapper>
        {sortArticles().map((article, index) => {
          if (index >= maxArticles) return null;
          function viewArticle() {
            dispatch(modalArticleSet(article._id));
          }
          return (
            <UserArticle key={article._id} onClick={viewArticle}>
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
              <UserArticleImageWrapper>
                <UserArticleImage src={article.articleImageUrl} />
              </UserArticleImageWrapper>
            </UserArticle>
          );
        })}
      </UserArticleGridWrapper>
    </div>
  );
}
