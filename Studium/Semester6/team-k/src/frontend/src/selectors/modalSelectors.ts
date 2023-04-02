import { createSelector } from "@reduxjs/toolkit";
import { RootState } from "..";
import { ModalArticleSliceState } from "../slices/modalSlice";

function selectSelf(state: RootState): ModalArticleSliceState {
  return state.modal;
}

export const selectArticleModal = createSelector(selectSelf, (state) => state.content);
