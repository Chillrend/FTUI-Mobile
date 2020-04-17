package com.rsypj.ftuimobile.extend;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class SmartSIPButton extends AppCompatButton {

    public SmartSIPButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public SmartSIPButton(Context context) {
        super(context);
        init();
    }
    public SmartSIPButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        Typeface font_type=Typeface.createFromAsset(getContext().getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        setTypeface(font_type);
    }
}
