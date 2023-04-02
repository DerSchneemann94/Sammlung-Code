import { Article } from "src/schemas/Article.schema";

export interface ArticleDao {

    getArticlesFromDb();

    updateArticlesInDb(article: Article[]);

    //TO-DO: wird vermutlich nicht gebraucht
    DeleteArticlesFromdb();




}