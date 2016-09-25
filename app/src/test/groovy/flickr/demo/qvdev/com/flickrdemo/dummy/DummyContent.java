package flickr.demo.qvdev.com.flickrdemo.dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * For testing purpose
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") // Used during testing
    private static final List<DummyItem> ITEMS = new ArrayList<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position);
    }

    /**
     * A dummy item representing a piece of content.
     */
    private static class DummyItem {
        @SuppressWarnings("unused") // Used for testing
        final String id;
        final String content;

        DummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
