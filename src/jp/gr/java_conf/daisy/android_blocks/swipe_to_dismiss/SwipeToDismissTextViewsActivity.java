package jp.gr.java_conf.daisy.android_blocks.swipe_to_dismiss;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import jp.gr.java_conf.daisy.android_blocks.R;

/**
 * Example of using {@link SwipeToDismissTouchListener} to dismiss {@link TextView} by swipe.
 */
public class SwipeToDismissTextViewsActivity extends Activity {

    private static final String[] ANIMALS = new String[] {
            "DOG", "CAT", "PIG", "TIGER", "PANDA", "GIRAFFE", "ELEPHANT", "BEAR", "DUCK", "CHICKEN",
            "FOX", "GOAT", "KOALA", "KANGAROO", "LION", "PENGUIN", "DOLPHIN", "RABBIT", "RAT",
            "SHARK", "WHALE", "BRAWN", "DEER"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_to_dismiss_textview);
        ViewGroup parent = (ViewGroup) findViewById(R.id.removable_views_container);
        for (String animal: ANIMALS) {
            TextView textView = new TextView(this);
            textView.setText(animal);
            textView.setOnTouchListener(SwipeToDismissTouchListener.forViewInViewGroup(this));
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setPadding(30, 30, 30, 30);
            parent.addView(textView);
        }
    }
}
