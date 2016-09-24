package flickr.demo.qvdev.com.flickrdemo;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import flickr.demo.qvdev.com.flickrdemo.model.Photo_;

class FlickrItemRecyclerViewAdapter
        extends RecyclerView.Adapter<FlickrViewHolder> {

    private final List<Photo_> mItems;

    FlickrItemRecyclerViewAdapter(List<Photo_> items) {
        mItems = items;
    }

    @Override
    public FlickrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flickritem_list_content, parent, false);
        return new FlickrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FlickrViewHolder holder, int position) {
        holder.itemId.setText(mItems.get(position).getId());
        holder.itemContent.setText(mItems.get(position).getTitle());
        loadThumbnail(holder.thumbnail, mItems.get(position).getUrl_t());
    }

    private void loadThumbnail(SimpleDraweeView imageView, String url) {
        Uri uri = Uri.parse(url);
        imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}