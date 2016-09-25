package flickr.demo.qvdev.com.flickrdemo

import spock.lang.Specification
import spock.lang.Unroll

class InfiniteScrollTest extends Specification {

    @Unroll
    def "Test infinite scrolling"() {

        given: "FlickrListActivity"
        def FlickrItemListActivity listActivity = new FlickrItemListActivity();

        expect: "Initial page should be 1"
        listActivity.mCurrentPage == 1

        when: "Items loaded page should be increased"
        listActivity.setNextPage()

        then: "Page should be 2"
        listActivity.mCurrentPage == 2
    }
}
