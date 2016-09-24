package flickr.demo.qvdev.com.flickrdemo

import spock.lang.Specification
import spock.lang.Unroll

class DetailsFragmentTest extends Specification {

    @Unroll
    def "DetailsFragment loading items testing"() {
        given: "DetailsFragment"
        def FlickrItemDetailFragment detailFragment = new FlickrItemDetailFragment();

        when: "Load items is called"
        detailFragment.loadMockedItemDetails()

        then: "Item should not be null"
        detailFragment.mItem
    }
}
