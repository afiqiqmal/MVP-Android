package com.java.mvp.mvpandroid.utils;

import android.content.Context;
import android.graphics.Typeface;


import com.java.mvp.mvpandroid.R;

import javax.inject.Inject;

/**
 * @author : hafiq on 01/09/2017.
 */

public class TypeFaceUtils {


    private Context context;

    @Inject
    public TypeFaceUtils(Context context){
        this.context = context;
    }

    public Typeface getBoldFont(){
        return Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_bold));
    }

    public Typeface getRegularFont(){
        return Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_regular));
    }

    public Typeface getMediumFont(){
        return Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_medium));
    }

    public Typeface getThinFont(){
        return Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_thin));
    }

    public Typeface loadFont(String path){
        return Typeface.createFromAsset(context.getAssets(), path);
    }
}
