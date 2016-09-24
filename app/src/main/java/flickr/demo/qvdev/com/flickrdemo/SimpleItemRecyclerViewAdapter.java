package flickr.demo.qvdev.com.flickrdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import flickr.demo.qvdev.com.flickrdemo.dummy.DummyContent;

class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<FlickrViewHolder> {

    private final List<DummyContent.DummyItem> mItems;

    SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
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
        holder.mItem = mItems.get(position);
        holder.mIdView.setText(mItems.get(position).id);
        holder.mContentView.setText(mItems.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}