package jp.gr.java_conf.daisy.android_blocks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import jp.gr.java_conf.daisy.android_blocks.bordered_view.BorderdViewActivity;
import jp.gr.java_conf.daisy.android_blocks.right_checkbox.RightCheckboxActivity;
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
        addLinkToActivity(R.id.link_swipe_to_dismiss_view, SwipeToDismissTextViewsActivity.class);
        addLinkToActivity(
                R.id.link_swipe_to_dismiss_list_item, SwipeToDismissListViewItemsActivity.class);
        addLinkToActivity(R.id.link_bordered_view, BorderdViewActivity.class);
        addLinkToActivity(R.id.link_right_checkbox, RightCheckboxActivity.class);
    }

    private void addLinkToActivity(int viewResourceId, Class<? extends Activity> clazz) {
        findViewById(viewResourceId).setOnClickListener(new ClickToJumpListener(clazz));
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
