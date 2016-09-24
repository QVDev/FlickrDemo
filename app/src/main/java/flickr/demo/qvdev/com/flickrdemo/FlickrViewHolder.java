package flickr.demo.qvdev.com.flickrdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import flickr.demo.qvdev.com.flickrdemo.dummy.DummyContent;

class FlickrViewHolder extends RecyclerView.ViewHolder {

    final View mView;
    final TextView mIdView;
    final TextView mContentView;
    DummyContent.DummyItem mItem;

    FlickrViewHolder(View view) {
        super(view);
        mView = view;
        mIdView = (TextView) view.findViewById(R.id.id);
        mContentView = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }
}
