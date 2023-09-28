package hussein.nasereddine.areeba_challenge.view_holders.articles;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hussein.nasereddine.areeba_challenge.R;
import io.supercharge.shimmerlayout.ShimmerLayout;

@SuppressWarnings("FieldCanBeLocal")
public class ArticleLoaderVH extends RecyclerView.ViewHolder {

    // Views
    private final ShimmerLayout mShimmerLayout;

    public ArticleLoaderVH(@NonNull View view){
        super(view);
        this.mShimmerLayout = view.findViewById(R.id.rv_item_article_loader_shimmer);
    }

    public void bind(){
        mShimmerLayout.startShimmerAnimation();
    }
}
