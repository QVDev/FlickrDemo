package flickr.demo.qvdev.com.flickrdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import flickr.demo.qvdev.com.flickrdemo.dummy.DummyContent;

class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<FlickrViewHolder> {

    private final List<DummyContent.DummyItem> mValues;
    private final FragmentManager mFragmentManager;
    private final boolean mIsTwoPane;

    SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items, FragmentManager fragmentManager, boolean isTwoPane) {
        mValues = items;
        mFragmentManager = fragmentManager;
        mIsTwoPane = isTwoPane;
    }

    @Override
    public FlickrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flickritem_list_content, parent, false);
        return new FlickrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FlickrViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(FlickrItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    FlickrItemDetailFragment fragment = new FlickrItemDetailFragment();
                    fragment.setArguments(arguments);
                    mFragmentManager.beginTransaction()
                            .replace(R.id.flickritem_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, FlickrItemDetailActivity.class);
                    intent.putExtra(FlickrItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}