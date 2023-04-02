import styled, { css } from 'styled-components';
import { ZIndex } from '../constants/globalConstants';

export const HomeArticleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 50rem;
  justify-content: center;
  margin: 0 2rem 2rem;
  padding: 1.5rem 0;
  border-top: 1px solid #eee;
  justify-content: space-between;
  background-color: #f4f4f4;
  border-radius: 10px;
  cursor: pointer;
  box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;
  transition: all 0.8s ease;
  :hover {
    box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
  }

  @media (max-width: 750px) {
    width: 100%;
  }
`;

export const HomeArticleContentWrapper = styled.div`
  display: flex;
`;

export const HomeArticleContent = styled.div`
  display: flex;
  flex-direction: column;
  width: 37rem;
  @media (max-width: 750px) {
    width: 80%;
  }
`;

export const HomeArticleImage = styled.img`
  width: 37rem;
  margin-top: 1rem;
  border-radius: 5px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  @media (max-width: 750px) {
    width: 90%;
  }
`;

export const NewsOutletAndDate = styled.a`
  display: flex;
  color: #9c9b9a;
  font-size: 0.8125rem;
  text-decoration: none;
`;

export const NewsOutletName = styled.span`
  text-decoration: underline;
`;

export const NewsTitle = styled.div`
  font-size: 1.25rem;
  font-weight: 700;

  font-family: Poppins;
`;

export const ModalCustomStyle = {
  overlay: {
    zIndex: ZIndex.Modal,
  },
  content: {
    marginTop: '3rem',
    marginRight: 'auto',
    marginLeft: 'auto',
    border: 'none',
    boxShadow: '0px 8px 15px rgba(0, 0, 0, 0.3)',
    maxWidth: '50rem',
    backgroundColor: '#fafbff',
  },
};

export const ModalArticleClassName = css`
  font-family: Poppins;
`;
