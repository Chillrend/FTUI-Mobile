package com.rsypj.ftuimobile.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Author By dhadotid
 * Date 14/07/2018.
 */
public class PasswordFontfaceWatcher implements TextWatcher {
    private static final int TEXT_VARIATION_PASSWORD =
            (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    private TextView mView;
    Context context;

    /**
     * Register a new watcher for this {@code TextView} to alter the fontface based on the field's contents.
     *
     * <p />
     * This is only necessary for a textPassword field that has a non-empty hint text. A view not meeting these
     * conditions will incur no side effects.
     *
     * @param view
     */
    public static void register(TextView view, Context ctx) {
        PasswordFontfaceWatcher obj = new PasswordFontfaceWatcher(view);

        obj.setContext(ctx);
        final CharSequence hint = view.getHint();
        final int inputType = view.getInputType();
        final boolean isPassword = ((inputType & (EditorInfo.TYPE_MASK_CLASS | EditorInfo.TYPE_MASK_VARIATION))
                == TEXT_VARIATION_PASSWORD);

        if (isPassword && hint != null && !"".equals(hint)) {
            view.addTextChangedListener(obj);

            if (view.length() > 0) {
                obj.setMonospaceFont();
            } else {
                obj.setDefaultFont();
            }
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public PasswordFontfaceWatcher(TextView view) {
        mView = view;
    }

    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        // Not needed
    }

    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        if (s.length() == 0 && after > 0) {
            // Input field went from empty to non-empty
            setMonospaceFont();
        }
    }

    public void afterTextChanged(final Editable s) {
        if (s.length() == 0) {
            // Input field went from non-empty to empty
            setDefaultFont();
        }
    }

    public void setDefaultFont() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        mView.setTypeface(font);
    }

    public void setMonospaceFont() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        mView.setTypeface(font);
    }
}