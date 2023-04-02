import React, { useRef } from 'react';
import {
  ModalArticleCommentsWrapper,
  ModalArticleInputWrapper,
  ModalArticleMessageInput,
  ModalArticleMessageWrapper,
  ModalArticleVoteButton,
} from '../partials/ModalArticle.style';
import { commentsAddComment } from '../../slices/commentsSlice';
import { useDispatch, useSelector } from 'react-redux';
import { selectUser } from '../../selectors/userSelectors';
import { selectComments } from '../../selectors/commentsSelectors';
import { selectNews } from '../../selectors/newsSelectors';
import { selectArticleModal } from '../../selectors/modalSelectors';
import { CommentCard } from './CommentCard';
import { getNextId } from '../../utility/helper';
import toast from 'react-hot-toast';
import { requestAddComment } from '../../api/api';

function Comments(): JSX.Element | null {
  const dispatch = useDispatch();
  const articleId = useSelector(selectArticleModal);
  const article = useSelector(selectNews).articles.find((article) => article._id === articleId);
  const comments = useSelector(selectComments);

  const user = useSelector(selectUser);
  const messageInputRef = useRef<HTMLInputElement>(null);

  async function sendMessage() {
    if (
      !messageInputRef.current?.value ||
      messageInputRef.current.value.trim().length <= 0 ||
      !article ||
      !articleId
    ) {
      toast.error('Enter a valid comment.');
      return;
    }
    try {
      const comment = await requestAddComment({
        articleId: article._id,
        userId: user.id,
        createdAt: new Date().toISOString(),
        comment: messageInputRef.current.value,
      });
      dispatch(
        commentsAddComment({
          articleId: article._id,
          comment: {
            _id: comment.id,
            user: { username: user.username, image: user.image },
            comment: messageInputRef.current.value,
            parentId: undefined,
            articleId,
            upvotes: [],
            downvotes: [],
            children: [],
          },
        }),
      );
    } catch (error) {
      toast.error('' + error);
    }

    messageInputRef.current.value = '';
  }

  if (!articleId) return null;
  return (
    <ModalArticleMessageWrapper>
      <ModalArticleInputWrapper onSubmit={sendMessage}>
        <ModalArticleMessageInput type="text" ref={messageInputRef} />
        <ModalArticleVoteButton type="submit">send</ModalArticleVoteButton>
      </ModalArticleInputWrapper>
      <ModalArticleCommentsWrapper>
        {(comments[articleId] ? comments[articleId].structuredComments : []).map((comment) => (
          <CommentCard key={getNextId()} comment={comment} parentCommentId={comment._id} />
        ))}
      </ModalArticleCommentsWrapper>
    </ModalArticleMessageWrapper>
  );
}

export default Comments;
