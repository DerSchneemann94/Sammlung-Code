import React, { useEffect, useRef } from 'react';
import ReactModal from 'react-modal';
import { useDispatch, useSelector } from '../..';
import { fetchArticleComments } from '../../api/api';
import { selectArticleModal } from '../../selectors/modalSelectors';
import { selectNews } from '../../selectors/newsSelectors';
import { commentsSetArticleComments } from '../../slices/commentsSlice';
import { modalArticleReset } from '../../slices/modalSlice';
import { stopEvent } from '../../utility/helper';
import Comments from '../elements/Comments';
import useAsyncEffect from '../hooks/useAsyncEffect';
import {
  ModalCustomStyle,
  NewsOutletAndDate,
  NewsOutletName,
  NewsTitle,
} from '../pages/Home.style';
import { ModalArticleImage, ModalArticleWrapper } from './ModalArticle.style';

export function ModalArticle(): JSX.Element | null {
  const dispatch = useDispatch();
  const articleId = useSelector(selectArticleModal);
  const article = useSelector(selectNews).articles.find((article) => article._id === articleId);
  const modalRef = useRef<HTMLDivElement>(null);

  useAsyncEffect(
    async (stopped) => {
      if (!articleId) return;
      const comments = await fetchArticleComments(articleId);
      if (stopped()) return;

      dispatch(
        commentsSetArticleComments({
          articleId,
          comments: comments.map((comment) => {
            return { ...comment, children: [] };
          }),
        }),
      );
    },
    [articleId],
  );

  useEffect(() => {
    if (!modalRef.current) return;
    ReactModal.setAppElement(modalRef.current);
  }, [modalRef]);

  function closeModal() {
    dispatch(modalArticleReset());
  }

  if (!article) return null;

  return (
    <div ref={modalRef}>
      <ReactModal
        style={ModalCustomStyle}
        isOpen={article !== undefined}
        onRequestClose={closeModal}
        contentLabel="News Modal"
        ariaHideApp={false}
      >
        <ModalArticleWrapper>
          <ModalArticleImage src={article.articleImageUrl} />
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
          <span style={{ fontWeight: 'bold', marginTop: '2rem' }}>Discussion</span>
          <Comments />
        </ModalArticleWrapper>
      </ReactModal>
    </div>
  );
}
