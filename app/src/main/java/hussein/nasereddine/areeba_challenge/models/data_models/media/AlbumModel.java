package hussein.nasereddine.areeba_challenge.models.data_models.media;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import hussein.nasereddine.areeba_challenge.utils.data.JsonDataParser;

public class AlbumModel extends JsonDataParser {
    private final String mediaUrl;

    public AlbumModel(@NonNull JSONObject jsonObject){
        this.mediaUrl = tryToGet(jsonObject, "image_url");
    }

    public String getMediaUrl() {
        return mediaUrl;
    }
}
