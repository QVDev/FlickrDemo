package flickr.demo.qvdev.com.flickrdemo.network;

import flickr.demo.qvdev.com.flickrdemo.model.PhotoDetail;
import flickr.demo.qvdev.com.flickrdemo.model.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static flickr.demo.qvdev.com.flickrdemo.network.FlickrApiAdapter.FLICKR_PHOTO_DETAIL;
import static flickr.demo.qvdev.com.flickrdemo.network.FlickrApiAdapter.FLICKR_SEARCH;

interface FlickrService {

    @GET(FLICKR_SEARCH)
    Observable<SearchResult> searchPhotos(@Query("text") String searchString, @Query("page") int currentPage);

    @GET(FLICKR_PHOTO_DETAIL)
    Observable<PhotoDetail> getPhotoDetail(@Query("photo_id") String photoId);
}
