package hussein.nasereddine.areeba_challenge.models.view_models.articles;

import androidx.annotation.NonNull;

import hussein.nasereddine.areeba_challenge.enums.ArticleLanguage;
import hussein.nasereddine.areeba_challenge.enums.RecyclerViewItemType;
import hussein.nasereddine.areeba_challenge.models.data_models.articles.ArticleModel;

public class ArticleVM {
    private final ArticleModel article;
    private final ArticleLanguage language;

    public ArticleVM(@NonNull ArticleModel article, @NonNull ArticleLanguage language){
        this.article = article;
        this.language = language;
    }

    public int getItemType(){
        return language == ArticleLanguage.ARABIC ?
                    RecyclerViewItemType.ARTICLE_HORIZONTAL_AR :
                    RecyclerViewItemType.ARTICLE_HORIZONTAL_EN;
    }

    public ArticleLanguage getLanguage() {
        return language;
    }

    public ArticleModel getArticle() {
        return article;
    }
}
