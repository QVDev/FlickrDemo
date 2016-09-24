package flickr.demo.qvdev.com.flickrdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import flickr.demo.qvdev.com.flickrdemo.dummy.DummyContent;

/**
 * An activity representing a list of FlickrItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FlickrItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FlickrItemListActivity extends AppCompatActivity {

    private List<DummyContent.DummyItem> mFlickrItems = DummyContent.ITEMS;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickritem_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecyclerView = (RecyclerView) findViewById(R.id.flickritem_list);
        assert mRecyclerView != null;

        setupRecyclerView(mRecyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new FlickrItemRecyclerViewAdapter(mFlickrItems));
    }


    public void flickrItemClicked(View view) {
        // mIsTwoPane is for larger screen w300 it will have a detail view in same screen
        boolean isTwoPane = findViewById(R.id.flickritem_detail_container) != null;

        // Get the matching FlickrItem from the recyclerView
        DummyContent.DummyItem item = mFlickrItems.get(mRecyclerView.getChildAdapterPosition(view));
        if (isTwoPane) {
            showDetailsInPane(item);
        } else {
            showDetailsInActivity(item);
        }
    }

    // Larger screens w300 will replace the fragment for detail view
    private void showDetailsInPane(DummyContent.DummyItem item) {
        Bundle arguments = new Bundle();
        arguments.putString(FlickrItemDetailFragment.ARG_ITEM_ID, item.id);
        FlickrItemDetailFragment fragment = new FlickrItemDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flickritem_detail_container, fragment)
                .commit();
    }

    // Smaller screens < w300 will open a new activity for detail view
    private void showDetailsInActivity(DummyContent.DummyItem item) {
        Intent intent = new Intent(this, FlickrItemDetailActivity.class);
        intent.putExtra(FlickrItemDetailFragment.ARG_ITEM_ID, item.id);

        startActivity(intent);
    }
}
