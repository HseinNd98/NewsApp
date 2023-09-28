package hussein.nasereddine.areeba_challenge.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.adapters.RecyclerViewAdapter;
import hussein.nasereddine.areeba_challenge.models.view_models.RecyclerViewVM;

@SuppressWarnings("FieldCanBeLocal")
public class MediaSliderView extends RelativeLayout {
    private final Context context;

    // Views
    private ViewPager2 mViewPager;
    private TextView mLabelIndicator;

    // Adapter
    private RecyclerViewAdapter mAdapter;
    private final List<RecyclerViewVM> list = new ArrayList<>();

    public MediaSliderView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public MediaSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public MediaSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){
        final View rootView = LayoutInflater.from(context).inflate(R.layout.view_media_slider, this, true);

        // Init views
        mViewPager = rootView.findViewById(R.id.view_media_slider_view_pager);
        mLabelIndicator = rootView.findViewById(R.id.view_media_slider_label_number);

        initMediaSliderAdapter();
    }

    private void initMediaSliderAdapter(){
        mAdapter = new RecyclerViewAdapter(context, list);
        mViewPager.setAdapter(mAdapter);

        // On page selected
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Update label number
                mLabelIndicator.setText(String.format("%s/%s", (position + 1), list.size()));
            }
        });
    }

    public void setMediaList(@NonNull List<RecyclerViewVM> mediaList){
        int previousSize = this.list.size();
        int newSize = mediaList.size();

        // Remove items if necessary
        if (previousSize > newSize) {
            mAdapter.notifyItemRangeRemoved(newSize, previousSize - newSize);
        }

        // Add new items
        if (newSize > previousSize) {
            mAdapter.notifyItemRangeInserted(previousSize, newSize - previousSize);
        }

        this.list.clear();
        this.list.addAll(mediaList);
    }
}
