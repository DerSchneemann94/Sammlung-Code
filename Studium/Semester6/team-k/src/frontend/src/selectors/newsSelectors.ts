import { createSelector } from "@reduxjs/toolkit";
import { RootState } from "..";
import { NewsState } from "../slices/newsSlice";

function selectSelf(state: RootState): NewsState {
  return state.news;
}

export const selectNews = createSelector(selectSelf, (state) => state.content);
