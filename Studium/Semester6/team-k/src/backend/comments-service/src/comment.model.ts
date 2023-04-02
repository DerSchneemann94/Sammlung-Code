import * as mongoose from 'mongoose';
import { Timestamp } from 'rxjs';

export const CommentSchema = new mongoose.Schema({
    articleId: {type: mongoose.Schema.Types.ObjectId, required: true},
    userId: {type: mongoose.Schema.Types.ObjectId, required: true},
    parentId: {type: mongoose.Schema.Types.ObjectId, required: false},
    comment: {type: String, required: true},
    upvotes: [mongoose.Schema.Types.ObjectId],
    downvotes: [mongoose.Schema.Types.ObjectId]
},
{
  timestamps: true
});

export interface Comment extends mongoose.Document{
    id: String;
    articleId: string;
    userId: string;
    parentId: string;
    comment: string;
    upvotes: string[];
    downvotes: string[];
    createdAt;
}