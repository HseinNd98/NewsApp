package hussein.nasereddine.areeba_challenge.view_holders.articles;

import android.content.Context;
import android.text.Html;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.interfaces.ItemClickListener;
import hussein.nasereddine.areeba_challenge.models.data_models.articles.ArticleModel;
import hussein.nasereddine.areeba_challenge.models.view_models.RecyclerViewVM;
import hussein.nasereddine.areeba_challenge.models.view_models.articles.ArticleVM;

@SuppressWarnings("FieldCanBeLocal")
public class ArticleVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final Context context;
    private final ItemClickListener itemClickListener;

    // Views
    private final AppCompatImageView mImageView;
    private final AppCompatTextView mTitle, mDate;

    // View model
    private RecyclerViewVM mViewVM;

    public ArticleVH(@NonNull View view, @NonNull Context context, @NonNull ItemClickListener itemClickListener){
        super(view);
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.mImageView = view.findViewById(R.id.rv_item_article_horizontal_img);
        this.mTitle = view.findViewById(R.id.rv_item_article_horizontal_title);
        this.mDate = view.findViewById(R.id.rv_item_article_horizontal_date);
        view.setOnClickListener(this);
    }

    public void bind(@NonNull RecyclerViewVM viewVM){
        this.mViewVM = viewVM;

        // model
        final ArticleVM articleVM = viewVM.getArticleVM();
        final ArticleModel article = articleVM.getArticle();

        // bind
        mImageView.setImageDrawable(null);
        Glide.with(context).load(article.getImageUrl()).into(mImageView);
        mTitle.setText(Html.fromHtml(article.getTitle(), 0).toString().trim());
        mDate.setText(article.getDateTime().getTimeAgo(articleVM.getLanguage()));
    }

    @Override
    public void onClick(View view) {
        if(itemClickListener != null){
            itemClickListener.onClick(mViewVM, getAbsoluteAdapterPosition(), 0);
        }
    }
}
