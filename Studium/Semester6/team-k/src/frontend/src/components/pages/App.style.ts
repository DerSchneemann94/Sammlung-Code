import styled from 'styled-components';

export const AppWrapper = styled.div`
  display: flex;
  flex-direction: column;
  font-family: 'Poppins';
`;

// for React Burger Menu (not a react style component)
export const burgerMenuStyle = {
  bmBurgerButton: {
    position: 'fixed',
    width: '36px',
    height: '30px',
  },
  bmBurgerBars: {
    background: '#373a47'
  },
  bmBurgerBarsHover: {
    background: '#a90000'
  },
  bmCrossButton: {
    height: '24px',
    width: '24px'
  },
  bmCross: {
    background: '#bdc3c7'
  },
  bmMenuWrap: {
    position: 'fixed',
    height: '100%',
    top: '0'
  },
  bmMenu: {
    background: '#373a47',
    padding: '2.5em 1.5em 0',
    fontSize: '1.15em'
  },
  bmMorphShape: {
    fill: '#373a47'
  },
  bmItemList: {
    color: '#b8b7ad',
    padding: '0.8em',
    display: 'flex',
    flexDirection: 'column'
  },
  bmItem: {
    display: 'inline-block',
    marginTop: '0.5rem'
  },
  bmOverlay: {
    background: 'transparent'
  }
}
