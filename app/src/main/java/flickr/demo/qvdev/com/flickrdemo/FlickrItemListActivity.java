package flickr.demo.qvdev.com.flickrdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import flickr.demo.qvdev.com.flickrdemo.model.Photo_;
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
public class FlickrItemListActivity extends AppCompatActivity implements OnLoadMoreListener {

    private final List<Photo_> mFlickrItems = new ArrayList<>();
    private final FlickrApiAdapter mFlickrApiAdapter = new FlickrApiAdapter();

    private RecyclerView mRecyclerView;
    private int mCurrentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickritem_list);

        Fresco.initialize(this);
        setupToolbar();
        setupRecyclerView();
        loadFlickrItems();
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
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager, this));
    }

    private void loadFlickrItems() {
        final Observable<SearchResult> search = mFlickrApiAdapter.SearchImages("Skyline", mCurrentPage);

        search.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SearchResult>() {
                    @Override
                    public void call(final SearchResult searchResult) {
                        updateItemsInList(searchResult);
                        setNextPage();
                    }
                });
    }

    private void updateItemsInList(SearchResult searchResult) {
        mFlickrItems.addAll(searchResult.getPhotos().getPhoto());
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setNextPage() {
        mCurrentPage++;
    }

    @Override
    public void onLoadMore() {
        loadFlickrItems();
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
}
