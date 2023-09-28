package hussein.nasereddine.areeba_challenge.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.models.data_models.media.AlbumModel;
import hussein.nasereddine.areeba_challenge.models.view_models.RecyclerViewVM;
import hussein.nasereddine.areeba_challenge.models.view_models.media.AlbumVM;
import hussein.nasereddine.areeba_challenge.utils.data.JsonReader;
import hussein.nasereddine.areeba_challenge.views.MediaSliderView;

@SuppressWarnings("FieldCanBeLocal")
public class AlbumActivity extends AppCompatActivity {

    // Views
    private MaterialButton mBtnBack;
    private MediaSliderView mMediaSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initViews();
        initMediaSlider();

        mBtnBack.setOnClickListener(v -> onBackPressed());
    }

    private void initMediaSlider(){
        final List<AlbumModel> albumList = JsonReader.loadJSONAlbumFromRawResource(this);
        if(albumList == null){
            Toast.makeText(this, getString(R.string.msg_album_failed_to_load), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Convert album list to list Recyclerview view model
        List<RecyclerViewVM> vmList = new ArrayList<>();
        for(AlbumModel albumModel : albumList){
            vmList.add(new RecyclerViewVM(new AlbumVM(albumModel)));
        }

        // Set medias
        mMediaSlider.setMediaList(vmList);
    }

    private void initViews(){
        mBtnBack = findViewById(R.id.activity_album_btn_back);
        mMediaSlider = findViewById(R.id.activity_album_mediaslider);
    }
}