package hussein.nasereddine.areeba_challenge.activities;

import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.enums.ArticleLanguage;
import hussein.nasereddine.areeba_challenge.interfaces.ArticlesListener;
import hussein.nasereddine.areeba_challenge.models.data_models.articles.ArticleModel;
import hussein.nasereddine.areeba_challenge.utils.network.requests.ArticlesRequest;

@SuppressWarnings("FieldCanBeLocal")
public class NewsDetailsActivity extends AppCompatActivity implements ArticlesListener {

    // Views
    private MaterialButton mBtnBack;
    private AppCompatTextView mNavbarTitle, mTitle, mDate, mBody;
    private AppCompatImageView mImageView;

    // Article data
    private ArticleLanguage mLanguage = ArticleLanguage.ENGLISH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initViews();
        getArticleData();

        mBtnBack.setOnClickListener(v -> onBackPressed());
    }

    private void bindArticle(@NonNull ArticleModel article){
        Glide.with(this).load(article.getImageUrl()).into(mImageView);
        mNavbarTitle.setText(Html.fromHtml(article.getTitle(), 0).toString().trim());
        mTitle.setText(Html.fromHtml(article.getTitle(), 0).toString().trim());
        mDate.setText(article.getDateTime().getTimeAgo(mLanguage));
        mBody.setText(Html.fromHtml(article.getBody(), 0));
    }

    private void getArticleData(){
        final String articleID = getIntent().getStringExtra("id");
        if(articleID == null){
            Toast.makeText(this, "Invalid article ID!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Get language
        mLanguage = ArticleLanguage.valueOf(getIntent().getStringExtra("lang"));

        // Get article data
        new ArticlesRequest.Builder()
                .setArticleUri(articleID)
                .GET(this, this);
    }

    private void initViews(){
        mBtnBack = findViewById(R.id.activity_news_details_btn_back);
        mNavbarTitle = findViewById(R.id.activity_news_details_navbar_title);
        mTitle = findViewById(R.id.activity_news_details_title);
        mDate = findViewById(R.id.activity_news_details_date);
        mBody = findViewById(R.id.activity_news_details_body);
        mImageView = findViewById(R.id.activity_news_details_img);
    }

    @Override
    public void onArticlesReceived(@NonNull List<ArticleModel> articles, int page, int count, int pages, int totalResults) {
        if(articles.size() == 0){
            Toast.makeText(this, "Article not found or maybe deleted!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ArticleModel article = articles.get(0);
        bindArticle(article);
    }

    @Override
    public void onArticlesFailure(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}