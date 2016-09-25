package flickr.demo.qvdev.com.flickrdemo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Source copied from from https://gist.github.com/ssinss/e06f12ef66c51252563e
 * adjusted variables scopes and move onLoadMore to Interface
 */
class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private final static int VISIBLE_THRESHOLD = 10; // The minimum amount of items to have below your current scroll position before loading more.

    private final LinearLayoutManager mLinearLayoutManager;
    private final OnLoadMoreListener mListener;

    private int mPreviousTotal = 0; // The total number of items in the dataset after the last load
    private boolean mLoading = true; // True if we are still waiting for the last set of data to load.

    EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, OnLoadMoreListener listener) {
        mLinearLayoutManager = linearLayoutManager;
        mListener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        if (!mLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            // End has been reached

            notifyOnLoadMoreListener();

            mLoading = true;
        }
    }

    private void notifyOnLoadMoreListener() {
        if (mListener != null) {
            mListener.onLoadMore();
        }
    }

    void reset() {
        mPreviousTotal = 0;
        mLoading = false;
    }
}
