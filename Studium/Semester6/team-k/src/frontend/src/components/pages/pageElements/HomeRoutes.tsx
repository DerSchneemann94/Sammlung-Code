import React, { useRef, useState } from 'react';
import { slide as Menu } from 'react-burger-menu';
import { isMobile } from 'react-device-detect';
import { useSelector } from 'react-redux';
import { useLocation } from 'react-router';
import { history, useDispatch } from '../../..';
import { selectNews } from '../../../selectors/newsSelectors';
import { newsSetFilteredArticles } from '../../../slices/newsSlice';
import { RoutePaths } from '../../constants/routes';
import { burgerMenuStyle } from '../App.style';

import {
  HomeRoutesInputLikeIconSpan,
  HomeRoutesMargin,
  HomeSearchIcon,
  HomeSearchInput,
  HomeSearchInputContent,
  HomeSearchWrapper,
  Logo,
  MenuItem,
  MenuWrapper,
  NavigationWrapper,
} from './HomeRoutes.style';

import { AiFillLike, AiOutlineLike } from 'react-icons/ai';
import useToggleState from '../../hooks/useToggleState';
import useAsyncEffect from '../../hooks/useAsyncEffect';
import { NewsArticle } from '../../../types/api/newsAPI.org';

function showSearchBar(path: string): boolean {
  switch (path) {
    case RoutePaths.Login:
    case RoutePaths.Register:
    case RoutePaths.UserEdit:
      return false;
    default:
      return true;
  }
}

export function HomeRoutes(): JSX.Element {
  const dispatch = useDispatch();
  const location = useLocation().pathname;
  const news = useSelector(selectNews);
  const [searchFilter, setSearchFilter] = useState('');
  const [showMostLiked, toggleShowMostLikes] = useToggleState(false);
  const [showBurgerMenu, setShowBurgerMenu] = useState(false);
  const searchInputRef = useRef<HTMLInputElement>(null);

  function searchInputChange() {
    if (!searchInputRef.current) return;
    setSearchFilter(searchInputRef.current.value);
  }

  function filterArticles() {
    const lowerCaseTextFilters = searchFilter
      .toLowerCase()
      .split(/\s+/g)
      .filter((item) => item.length > 0);

    const filteredArticles = Object.values(news.articles).filter((article) => {
      for (const filter of lowerCaseTextFilters) {
        if (article.searchableContent.indexOf(filter) === -1) {
          return false;
        }
      }
      return true;
    });

    const sortedArticles = filteredArticles.sort((lhs, rhs) => {
      return rhs.publishedAtDate.getTime() - lhs.publishedAtDate.getTime();
    });
    if (!showMostLiked) return sortedArticles;
    return sortedArticles.sort(
      (lhs, rhs) =>
        rhs.likes.length - rhs.dislikes.length - (lhs.likes.length - rhs.dislikes.length),
    );
  }

  useAsyncEffect(
    async (stopped) => {
      const filteredArticles = await new Promise<Array<NewsArticle>>((resolve) => {
        resolve(filterArticles());
      });
      if (stopped()) return;
      dispatch(newsSetFilteredArticles(filteredArticles));
    },
    [searchFilter, showMostLiked, news.articles],
  );

  function createHeaderMenu() {
    if (isMobile) {
      return (
        <div
          style={{
            gridArea: '1 / 3 / 2 / 4',
            alignContent: 'end',
            display: 'flex',
            justifyContent: 'flex-end',
            paddingRight: '1rem',
          }}
        >
          <Menu
            isOpen={showBurgerMenu}
            onOpen={() => {
              setShowBurgerMenu(true);
            }}
            styles={burgerMenuStyle}
            right
          >
            <a
              id="home"
              className="menu-item"
              onClick={() => {
                history.push(RoutePaths.Home);
                setShowBurgerMenu(false);
              }}
            >
              HOME
            </a>
            {location === RoutePaths.User ? (
              <a
                id="EDIT"
                className="menu-item"
                onClick={() => {
                  history.push(RoutePaths.UserEdit);
                  setShowBurgerMenu(false);
                }}
              >
                EDIT
              </a>
            ) : (
              <a
                id="USER"
                className="menu-item"
                onClick={() => {
                  history.push(RoutePaths.User);
                  setShowBurgerMenu(false);
                }}
              >
                USER
              </a>
            )}
          </Menu>
        </div>
      );
    }
    return (
      <MenuWrapper>
        <MenuItem
          onClick={() => {
            history.push(RoutePaths.Home);
          }}
        >
          HOME
        </MenuItem>
        {location === RoutePaths.User ? (
          <MenuItem
            onClick={() => {
              history.push(RoutePaths.UserEdit);
            }}
          >
            EDIT
          </MenuItem>
        ) : (
          <MenuItem
            onClick={() => {
              history.push(RoutePaths.User);
            }}
          >
            USER
          </MenuItem>
        )}
      </MenuWrapper>
    );
  }

  return (
    <HomeRoutesMargin>
      <NavigationWrapper id="outer-container">
        <Logo onClick={() => history.push(RoutePaths.Home)}>Newsy</Logo>
        {!showSearchBar(location) ? null : (
          <HomeSearchWrapper>
            <HomeSearchInputContent>
              <HomeSearchIcon />
              <HomeSearchInput
                type="search"
                name="q"
                ref={searchInputRef}
                onChange={searchInputChange}
              />
              <HomeRoutesInputLikeIconSpan onClick={toggleShowMostLikes}>
                {showMostLiked ? <AiFillLike /> : <AiOutlineLike />}
              </HomeRoutesInputLikeIconSpan>
            </HomeSearchInputContent>
          </HomeSearchWrapper>
        )}
        {createHeaderMenu()}
      </NavigationWrapper>
    </HomeRoutesMargin>
  );
}
