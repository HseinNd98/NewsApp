package hussein.nasereddine.areeba_challenge.models.view_models;

import androidx.annotation.NonNull;

import hussein.nasereddine.areeba_challenge.models.view_models.articles.ArticleLoaderVM;
import hussein.nasereddine.areeba_challenge.models.view_models.articles.ArticleVM;
import hussein.nasereddine.areeba_challenge.models.view_models.media.AlbumVM;

@SuppressWarnings("FieldCanBeLocal")
public class RecyclerViewVM {
    private final int itemType;

    // Article
    private ArticleVM articleVM;
    public RecyclerViewVM(@NonNull ArticleVM articleVM){
        this.articleVM = articleVM;
        this.itemType = articleVM.getItemType();
    }

    // Album
    private AlbumVM albumVM;
    public RecyclerViewVM(@NonNull AlbumVM albumVM){
        this.albumVM = albumVM;
        this.itemType = albumVM.getItemType();
    }

    // Article loader
    private ArticleLoaderVM articleLoaderVM;
    public RecyclerViewVM(@NonNull ArticleLoaderVM articleLoaderVM){
        this.articleLoaderVM = articleLoaderVM;
        this.itemType = articleLoaderVM.getItemType();
    }

    public ArticleLoaderVM getArticleLoaderVM() {
        return articleLoaderVM;
    }

    public AlbumVM getAlbumVM() {
        return albumVM;
    }

    public ArticleVM getArticleVM() {
        return articleVM;
    }

    public int getItemType() {
        return itemType;
    }
}
