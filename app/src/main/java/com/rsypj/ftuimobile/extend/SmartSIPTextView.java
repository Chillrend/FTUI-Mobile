package com.rsypj.ftuimobile.extend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class SmartSIPTextView extends AppCompatTextView {

    public SmartSIPTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        this.setTypeface(face);
    }

    public SmartSIPTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        this.setTypeface(face);
    }

    public SmartSIPTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
}
