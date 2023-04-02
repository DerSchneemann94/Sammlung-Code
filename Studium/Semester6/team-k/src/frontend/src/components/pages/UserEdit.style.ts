import styled from 'styled-components';

export const UserEditWrapper = styled.div`
  display: flex;
  justify-content: center;
`;

export const UserEditContent = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  width: fit-content;
  background-color: #f4f4f4;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: rgba(50,50,93,0.25) 0px 2px 5px -1px,rgba(0,0,0,0.3) 0px 1px 3px -1px;
`;

export const UserEditProfilePictureWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0.25rem;
`;

export const UserEditProfilePictureText = styled.span``;

export const UserEditProfilePictureInput = styled.input`
  width: 14rem;
  margin-top: 2rem;
`;
