import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import toast from 'react-hot-toast';

export type CommentPost = {
  parentId?: string;
  articleId: string;
  userId: string;
  comment: string;
  upvotes: number;
  downvotes: number;
};

export type CommentAPI = {
  articleId: string;
  comment: string;
  createdAt: string;
  downvotes: Array<string>;
  parentId?: string;
  _id: string;
  upvotes: Array<string>;
  user: { username: string; image: string };
};

export type Comment = {
  articleId: string;
  comment: string;
  downvotes: Array<string>;
  parentId?: string;
  _id: string;
  upvotes: Array<string>;
  user: { username: string; image: string };
  children: Array<Comment>;
};

function makeCommentsStructure(comments: Array<Comment>) {
  //reset children
  comments.map((comment) => (comment.children = []));
  //create children structure
  const rootComments = comments.filter((comment) => !comment.parentId);
  const childComments = comments.filter((comment) => comment.parentId);
  let levelComments = rootComments;

  while (childComments.length > 0) {
    const nextLevelComments: Array<Comment> = [];
    console.log('nextLevelComments', nextLevelComments, childComments.length);
    for (const comment of levelComments) {
      for (let i = 0; i < childComments.length; i++) {
        console.log('vergleich', childComments[i].parentId, comment._id);
        if (childComments[i].parentId === comment._id) {
          const [lostChild] = childComments.splice(i, 1);
          comment.children.push(lostChild);
          i--;
        }
      }
      for (const child of comment.children) {
        nextLevelComments.push(child);
      }
    }
    levelComments = nextLevelComments;
  }
  return rootComments;
}

export type CommentsSliceState = {
  [articleId: string]: { allComments: Array<Comment>; structuredComments: Array<Comment> };
};

const initialState: CommentsSliceState = {};

export const commentsSlice = createSlice({
  name: 'comments',
  initialState,
  reducers: {
    commentsSetArticleComments(
      state,
      action: PayloadAction<{ articleId: string; comments: Array<Comment> }>,
    ) {
      //initialize Arrays
      if (!state[action.payload.articleId]) {
        state[action.payload.articleId] = {
          allComments: [],
          structuredComments: [],
        };
      }
      //set Comments
      state[action.payload.articleId].allComments = action.payload.comments;
      state[action.payload.articleId].structuredComments = makeCommentsStructure(
        state[action.payload.articleId].allComments,
      );
    },
    commentsAddComment(
      state,
      action: PayloadAction<{ articleId: string; comment: Comment; parentId?: string }>,
    ) {
      if (action.payload.comment.user.username.length <= 0) {
        toast.error('Error: You have to login');
        return;
      }
      //initialize Arrays
      if (!state[action.payload.articleId]) {
        state[action.payload.articleId] = {
          allComments: [],
          structuredComments: [],
        };
      }
      state[action.payload.articleId].allComments.push(action.payload.comment);
      if (!action.payload.parentId) {
        state[action.payload.articleId].structuredComments.push(action.payload.comment);
        toast.success('Comment added successfully');
        return state;
      }

      let parentComment = state[action.payload.articleId].allComments.find(
        (articleComment) => articleComment._id === action.payload.parentId,
      );

      const pathToComment: Array<string> = [];
      while (parentComment) {
        pathToComment.push(parentComment._id);
        const nextParentId = parentComment.parentId;
        parentComment = state[action.payload.articleId].allComments.find(
          (articleComment) => articleComment._id === nextParentId,
        );
      }
      const firstParentId = pathToComment.pop();
      let targetComment = state[action.payload.articleId].structuredComments.find(
        (rootComment) => rootComment._id === firstParentId,
      );

      for (let i = pathToComment.length - 1; i >= 0; i--) {
        targetComment = targetComment?.children.find((child) => child._id === pathToComment[i]);
      }

      if (!targetComment) return;
      targetComment.children.push(action.payload.comment);
      toast.success('Comment added successfully');
    },
    commentUpVote(
      state,
      action: PayloadAction<{ articleId: string; comment: Comment; userId: string }>,
    ) {
      if (action.payload.userId.length <= 0) {
        toast.error('Error: You have to login');
        return;
      }

      const votedComment = state[action.payload.articleId].allComments.find(
        (c) => c._id === action.payload.comment._id,
      );
      if (!votedComment) return;
      if (votedComment.upvotes.includes(action.payload.userId)) {
        votedComment.upvotes.splice(votedComment.upvotes.indexOf(action.payload.userId), 1);
      }
      votedComment.upvotes.push(action.payload.userId);

      if (!action.payload.comment.parentId) {
        const structuredVotedComment = state[action.payload.articleId].structuredComments.find(
          (c) => c._id === action.payload.comment._id,
        );
        if (!structuredVotedComment) return;
        if (structuredVotedComment.upvotes.includes(action.payload.userId)) {
          structuredVotedComment.upvotes.splice(
            structuredVotedComment.upvotes.indexOf(action.payload.userId),
            1,
          );

          toast.success('Upvote removed');
          return;
        }
        structuredVotedComment.upvotes.push(action.payload.userId);
        toast.success('Comment upvoted');

        return;
      }

      let parentComment = state[action.payload.articleId].allComments.find(
        (articleComment) => articleComment._id === action.payload.comment.parentId,
      );

      const pathToComment: Array<string> = [];
      while (parentComment) {
        pathToComment.push(parentComment._id);
        const nextParentId = parentComment.parentId;
        parentComment = state[action.payload.articleId].allComments.find(
          (articleComment) => articleComment._id === nextParentId,
        );
      }
      const firstParentId = pathToComment.pop();
      let targetComment = state[action.payload.articleId].structuredComments.find(
        (rootComment) => rootComment._id === firstParentId,
      );

      for (let i = pathToComment.length - 1; i >= 0; i--) {
        targetComment = targetComment?.children.find((child) => child._id === pathToComment[i]);
      }
      targetComment = targetComment?.children.find((c) => c._id === action.payload.comment._id);
      if (!targetComment) return;
      if (targetComment.upvotes.includes(action.payload.userId)) {
        targetComment.upvotes.splice(targetComment.upvotes.indexOf(action.payload.userId), 1);
        toast.success('Upvote removed');
        return;
      }

      targetComment.upvotes.push(action.payload.userId);
      toast.success('Comment upvoted');
    },
  },
});

export const { commentsSetArticleComments, commentsAddComment, commentUpVote } =
  commentsSlice.actions;

export default commentsSlice.reducer;
