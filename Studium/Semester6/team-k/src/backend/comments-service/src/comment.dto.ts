export class CreateComment {
    userId: string;
    articleId: string;
    parentId: string;
    comment: string;
}

export class RateComment {
    userId: string;
    commentId: string;
    rating: string;
}

export class DeleteComment {
    userId: string
    commentId: string
}