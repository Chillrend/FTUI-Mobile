package com.rsypj.ftuimobile.extend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class SmartSIPEditText extends AppCompatEditText{

    public SmartSIPEditText(Context context){
        super(context);
        init(context);
    }

    public SmartSIPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmartSIPEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

    public void init(Context context){
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        this.setTypeface(face);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Trajan-Pro-Regular.ttf");
        super.setTypeface(tf);
    }
}
