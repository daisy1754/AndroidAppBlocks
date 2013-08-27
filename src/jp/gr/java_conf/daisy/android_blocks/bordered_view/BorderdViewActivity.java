package jp.gr.java_conf.daisy.android_blocks.bordered_view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import jp.gr.java_conf.daisy.android_blocks.R;

/**
 * Example for using {@link BorderedView}.
 */
public class BorderdViewActivity extends Activity {
    BorderedView<ImageView> borderedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bordered_view);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.android_robot_200);
        borderedImageView = new BorderedView(imageView);
        ((ViewGroup) findViewById(R.id.bordered_view_activity_parent))
                .addView(borderedImageView, 0);
        ((EditText) findViewById(R.id.border_color_rgb))
                .addTextChangedListener(new TextWatcherAdapter() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            borderedImageView.setBorderColor(Color.parseColor(s.toString()));
                        } catch (IllegalArgumentException e) {
                            // Do nothing
                        }
                    }
                });
        ((EditText) findViewById(R.id.border_width_px))
                .addTextChangedListener(new TextWatcherAdapter() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            int width = Integer.parseInt(s.toString());
                            if (width >= 0) {
                                borderedImageView.setBorderWidth(width);
                            }
                        } catch (NumberFormatException e) {
                            // Do nothing.
                        }
                    }
                });
    }

    private static class TextWatcherAdapter implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) { }
    }
}
