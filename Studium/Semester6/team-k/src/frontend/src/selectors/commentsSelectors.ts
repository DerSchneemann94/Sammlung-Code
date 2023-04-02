import { createSelector } from '@reduxjs/toolkit';
import { RootState } from '..';
import { CommentsSliceState } from '../slices/commentsSlice';

function selectSelf(state: RootState): CommentsSliceState {
  return state.comments;
}

export const selectComments = createSelector(selectSelf, (state) => state);
