import React, { useRef, useState } from 'react';
import toast from 'react-hot-toast';
import { useDispatch, useSelector } from 'react-redux';
import { CommentChoice, requestAddComment, requestRateComment } from '../../api/api';
import { selectArticleModal } from '../../selectors/modalSelectors';
import { selectNews } from '../../selectors/newsSelectors';
import { selectUser } from '../../selectors/userSelectors';
import { Comment, commentsAddComment, commentUpVote } from '../../slices/commentsSlice';
import { getNextId } from '../../utility/helper';
import {
  ModalArticleCancelButton,
  ModalArticleInputWrapper,
  ModalArticleMessageInput,
  ModalArticleVoteButton,
} from '../partials/ModalArticle.style';
import {
  AddNewCommentChildWrapper,
  CommentCardInfo,
  CommentCardUserImage,
  CommentCardWrapper,
} from './CommentCard.style';

type CommentProps = { comment: Comment; parentCommentId?: string };

export function CommentCard({ comment, parentCommentId }: CommentProps): JSX.Element | null {
  const dispatch = useDispatch();
  const [openReply, setOpenReply] = useState<boolean>(false);
  const messageInputRef = useRef<HTMLInputElement>(null);
  const articleId = useSelector(selectArticleModal);
  const article = useSelector(selectNews).articles.find((article) => article._id === articleId);
  const user = useSelector(selectUser);
  const replyId = parentCommentId ? parentCommentId : comment._id;

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
        parentId: replyId,
        comment: messageInputRef.current.value,
      });
      dispatch(
        commentsAddComment({
          articleId: article._id,
          parentId: parentCommentId,
          comment: {
            _id: comment.id,
            user: { username: user.username, image: user.image },
            comment: messageInputRef.current.value,
            parentId: replyId,
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
    setOpenReply(false);
  }

  async function commentUpVoteButton() {
    if (!article) return;
    console.log(await requestRateComment(comment._id, CommentChoice.up));

    dispatch(commentUpVote({ comment, userId: user.id, articleId: article._id }));
  }

  if (!articleId) return null;

  return (
    <CommentCardWrapper>
      <CommentCardUserImage src={comment.user.image} />
      <CommentCardInfo>
        <span style={{ fontWeight: 'bold' }}>{comment.user.username}</span>
        <span>{comment.comment}</span>
        <AddNewCommentChildWrapper>
          <div style={{ display: 'flex' }}>
            <div
              style={{ cursor: 'pointer', marginRight: '0.5rem' }}
              onClick={() => setOpenReply(true)}
            >
              Reply
            </div>
            <div style={{ display: 'flex', cursor: 'pointer' }} onClick={commentUpVoteButton}>
              <span style={{ marginRight: '2px' }}>{comment.upvotes.length}</span>
              Upvote
            </div>
          </div>

          {openReply && (
            <ModalArticleInputWrapper onSubmit={sendMessage}>
              <ModalArticleMessageInput type="text" ref={messageInputRef} />
              <ModalArticleVoteButton type="submit">send</ModalArticleVoteButton>
              <ModalArticleCancelButton onClick={() => setOpenReply(false)}>
                cancel
              </ModalArticleCancelButton>
            </ModalArticleInputWrapper>
          )}
        </AddNewCommentChildWrapper>
        <div>
          {comment.children.map((c) => (
            <CommentCard key={getNextId()} comment={c} parentCommentId={c._id} />
          ))}
        </div>
      </CommentCardInfo>
    </CommentCardWrapper>
  );
}
