import React, { useEffect, useState } from 'react';
import toast from 'react-hot-toast';
import { IoIosArrowDown, IoIosArrowUp } from 'react-icons/io';
import { store, useDispatch, useSelector } from '../..';
import { ArticleRating, requestRateArticle } from '../../api/api';
import { selectUser } from '../../selectors/userSelectors';
import { newsArticleDownVote, newsArticleUpVote } from '../../slices/newsSlice';
import { NewsArticle } from '../../types/api/newsAPI.org';
import { remToPixel, stopEvent } from '../../utility/helper';
import { HomeArticleVotesWrapper as UpDownVoterWrapper } from './UpDownVoter.style';

type UpDownVoterParams = {
  article: NewsArticle;
};

export function UpDownVoter({ article }: UpDownVoterParams): JSX.Element {
  const dispatch = useDispatch();
  const user = useSelector(selectUser);
  const [likeAdjust, setLikeAdjust] = useState(0);

  const articlesLikes = article.likes.length - article.dislikes.length;

  useEffect(() => {
    const unsubscribe = store.subscribe(() => {
      const newArticle = store
        .getState()
        .news.content.articles.find((newArticle) => article._id === newArticle._id);
      if (!newArticle || newArticle.likes.length - newArticle.dislikes.length === articlesLikes)
        return;
      setLikeAdjust(0);
    });

    return () => {
      unsubscribe();
    };
  }, []);

  async function upVote(e: React.MouseEvent) {
    stopEvent(e);
    if (!article.likes.find((userId) => userId === user.id)) {
      if (likeAdjust > 0) {
        setLikeAdjust(0);
      } else {
        setLikeAdjust(1);
      }
    }

    try {
      await requestRateArticle(article._id, ArticleRating.up);
      dispatch(newsArticleUpVote({ articleId: article._id, userId: user.id }));
    } catch (error) {
      toast.error('' + error);
    }
  }

  async function downVote(e: React.MouseEvent) {
    stopEvent(e);
    if (!article.dislikes.find((userId) => userId === user.id)) {
      if (likeAdjust < 0) {
        setLikeAdjust(0);
      } else {
        setLikeAdjust(-1);
      }
    }

    try {
      await requestRateArticle(article._id, ArticleRating.down);
      dispatch(newsArticleDownVote({ articleId: article._id, userId: user.id }));
    } catch (error) {
      toast.error('' + error);
    }
  }

  return (
    <UpDownVoterWrapper>
      <IoIosArrowUp size={remToPixel(2)} onClick={upVote} />
      {articlesLikes + likeAdjust}
      <IoIosArrowDown size={remToPixel(2)} onClick={downVote} />
    </UpDownVoterWrapper>
  );
}
