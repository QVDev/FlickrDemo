package flickr.demo.qvdev.com.flickrdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import flickr.demo.qvdev.com.flickrdemo.model.Photo;
import flickr.demo.qvdev.com.flickrdemo.model.Title;

/**
 * A fragment representing a single FlickrItem detail screen.
 * This fragment is either contained in a {@link FlickrItemListActivity}
 * in two-pane mode (on tablets) or a {@link FlickrItemDetailActivity}
 * on handsets.
 */
public class FlickrItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private Photo mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FlickrItemDetailFragment() {
        // ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            loadMockedItemDetails(getArguments().getString(ARG_ITEM_ID));
        }
    }

    private void loadMockedItemDetails(String photoId) {
        mItem = new Photo();
        mItem.setId(photoId);
        Title title = new Title();
        title.set_content(photoId);
        mItem.setTitle(title);
        itemDetailsLoaded();
    }

    private void itemDetailsLoaded() {
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flickritem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.flickritem_detail)).setText(mItem.getTitle().get_content());
        }

        return rootView;
    }
}
