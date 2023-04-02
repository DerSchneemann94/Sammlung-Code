import styled from 'styled-components';
import { BiSearchAlt2 } from 'react-icons/bi';
import { ZIndex } from '../../constants/globalConstants';

export const HomeRoutesMargin = styled.div`
  margin-bottom: 5.25rem;
  @media (max-width: 1200px) {
    margin-bottom: 8.45rem;
  }
`;

export const HomeRoutesSpacer = styled.div`
  margin-bottom: 10.5em;
  z-index: ${ZIndex.Header};
`;

export const HomeRoutesWrapper = styled.div`
  position: fixed;
  width: 100%;
  background-color: #fff;
`;

export const MenuWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  grid-area: 1 / 3 / 2 / 4;
`;

export const Logo = styled.div`
  font-weight: 700;
  font-family: Poppins;
  font-size: 1.3rem;
  @media (max-width: 1200px) {
    width: 36px;
    height: 30px;
    left: 36px;
    top: 36px;
  }
`;

export const NavigationWrapper = styled.div`
  position: fixed;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-row-gap: 1rem;

  width: calc(100% - 4rem);
  padding: 1rem 2rem 0.5rem 2rem;
  background-color: #fff;

  box-shadow: rgba(9, 30, 66, 0.25) 0px 1px 1px, rgba(9, 30, 66, 0.13) 0px 0px 1px 1px;

  z-index: ${ZIndex.Header};
  pointer-events: all;
`;

export const MenuItem = styled.div`
  pointer-events: auto;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 140px;
  height: 45px;
  margin-right: 10px;
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
    transform: translateY(-7px);
  }
`;

export const HomeSearchWrapper = styled.div`
  /* width: calc(100vw - 16rem); */
  display: flex;
  flex-direction: column;
  align-items: center;
  @media (max-width: 1200px) {
    grid-area: 2 / 1 / 3 / span 3;
    top: 7rem;
    width: 100%;
    margin: 0;
  }
`;

export const HomeSearchInputContent = styled.div`
  display: flex;
  align-items: center;
  max-width: 30rem;
  width: 100%;
  border: 1px solid black;
  border-radius: 3px;
  padding: 0.3rem;
  background-color: #fff;
  @media (max-width: 1200px) {
    width: 90%;
  }
`;

export const HomeSearchIcon = styled(BiSearchAlt2)`
  font-size: 1.5rem;
  width: 1.5rem;
  height: 1.5rem;
  margin-right: 0.5rem;
`;

export const HomeSearchInput = styled.input`
  width: calc(100% - 2rem);
  border: none;
  outline: none;
`;

export const HomeRoutesInputLikeIconSpan = styled.span`
  display: flex;
  margin-left: 0.5rem;
  transition: all 0.3s ease 0s;
  cursor: pointer;
  &:hover {
    color: black;
    transform: scale(1.2);
  }
`;
