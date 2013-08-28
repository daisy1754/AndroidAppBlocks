package jp.gr.java_conf.daisy.android_blocks.right_checkbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;
import jp.gr.java_conf.daisy.android_blocks.R;

/**
 * Similar to {@link CheckBox} which can put the label on left side and checkbox on right side.
 */
public class RightCheckbox extends FrameLayout implements Checkable {
    private CheckBox checkbox;
    private TextView label;

    public RightCheckbox(Context context) {
        this(context, null);
    }

    public RightCheckbox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.right_checkbox, this);
        checkbox = (CheckBox) findViewById(R.id.right_checkbox_check_body);
        label = (TextView) findViewById(R.id.right_checkbox_label);
        setInitialValue(attrs);
        label.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox.toggle();
            }
        });
    }

    public void setText(CharSequence charSequence) {
        label.setText(charSequence);
    }

    @Override
    public void setChecked(boolean checked) {
        setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return isChecked();
    }

    @Override
    public void toggle() {
        checkbox.toggle();
    }

    private void setInitialValue(AttributeSet attrs) {
        String text = attrs.getAttributeValue(
                getResources().getString(R.string.android_resource_namespace), "text");
        boolean checked = attrs.getAttributeBooleanValue(
                getResources().getString(R.string.android_resource_namespace), "checked", false);
        label.setText(text);
        checkbox.setChecked(checked);
    }
}
