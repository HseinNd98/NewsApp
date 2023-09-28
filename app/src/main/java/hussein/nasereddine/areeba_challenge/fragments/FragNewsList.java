package hussein.nasereddine.areeba_challenge.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.activities.NewsDetailsActivity;
import hussein.nasereddine.areeba_challenge.adapters.RecyclerViewAdapter;
import hussein.nasereddine.areeba_challenge.enums.ArticleLanguage;
import hussein.nasereddine.areeba_challenge.enums.RecyclerViewItemType;
import hussein.nasereddine.areeba_challenge.interfaces.ArticlesListener;
import hussein.nasereddine.areeba_challenge.interfaces.ItemClickListener;
import hussein.nasereddine.areeba_challenge.models.data_models.articles.ArticleModel;
import hussein.nasereddine.areeba_challenge.models.view_models.RecyclerViewVM;
import hussein.nasereddine.areeba_challenge.models.view_models.articles.ArticleLoaderVM;
import hussein.nasereddine.areeba_challenge.models.view_models.articles.ArticleVM;
import hussein.nasereddine.areeba_challenge.utils.network.requests.ArticlesRequest;
import hussein.nasereddine.areeba_challenge.views.FeedRecyclerView;

@SuppressWarnings("FieldCanBeLocal")
public class FragNewsList extends Fragment implements FeedRecyclerView.OnApproachBottomListener, ItemClickListener, ArticlesListener {
    public final static String TAG = "FragNewsListTag";

    // Main
    private Context context;
    private View mMainView;

    // Views
    private SwipeRefreshLayout mRefresh;
    private FeedRecyclerView mRV;

    // Adapter
    private final List<RecyclerViewVM> list = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;
    private boolean isRequesting = false, canGetMoreData = true;

    // Configs
    private final int COUNT_PER_PAGE = 10;
    private final String REQUEST_KEYWORD_EN = "Cryptocurrencies";
    private final String REQUEST_KEYWORD_AR = "العملات الرقمية";
    private ArticleLanguage mLanguage = ArticleLanguage.ENGLISH;

    private FragNewsList(){}

    public static FragNewsList newInstance(@NonNull ArticleLanguage language){
        FragNewsList fragment = new FragNewsList();
        Bundle args = new Bundle();
        args.putString("lang", language.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mLanguage = ArticleLanguage.valueOf(getArguments().getString("lang"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.frag_news_list, container, false);
        return mMainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initAdapter();

        mRefresh.setOnRefreshListener(this::refresh);
    }

    private void request(){
        if(!isRequesting && canGetMoreData){
            isRequesting = true;

            // Add loader
            list.add(new RecyclerViewVM(new ArticleLoaderVM()));
            mAdapter.notifyItemInserted(list.size());

            // Send request
            new ArticlesRequest.Builder()
                    .setCount(10)
                    .setPage(getRequestPage())
                    .setKeyword(mLanguage == ArticleLanguage.ENGLISH ? REQUEST_KEYWORD_EN : REQUEST_KEYWORD_AR)
                    .GET(context, this);
        }
    }

    private int getRequestPage(){
        return ((int) list.size() / COUNT_PER_PAGE) + 1;
    }

    private void refresh(){
        isRequesting = false;
        canGetMoreData = true;
        mAdapter.clear();
        request();
    }

    private void initAdapter(){
        mAdapter = new RecyclerViewAdapter(context, list);
        mRV.init(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        mRV.setItemsBeforeReachingBottom(10);
        mRV.setOnApproachBottomListener(this);
        mRV.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        request();
    }

    private void initViews(){
        mRefresh = mMainView.findViewById(R.id.frag_news_list_refresh);
        mRV = mMainView.findViewById(R.id.frag_news_list_rv);
    }

    @Override
    public void onApproachBottom() {
        request();
    }

    @Override
    public void onClick(@NonNull RecyclerViewVM model, int position, int type) {
        final ArticleVM articleVM = model.getArticleVM();
        if(articleVM != null){
            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("id", articleVM.getArticle().getId());
            intent.putExtra("lang", articleVM.getLanguage().name());
            startActivity(intent);
        }
    }

    @Override
    public void onArticlesReceived(@NonNull List<ArticleModel> articles, int page, int count, int pages, int totalResults) {
        // Remove loader
        mAdapter.removeItemOfType(RecyclerViewItemType.ARTICLES_LOADER);

        for(ArticleModel article : articles){
            list.add(new RecyclerViewVM(new ArticleVM(article, mLanguage)));
            mAdapter.notifyItemInserted(list.size());
        }

        isRequesting = false;
        canGetMoreData = (count < totalResults);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onArticlesFailure(@NonNull String message) {
        // Remove loader
        mAdapter.removeItemOfType(RecyclerViewItemType.ARTICLES_LOADER);

        Snackbar.make(mRefresh, message, 4000).show();
        isRequesting = false;
        mRefresh.setRefreshing(false);
    }
}
