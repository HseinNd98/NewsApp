package hussein.nasereddine.areeba_challenge.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FeedRecyclerView extends RecyclerView {

    // Number of items left in the bottom to inform listener for reaching bottom
    private int mItemsLeftInBottom = 5;

    // Scroll listener to inform parent when reaching bottom
    private OnApproachBottomListener mOnApproachBottomListener = null;

    public FeedRecyclerView(@NonNull Context context) {
        super(context);
    }

    public FeedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FeedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(@NonNull GridLayoutManager gridLayoutManager){
        // Init layout manager
        setLayoutManager(gridLayoutManager);

        // Init Item Animator
        setItemAnimator(new DefaultItemAnimator());

        // Listen for scrolls
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = gridLayoutManager.getItemCount();
                int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

                if (totalItemCount <= lastVisibleItem + mItemsLeftInBottom) {
                    if (mOnApproachBottomListener != null) {
                        mOnApproachBottomListener.onApproachBottom();
                    }
                }
            }
        });
    }

    public void init(@NonNull LinearLayoutManager linearLayoutManager){
        // Init layout manager
        setLayoutManager(linearLayoutManager);

        // Init Item Animator
        setItemAnimator(new DefaultItemAnimator());

        // Listen for scrolls
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (totalItemCount <= lastVisibleItem + mItemsLeftInBottom) {
                    if (mOnApproachBottomListener != null) {
                        mOnApproachBottomListener.onApproachBottom();
                    }
                }
            }
        });
    }

    public void setItemsBeforeReachingBottom(int itemsBeforeReachingBottom){
        this.mItemsLeftInBottom = itemsBeforeReachingBottom;
    }

    public void setOnApproachBottomListener(@NonNull OnApproachBottomListener listener){
        this.mOnApproachBottomListener = listener;
    }

    public interface OnApproachBottomListener{
        void onApproachBottom();
    }
}
