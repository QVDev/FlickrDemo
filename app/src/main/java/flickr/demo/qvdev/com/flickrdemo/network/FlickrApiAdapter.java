package flickr.demo.qvdev.com.flickrdemo.network;


import flickr.demo.qvdev.com.flickrdemo.model.PhotoDetail;
import flickr.demo.qvdev.com.flickrdemo.model.SearchResult;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class FlickrApiAdapter {

    private FlickrService mFlickerService;

    public FlickrApiAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.flickr.com/services/")
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
