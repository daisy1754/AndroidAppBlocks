package jp.gr.java_conf.daisy.android_blocks.right_checkbox;

import android.app.Activity;
import android.os.Bundle;
import jp.gr.java_conf.daisy.android_blocks.R;

/**
 * Example of using {@link RightCheckbox}
 */
public class RightCheckboxActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_checkbox);
        ((RightCheckbox) findViewById(R.id.activity_right_checkbox_no_text))
                .setText("Text (Set by program)");
    }
}
