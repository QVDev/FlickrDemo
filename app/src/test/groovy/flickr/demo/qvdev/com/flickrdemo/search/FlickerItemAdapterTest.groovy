package flickr.demo.qvdev.com.flickrdemo.search

import flickr.demo.qvdev.com.flickrdemo.dummy.DummyContent
import flickr.demo.qvdev.com.flickrdemo.search.FlickrItemRecyclerViewAdapter
import spock.lang.Specification
import spock.lang.Unroll

class FlickerItemAdapterTest extends Specification {

    @Unroll
    def "Test Flickr adapter"() {

        given: "The Flickr adapter"
        def FlickrItemRecyclerViewAdapter adapter = new FlickrItemRecyclerViewAdapter(DummyContent.ITEMS)

        expect: "The items should not be null"
        adapter.mItems

        when: "Calling getItemCount"
        def int adapterCount = adapter.getItemCount()

        then: "The getItemsCount should return the dummy exact size"
        adapterCount == DummyContent.ITEMS.size()
    }
}
