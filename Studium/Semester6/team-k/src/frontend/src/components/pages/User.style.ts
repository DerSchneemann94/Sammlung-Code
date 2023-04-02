import { MdEdit } from 'react-icons/md';
import styled from 'styled-components';

export const UserPage = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: center;

  margin-top:0.75rem;
`;

export const UserProfileImageWrapper = styled.span`
  position: relative;
`;

export const UserProfileImageEditIcon = styled(MdEdit)`
  position: absolute;
  right: 0%;
  bottom: 0.5rem;
  font-size: 1.5rem;
`;

export const UserArticleGridWrapper = styled.div`
  display: grid;
  grid-template-columns: 33% 33% 33%;
  @media (max-width: 1350px) {
    grid-template-columns: 50% 50%;
  }
  @media (max-width: 900px) {
    grid-template-columns: 100%;
  }
`;

export const UserArticle = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  margin: 0 1rem;
  margin-bottom: 5rem;
  padding: 2rem;
  background-color: #f4f4f4;

  border-radius: 50px;
  word-break: break-all;
  cursor: pointer;
  box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;
  transition: all 1s ease;
  :hover {
    box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
  }
`;

export const UserArticleImageWrapper = styled.div`
  margin-top: auto;
  padding-top: 0.5rem;
`;

export const LogoutButton = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 1rem;
  width: 140px;
  height: 45px;
  font-weight: 400;
  letter-spacing: 2.5px;
  font-weight: 500;
  color: #000;
  background-color: #fff;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  &:hover {
    background-color: #2ee59d;
    box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
    color: #fff;
  }
`;

export const UserArticleImage = styled.img`
  border-radius: 5px;
  width: 100%;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  object-fit: contain;
  max-height: 30rem;
  @media (max-width: 1930px) {
    max-height: 20rem;
  }
  @media (max-width: 1600px) {
    max-height: 15rem;
  }

  @media (max-width: 1350px) {
    grid-template-columns: 50% 50%;
    max-height: 20rem;
  }
  @media (max-width: 900px) {
    grid-template-columns: 100%;
  }
`;
