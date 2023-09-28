package hussein.nasereddine.areeba_challenge.utils.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.models.data_models.media.AlbumModel;

public class JsonReader extends JsonDataParser {

    /**
     * Streams local raw json resource file
     * Converts it to a json String, then converts it to a JSON array
     *
     * Then loops over the JSON array to generate a List of {@link AlbumModel}
     * to be returned after
     *
     * @param context   Context
     * @return          List of locally stored album model data
     */
    @Nullable
    public static List<AlbumModel> loadJSONAlbumFromRawResource(@NonNull Context context){
        try {
            InputStream is = context.getResources().openRawResource(R.raw.album);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Json string of file content
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Convert string to JSON array
            JSONObject jsonRootObj = new JSONObject(json);
            JSONArray jsonArray = tryToGetArray(jsonRootObj, "album");
            List<AlbumModel> albums = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject obj = tryToGetArrayObject(jsonArray, i);
                if(obj != null){
                    albums.add(new AlbumModel(obj));
                }
            }

            return albums;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
