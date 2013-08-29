package jp.gr.java_conf.daisy.android_blocks.swipe_to_dismiss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import jp.gr.java_conf.daisy.android_blocks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Example of using {@link SwipeToDismissTouchListener} for {@link ListView}.
 */
public class SwipeToDismissListViewItemsActivity extends Activity {

    private static final String[] ANIMALS = new String[] {
            "DOG", "CAT", "PIG", "TIGER", "PANDA", "GIRAFFE", "ELEPHANT", "BEAR", "DUCK", "CHICKEN",
            "FOX", "GOAT", "KOALA", "KANGAROO", "LION", "PENGUIN", "DOLPHIN", "RABBIT", "RAT",
            "SHARK", "WHALE", "BRAWN", "DEER"};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_to_dismiss_list_item);
        listView = (ListView) findViewById(R.id.item_removable_list);
        listView.setAdapter(new SwipeToItemRemoveListAdapter());
    }

    // Similar to StableArrayAdapter found in
    //  {@see https://www.youtube.com/watch?v=NewCSg2JKLk}
    // As talked in the video, you need to implement something like this to deal with ListView's
    // view recycling.
    private class SwipeToItemRemoveListAdapter extends ArrayAdapter<String> {
        private HashMap<String, Integer> idMap = new HashMap<String, Integer>();
        private SwipeToDismissTouchListener listener = new SwipeToDismissTouchListener(
                SwipeToDismissListViewItemsActivity.this,
                new SwipeToDismissTouchListener.SwipeAndDismissEventListenerAdapter() {
                    @Override
                    public void onSwipeEndAnimationEnd(View view, boolean removed) {
                        if (removed) {
                            remove(getItem(listView.getPositionForView(view)));
                        }
                    }
                });

        public SwipeToItemRemoveListAdapter() {
            super(SwipeToDismissListViewItemsActivity.this,
                    R.layout.swipe_to_dismiss_list_item,
                    new ArrayList<String>(Arrays.asList(ANIMALS)));
            for (int i = 0; i < ANIMALS.length; i++) {
                idMap.put(ANIMALS[i], i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return idMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (view != convertView) {
                // Add touch listener to every new view to track swipe motion
                view.setOnTouchListener(listener);
            }
            return view;
        }
    }
}
