import styled from 'styled-components';
import { IconArrowImage } from '../../resources/icons/icons';

export const ModalArticleVotesWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: fit-content;

  text-align: center;
  font-size: 0.8rem;
  padding: 0.5rem;

  ${IconArrowImage} {
    width: 2rem;
    height: 2rem;
    padding: 0.2rem;
  }
`;

export const ModalArticleImage = styled.img`
  width: 100%;
  border-radius: 4px;
  margin-bottom: 0.5rem;
`;

export const ModalArticleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  font-family: Poppins;
`;

export const ModalArticleMessageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 0.4rem;
  border-radius: 10px;
  padding: 20px;
  background-color: white;
  border-radius: 5px;
  border: 1px solid #d9e1ec;
`;

export const ModalArticleInputWrapper = styled.form`
  display: flex;
`;

export const ModalArticleCommentsWrapper = styled.div`
  display: flex;
  flex-direction: column-reverse;
`;

export const ModalArticleMessageInput = styled.input`
  width: 100%;
  height: 2rem;
`;

export const ModalArticleVoteButton = styled.button`
  font-weight: 600;
  background-color: #2ee59d;
  color: #fff;
  border: none;
  border-radius: 5px;
  margin-left: 5px;
  padding: 0rem 1rem;
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  &:hover {
    transform: scale(1.1);
  }
`;

export const ModalArticleCancelButton = styled.button`
  font-weight: 600;
  background-color: #fff;
  color: #21293c;
  border: 1px solid #d9e1ec;
  border-radius: 5px;
  margin-left: 5px;
  padding: 0rem 1rem;
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  &:hover {
    transform: scale(1.1);
  }
`;
