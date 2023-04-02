import { Article } from "src/articles/schemas/article.schema";

export interface RatingDao {

    getRating(id: string);

    addRating(rating: Article, change: any, userId: string);

    deleteRating(rating: Article, change: any, userId: string);

}