package hussein.nasereddine.areeba_challenge.models.view_models.media;

import androidx.annotation.NonNull;

import hussein.nasereddine.areeba_challenge.enums.RecyclerViewItemType;
import hussein.nasereddine.areeba_challenge.models.data_models.media.AlbumModel;

public class AlbumVM {
    private final AlbumModel album;

    public AlbumVM(@NonNull AlbumModel album){
        this.album = album;
    }

    public int getItemType(){
        return RecyclerViewItemType.ALBUM;
    }

    public AlbumModel getAlbum() {
        return album;
    }
}
