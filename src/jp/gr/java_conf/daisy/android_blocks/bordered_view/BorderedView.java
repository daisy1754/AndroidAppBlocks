package jp.gr.java_conf.daisy.android_blocks.bordered_view;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * {@link View} with uni-color, same width border.
 */
public class BorderedView<T extends View> extends LinearLayout {
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_BORDER_WIDTH = 1;
    private T innerView;

    public BorderedView(T contentView) {
        super(contentView.getContext());
        setGravity(Gravity.CENTER_HORIZONTAL);
        setBorderColor(DEFAULT_BORDER_COLOR);
        setBorderWidth(DEFAULT_BORDER_WIDTH);
        setContentView(contentView);
        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public BorderedView setContentView(T contentView) {
        return setContentView(contentView, Color.WHITE);
    }

    public BorderedView setContentView(T contentView, int backgroundColor) {
        innerView = contentView;
        innerView.setBackgroundColor(backgroundColor);
        addView(innerView);
        return this;
    }

    public BorderedView setBorderColor(int borderColor) {
        setBackgroundColor(borderColor);
        return this;
    }

    public BorderedView setBorderWidth(int borderWidth) {
        setPadding(borderWidth, borderWidth, borderWidth, borderWidth);
        return this;
    }
}
