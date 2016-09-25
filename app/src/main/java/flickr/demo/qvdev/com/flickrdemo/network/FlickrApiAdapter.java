package flickr.demo.qvdev.com.flickrdemo.network;


import flickr.demo.qvdev.com.flickrdemo.model.PhotoDetail;
import flickr.demo.qvdev.com.flickrdemo.model.SearchResult;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class FlickrApiAdapter {

    static final String FLICKR_SEARCH = "rest/?method=flickr.photos.search&api_key=be00e7f9fb70df90a8037ed1e3ea2e66&per_page=5&format=json&nojsoncallback=1&extras=url_m,url_t,url_o";
    static final String FLICKR_PHOTO_DETAIL = "rest/?method=flickr.photos.getInfo&api_key=be00e7f9fb70df90a8037ed1e3ea2e66&format=json&nojsoncallback=1&";

    private static final String FLICKR_BASE_URL = "https://api.flickr.com/services/";
    
    private final FlickrService mFlickerService;

    public FlickrApiAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(FLICKR_BASE_URL)
                .build();

        mFlickerService = retrofit.create(FlickrService.class);
    }

    public Observable<SearchResult> SearchImages(String searchQuery, int currentPage) {
        return mFlickerService.searchPhotos(searchQuery, currentPage);
    }

    public Observable<PhotoDetail> getPhotoDetails(String photoId) {
        return mFlickerService.getPhotoDetail(photoId);
    }
}
