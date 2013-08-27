package jp.gr.java_conf.daisy.android_blocks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import jp.gr.java_conf.daisy.android_blocks.bordered_view.BorderdViewActivity;
import jp.gr.java_conf.daisy.android_blocks.swipe_to_dismiss.SwipeToDismissListViewItemsActivity;
import jp.gr.java_conf.daisy.android_blocks.swipe_to_dismiss.SwipeToDismissTextViewsActivity;

/**
 * Main activity  that links to activity to demonstrate my building blocks.
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button) findViewById(R.id.link_swipe_to_dismiss_view))
                .setOnClickListener(new ClickToJumpListener(SwipeToDismissTextViewsActivity.class));
        ((Button) findViewById(R.id.link_swipe_to_dismiss_list_item))
                .setOnClickListener(new ClickToJumpListener(SwipeToDismissListViewItemsActivity.class));
        ((Button) findViewById(R.id.link_bordered_view))
                .setOnClickListener(new ClickToJumpListener(BorderdViewActivity.class));
    }

    private class ClickToJumpListener implements View.OnClickListener {
        private Class<?> clazz;
        public ClickToJumpListener(Class<? extends Activity> clazz) {
            this.clazz = clazz;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, clazz);
            startActivity(intent);
        }
    }
}
