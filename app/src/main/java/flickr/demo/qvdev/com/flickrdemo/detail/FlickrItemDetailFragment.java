package flickr.demo.qvdev.com.flickrdemo.detail;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import flickr.demo.qvdev.com.flickrdemo.R;
import flickr.demo.qvdev.com.flickrdemo.model.PhotoDetail;
import flickr.demo.qvdev.com.flickrdemo.network.FlickrApiAdapter;
import flickr.demo.qvdev.com.flickrdemo.search.FlickrItemListActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A fragment representing a single FlickrItem detail screen.
 * This fragment is either contained in a {@link FlickrItemListActivity}
 * in two-pane mode (on tablets) or a {@link FlickrItemDetailActivity}
 * on handsets.
 */
public class FlickrItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_MEDIUM_URL = "item_medium_url";
    private static final String PHOTO_DETAILS = "photo_details";
    private static final String PHOTO_TITLE = "photo_title";

    private SimpleDraweeView mImageView;
    private TextView mPhotoDetails;
    private String mMediumUrl;
    private String mTitle;

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

        if (getArguments().containsKey(ARG_ITEM_ID) && getArguments().containsKey(ARG_ITEM_MEDIUM_URL)) {
            String itemId = getArguments().getString(ARG_ITEM_ID);
            mMediumUrl = getArguments().getString(ARG_ITEM_MEDIUM_URL);
            if (savedInstanceState == null) {
                loadPhotoDetails(itemId);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(PHOTO_DETAILS, mPhotoDetails.getText().toString());
        outState.putString(PHOTO_TITLE, mTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoDetails.setText(savedInstanceState.getString(PHOTO_DETAILS));
            itemDetailsLoaded(savedInstanceState.getString(PHOTO_TITLE));
        }
    }

    private void loadImageUrl() {
        Uri uri = Uri.parse(mMediumUrl);
        mImageView.setImageURI(uri);
    }

    private void loadPhotoDetails(String photoId) {
        final FlickrApiAdapter flickrApiAdapter = new FlickrApiAdapter();
        final Observable<PhotoDetail> photo = flickrApiAdapter.getPhotoDetails(photoId);

        photo.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsafeSubscribe(new Subscriber<PhotoDetail>() {
                    @Override
                    public void onCompleted() {
                        // ;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isVisible()) {
                            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNext(PhotoDetail photoDetail) {
                        if (isVisible()) {
                            itemDetailsLoaded(photoDetail.getPhoto().getTitle().get_content());
                            loadPhotoDetails(photoDetail);
                        }
                    }
                });
    }

    private void loadPhotoDetails(PhotoDetail photo) {
        String date = photo.getPhoto().getDateuploaded();
        String description = photo.getPhoto().getDescription().get_content();

        mPhotoDetails.setText(getString(R.string.photo_views, date, description));
    }

    private void itemDetailsLoaded(final String title) {
        Activity activity = this.getActivity();
        if (activity != null) {
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                mTitle = title;
                appBarLayout.setTitle(title);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flickritem_detail, container, false);

        mImageView = (SimpleDraweeView) rootView.findViewById(R.id.medium_image);
        loadImageUrl();
        mPhotoDetails = (TextView) rootView.findViewById(R.id.photo_details);

        return rootView;
    }
}
