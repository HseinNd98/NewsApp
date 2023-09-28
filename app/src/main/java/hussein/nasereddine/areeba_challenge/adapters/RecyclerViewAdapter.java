package hussein.nasereddine.areeba_challenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.enums.RecyclerViewItemType;
import hussein.nasereddine.areeba_challenge.interfaces.ItemClickListener;
import hussein.nasereddine.areeba_challenge.models.view_models.DefaultVH;
import hussein.nasereddine.areeba_challenge.models.view_models.RecyclerViewVM;
import hussein.nasereddine.areeba_challenge.view_holders.articles.ArticleLoaderVH;
import hussein.nasereddine.areeba_challenge.view_holders.articles.ArticleVH;
import hussein.nasereddine.areeba_challenge.view_holders.media.MediaSliderVH;

@SuppressWarnings("FieldCanBeLocal")
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Main
    private final Context context;
    private ItemClickListener itemClickListener;

    // Data lists
    private final List<RecyclerViewVM> list;

    public RecyclerViewAdapter(@NonNull Context context, @NonNull List<RecyclerViewVM> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType){
            case RecyclerViewItemType.ARTICLE_HORIZONTAL_EN:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_rv_item_article_horizontal_en, parent, false);
                return new ArticleVH(itemView, context, itemClickListener);

            case RecyclerViewItemType.ARTICLE_HORIZONTAL_AR:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_rv_item_article_horizontal_ar, parent, false);
                return new ArticleVH(itemView, context, itemClickListener);

            case RecyclerViewItemType.ALBUM:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_rv_item_media_slider_item, parent, false);
                return new MediaSliderVH(itemView, context);

            case RecyclerViewItemType.ARTICLES_LOADER:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_rv_item_article_loader, parent, false);
                return new ArticleLoaderVH(itemView);

            default:
            case RecyclerViewItemType.UNSPECIFIED:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_rv_item_default, parent, false);
                return new DefaultVH(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewVM viewModel = list.get(position);

        switch (holder.getItemViewType()){
            case RecyclerViewItemType.ARTICLE_HORIZONTAL_EN:
            case RecyclerViewItemType.ARTICLE_HORIZONTAL_AR:
                ((ArticleVH) holder).bind(viewModel);
                break;

            case RecyclerViewItemType.ALBUM:
                ((MediaSliderVH) holder).bind(viewModel);
                break;

            case RecyclerViewItemType.ARTICLES_LOADER:
                ((ArticleLoaderVH) holder).bind();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getItemType();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void clear(){
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void removeItemOfType(int itemType){
        for(int i=list.size()-1; i>=0; i--){
            if(list.get(i).getItemType() == itemType){
                list.remove(i);
                notifyItemRemoved(i);
            }
        }
    }
}