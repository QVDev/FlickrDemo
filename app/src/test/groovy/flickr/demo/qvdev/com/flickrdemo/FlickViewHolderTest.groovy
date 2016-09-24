package flickr.demo.qvdev.com.flickrdemo

import android.view.View
import spock.lang.Specification
import spock.lang.Unroll

class FlickViewHolderTest extends Specification {

    @Unroll
    def "Test Flickr ViewHolder init"() {

        given: "Flickr ViewHolder"
        def FlickrViewHolder flickrViewHolder = new FlickrViewHolder(Mock(View.class))

        expect: "Flickr ViewHolder should not be null"
        flickrViewHolder
    }
}
