import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export type ModalArticleSliceState = {
  content: null | string;
};

const initialState: ModalArticleSliceState = {
  content: null,
};

export const modalArticleSlice = createSlice({
  name: 'modalSlice',
  initialState,
  reducers: {
    modalArticleSet(state, action: PayloadAction<string>) {
      state.content = action.payload;
    },
    modalArticleReset() {
      return initialState;
    },
  },
});

export const { modalArticleSet, modalArticleReset } = modalArticleSlice.actions;

export default modalArticleSlice.reducer;
