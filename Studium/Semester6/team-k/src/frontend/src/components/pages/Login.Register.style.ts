import styled from 'styled-components';

export const LoginRegisterPage = styled.div`
  display: flex;
  justify-content: center;
`;

export const LoginRegisterContentWrapper = styled.div`
  width: fit-content;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #e5e5e5;
  padding: 2rem;
  border-radius: 10px;
`;

export const LoginRegisterInput = styled.input`
  margin: 0.25rem;
  padding: 0.5rem;
  border-radius: 5px;
`;

export const LoginRegisterButton = styled.button`
  background-color: #000;
  color: #fff;
  margin: 0.25rem;
  padding: 0.5rem;
  margin-top: 0.5rem;
  border-radius: 5px;
  width: 100%;
  border: none;
  cursor: pointer;
`;
