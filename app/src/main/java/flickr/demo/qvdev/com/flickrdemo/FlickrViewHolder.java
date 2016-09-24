package flickr.demo.qvdev.com.flickrdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class FlickrViewHolder extends RecyclerView.ViewHolder {

    final TextView mIdView;
    final TextView mContentView;

    FlickrViewHolder(View view) {
        super(view);
        mIdView = (TextView) view.findViewById(R.id.id);
        mContentView = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }
}
