package flickr.demo.qvdev.com.flickrdemo

import android.support.v7.widget.LinearLayoutManager
import spock.lang.Specification
import spock.lang.Unroll

class InfiniteScrollTest extends Specification {

    @Unroll
    def "Test infinite scrolling"() {

        given: "FlickrListActivity"
        def FlickrItemListActivity listActivity = new FlickrItemListActivity()

        expect: "Initial page should be 1"
        listActivity.mCurrentPage == 1

        when: "Items loaded page should be increased"
        listActivity.setNextPage()

        then: "Page should be 2"
        listActivity.mCurrentPage == 2
    }

    @Unroll
    def "Endless RecyclerView OnScrollListener with OnLoadMoreListener"() {

        given: "EndlessRecyclerOnScrollListener"
        def EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(Mock(LinearLayoutManager.class), Mock(OnLoadMoreListener.class))

        when: "Notify load more is called"
        scrollListener.notifyOnLoadMoreListener()

        then: "The listener should be called"
        1 * scrollListener.mListener.onLoadMore()
    }

    @Unroll
    def "Endless RecyclerView OnScrollListener without"() {

        given: "EndlessRecyclerOnScrollListener"
        def EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener(Mock(LinearLayoutManager.class), null)

        when: "Notify load more is called"
        scrollListener.notifyOnLoadMoreListener()

        then: "The listener should not be called as it is null"
        0 * scrollListener.mListener.onLoadMore()
    }
}