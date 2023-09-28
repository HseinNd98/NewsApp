package hussein.nasereddine.areeba_challenge.utils.network.requests;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hussein.nasereddine.areeba_challenge.constants.Endpoints;
import hussein.nasereddine.areeba_challenge.constants.Tokens;
import hussein.nasereddine.areeba_challenge.interfaces.ArticlesListener;
import hussein.nasereddine.areeba_challenge.models.data_models.articles.ArticleModel;
import hussein.nasereddine.areeba_challenge.utils.data.JsonDataParser;
import hussein.nasereddine.areeba_challenge.utils.network.helpers.HttpRequest;

public class ArticlesRequest extends JsonDataParser {

    private ArticlesRequest(@NonNull HttpRequest.Builder requestBuilder, @NonNull ArticlesListener listener){
        requestBuilder.request(new HttpRequest.HttpCallback() {
            @Override
            public void onSuccess(@NonNull JSONObject jsonResponse) {
                final JSONObject articlesObj = tryToGetObject(jsonResponse, "articles");
                final JSONArray results = tryToGetArray(articlesObj, "results");
                final int page = tryToGetInt(articlesObj, "page");
                final int count = tryToGetInt(articlesObj, "count");
                final int pages = tryToGetInt(articlesObj, "pages");
                final int totalResults = tryToGetInt(articlesObj, "totalResults");

                final List<ArticleModel> list = new ArrayList<>();
                for(int i=0; i<results.length(); i++){
                    JSONObject obj = tryToGetArrayObject(results, i);
                    if(obj != null) {
                        list.add(new ArticleModel(obj));
                    }
                }

                listener.onArticlesReceived(list, page, count, pages, totalResults);
            }

            @Override
            public void onFailure(@NonNull String message) {
                listener.onArticlesFailure(message);
            }
        });
    }

    public static class Builder{
        private String keyword, articleUri;
        private int page = 1, count = 10;

        public Builder setArticleUri(@NonNull String articleUri){
            this.articleUri = articleUri;
            return this;
        }

        public Builder setKeyword(@NonNull String keyword){
            this.keyword = keyword;
            return this;
        }

        public Builder setPage(int page){
            this.page = page;
            return this;
        }

        public Builder setCount(int count){
            this.count = count;
            return this;
        }

        public ArticlesRequest GET(@NonNull Context context, @NonNull ArticlesListener listener){
            String url = String.format("%s?apiKey=%s&keyword=%s&articlesPage=%s&articlesCount=%s&resultType=%s",
                    Endpoints.GET_ARTICLES,
                    Tokens.API_TOKEN,
                    keyword,
                    page,
                    count,
                    "articles");

            // To get single article details
            if(articleUri != null){
                url = url + "&articleUri=" + articleUri;
            }

            HttpRequest.Builder requestBuilder = new HttpRequest.Builder(context)
                    .usingMethod(HttpRequest.Method.GET)
                    .withRequestURL(url);

            return new ArticlesRequest(requestBuilder, listener);
        }
    }
}
