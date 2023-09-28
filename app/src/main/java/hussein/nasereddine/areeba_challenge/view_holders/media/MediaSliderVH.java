package hussein.nasereddine.areeba_challenge.view_holders.media;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.models.data_models.media.AlbumModel;
import hussein.nasereddine.areeba_challenge.models.view_models.RecyclerViewVM;

@SuppressWarnings("FieldCanBeLocal")
public class MediaSliderVH extends RecyclerView.ViewHolder {
    private final Context context;

    // Views
    private final AppCompatImageView mImageView;

    // View model
    private RecyclerViewVM mViewVM;

    public MediaSliderVH(@NonNull View view, @NonNull Context context){
        super(view);
        this.context = context;
        this.mImageView = view.findViewById(R.id.rv_item_media_slider_item_img);
    }

    public void bind(@NonNull RecyclerViewVM viewVM){
        this.mViewVM = viewVM;

        // Model
        final AlbumModel album = viewVM.getAlbumVM().getAlbum();

        // bin
        Glide.with(context).load(album.getMediaUrl()).into(mImageView);
    }
}
