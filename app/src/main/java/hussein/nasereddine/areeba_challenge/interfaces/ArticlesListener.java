package hussein.nasereddine.areeba_challenge.interfaces;

import androidx.annotation.NonNull;

import java.util.List;

import hussein.nasereddine.areeba_challenge.models.data_models.articles.ArticleModel;

public interface ArticlesListener {
    void onArticlesReceived(@NonNull List<ArticleModel> articles, int page, int count, int pages, int totalResults);
    void onArticlesFailure(@NonNull String message);
}
