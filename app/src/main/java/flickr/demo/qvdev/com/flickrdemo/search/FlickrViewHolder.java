package flickr.demo.qvdev.com.flickrdemo.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import flickr.demo.qvdev.com.flickrdemo.R;

class FlickrViewHolder extends RecyclerView.ViewHolder {

    //Suppress for testing possibilities
    @SuppressWarnings("CanBeFinal")
    TextView itemId;
    @SuppressWarnings("CanBeFinal")
    TextView itemContent;

    final SimpleDraweeView thumbnail;

    FlickrViewHolder(View view) {
        super(view);
        itemId = (TextView) view.findViewById(R.id.id);
        itemContent = (TextView) view.findViewById(R.id.content);
        thumbnail = (SimpleDraweeView) view.findViewById(R.id.thumbnail);
    }

    @Override
    public String toString() {
        return itemId.getText().toString() + ": " + itemContent.getText().toString();
    }
}
