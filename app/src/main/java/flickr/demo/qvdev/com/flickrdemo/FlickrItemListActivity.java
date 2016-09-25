package flickr.demo.qvdev.com.flickrdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import flickr.demo.qvdev.com.flickrdemo.model.Photo_;
import flickr.demo.qvdev.com.flickrdemo.model.Photos;
import flickr.demo.qvdev.com.flickrdemo.model.SearchResult;
import flickr.demo.qvdev.com.flickrdemo.network.FlickrApiAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * An activity representing a list of FlickrItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FlickrItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FlickrItemListActivity extends AppCompatActivity implements OnLoadMoreListener, SearchView.OnQueryTextListener {

    private static final String SEARCH_RESULT = "search_result";
    private static final String SEARCH_TERM = "search_term";
    private static final String CURRENT_PAGE = "current_page";

    private List<Photo_> mFlickrItems = new ArrayList<>();
    private final FlickrApiAdapter mFlickrApiAdapter = new FlickrApiAdapter();

    private RecyclerView mRecyclerView;
    private int mCurrentPage = 1;
    private String mSearchTerm;
    private EndlessRecyclerOnScrollListener mEndlessScrollListener;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickritem_list);

        Fresco.initialize(this);
        setupToolbar();
        setupRecyclerView();
        if (savedInstanceState == null) {
            loadFlickrItems();
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE);
        mSearchTerm = savedInstanceState.getString(SEARCH_TERM);

        Gson gson = new Gson();
        Photos photos = gson.fromJson(savedInstanceState.getString(SEARCH_RESULT), Photos.class);
        updateItemsInList(photos.getPhoto());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        Photos photos = new Photos();
        photos.setPhoto(mFlickrItems);
        String savedResultState = gson.toJson(photos);

        outState.putString(SEARCH_RESULT, savedResultState);
        outState.putString(SEARCH_TERM, mSearchTerm);
        outState.putInt(CURRENT_PAGE, mCurrentPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.flickritem_list);
        assert mRecyclerView != null;
        mRecyclerView.setAdapter(new FlickrItemRecyclerViewAdapter(mFlickrItems));

        // For infinite loading
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mEndlessScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager, this);
        mRecyclerView.addOnScrollListener(mEndlessScrollListener);
    }

    private void loadFlickrItems() {
        if (mSearchTerm != null) {
            final Observable<SearchResult> search = mFlickrApiAdapter.SearchImages(mSearchTerm, mCurrentPage);

            search.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<SearchResult>() {
                        @Override
                        public void call(final SearchResult searchResult) {
                            updateItemsInList(searchResult.getPhotos().getPhoto());
                        }
                    });
        }
    }

    private void updateItemsInList(List<Photo_> searchResult) {
        mFlickrItems.addAll(searchResult);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setNextPage() {
        mCurrentPage++;
    }

    @Override
    public void onLoadMore() {
        setNextPage();
        loadFlickrItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    /**
     * The onclick is set in flickritem_list_content.xml
     *
     * @param view The view that is clicked
     */
    public void flickrItemClicked(View view) {
        // mIsTwoPane is for larger screen w300 it will have a detail view in same screen
        boolean isTwoPane = findViewById(R.id.flickritem_detail_container) != null;

        // Get the matching FlickrItem from the recyclerView
        Photo_ item = mFlickrItems.get(mRecyclerView.getChildAdapterPosition(view));
        if (isTwoPane) {
            showDetailsInPane(item);
        } else {
            showDetailsInActivity(item);
        }
    }

    // Larger screens w300 will replace the fragment for detail view
    private void showDetailsInPane(Photo_ item) {
        Bundle arguments = new Bundle();
        arguments.putString(FlickrItemDetailFragment.ARG_ITEM_ID, item.getId());
        arguments.putString(FlickrItemDetailFragment.ARG_ITEM_MEDIUM_URL, item.getUrl_m());

        FlickrItemDetailFragment fragment = new FlickrItemDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flickritem_detail_container, fragment)
                .commit();
    }

    // Smaller screens < w300 will open a new activity for detail view
    private void showDetailsInActivity(Photo_ item) {
        Intent intent = new Intent(this, FlickrItemDetailActivity.class);
        intent.putExtra(FlickrItemDetailFragment.ARG_ITEM_ID, item.getId());
        intent.putExtra(FlickrItemDetailFragment.ARG_ITEM_MEDIUM_URL, item.getUrl_m());

        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        resetStates();
        mSearchTerm = query;
        loadFlickrItems();
        return true;
    }

    private void resetStates() {
        mFlickrItems.clear();
        mCurrentPage = 1;

        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.reset();
        }
        if (mSearchView != null) {
            mSearchView.clearFocus();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
