import { createSelector } from '@reduxjs/toolkit';
import { RootState } from '..';
import { UserState } from '../slices/userSlice';

function selectSelf(state: RootState): UserState {
  return state.user;
}

export const selectUser = createSelector(selectSelf, (state) => state);
