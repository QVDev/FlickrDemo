package flickr.demo.qvdev.com.flickrdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import flickr.demo.qvdev.com.flickrdemo.model.PhotoDetail;
import flickr.demo.qvdev.com.flickrdemo.network.FlickrApiAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A fragment representing a single FlickrItem detail screen.
 * This fragment is either contained in a {@link FlickrItemListActivity}
 * in two-pane mode (on tablets) or a {@link FlickrItemDetailActivity}
 * on handsets.
 */
public class FlickrItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private TextView mContent;

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
            loadPhotoDetails(getArguments().getString(ARG_ITEM_ID));
        }
    }

    private void loadPhotoDetails(String photoId) {
        final FlickrApiAdapter flickrApiAdapter = new FlickrApiAdapter();
        final Observable<PhotoDetail> photo = flickrApiAdapter.getPhotoDetails(photoId);

        photo.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhotoDetail>() {
                    @Override
                    public void call(@NonNull final PhotoDetail photo) {
                        itemDetailsLoaded(photo);
                    }
                });
    }

    private void itemDetailsLoaded(final PhotoDetail photo) {
        Activity activity = this.getActivity();
        if (activity != null) {
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(photo.getPhoto().getTitle().get_content());
            }

            mContent.setText(photo.getPhoto().getUrls().getUrl().get(0).get_content());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flickritem_detail, container, false);

        mContent = (TextView) rootView.findViewById(R.id.flickritem_detail);

        return rootView;
    }
}
