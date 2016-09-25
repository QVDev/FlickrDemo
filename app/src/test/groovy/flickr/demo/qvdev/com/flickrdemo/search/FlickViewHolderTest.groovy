package flickr.demo.qvdev.com.flickrdemo.search

import android.content.Context
import android.view.View
import android.widget.TextView
import flickr.demo.qvdev.com.flickrdemo.search.FlickrViewHolder
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

    @Unroll
    def "Test Flickr ViewHolder toString"(String id, String text, String outcome) {

        given: "Flickr ViewHolder"
        def FlickrViewHolder flickrViewHolder = new FlickrViewHolder(Mock(View.class))
        flickrViewHolder.itemContent = new TextView(Mock(Context.class)) {
            @Override
            CharSequence getText() {
                return text
            }
        }
        flickrViewHolder.itemId = new TextView(Mock(Context.class)) {
            @Override
            CharSequence getText() {
                return id
            }
        }

        expect: "Flickr ViewHolder should have correct outcome based on input"
        flickrViewHolder.toString().contentEquals(outcome)

        where: "id and text input will construct the expected outcome"
        id   | text             | outcome
        "3"  | "Welcome"        | "3: Welcome"
        "1"  | "To data"        | "1: To data"
        "16" | "driven testing" | "16: driven testing"
    }
}
