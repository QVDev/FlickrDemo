package flickr.demo.qvdev.com.flickrdemo.network;

import flickr.demo.qvdev.com.flickrdemo.model.PhotoDetail;
import flickr.demo.qvdev.com.flickrdemo.model.SearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrService {

    @GET("rest/?method=flickr.photos.search&api_key=be00e7f9fb70df90a8037ed1e3ea2e66&per_page=5&format=json&nojsoncallback=1")
    Observable<SearchResult> searchPhotos(@Query("text") String searchString, @Query("page") int currentPage);

    @GET("rest/?method=flickr.photos.getInfo&api_key=be00e7f9fb70df90a8037ed1e3ea2e66&format=json&nojsoncallback=1")
    Observable<PhotoDetail> getPhotoDetail(@Query("photo_id") String photoId);
}
