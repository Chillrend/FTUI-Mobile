package com.rsypj.ftuimobile.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.internal.BaselineLayout;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by dhadotid on 09/04/2018.
 */

public class BottomNavigationViewHelper {

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView vie, Context context){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)vie.getChildAt(0);
        View itemTitle;

        try{
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++){
                BottomNavigationItemView item = (BottomNavigationItemView)menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        }catch (NoSuchFieldException e){

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for(int i=0; i<menuView.getChildCount(); i++){
            BottomNavigationItemView item = (BottomNavigationItemView)menuView.getChildAt(i);
            item.setChecked(true);
            //every BottomNavigationItemView has two children, first is an itemIcon and second is an itemTitle
            itemTitle = item.getChildAt(1);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trajan-Pro-Regular.ttf");
            ((TextView)((BaselineLayout) itemTitle).getChildAt(0)).setTypeface(font, Typeface.NORMAL);
            ((TextView)((BaselineLayout) itemTitle).getChildAt(1)).setTypeface(font, Typeface.NORMAL);
        }
    }
}
