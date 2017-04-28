package com.java.mvp.mvpandroid.ui.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author : hafiq on 07/02/2017.
 */

public class BlockPager extends ViewPager {
    boolean block = false;

    public BlockPager(Context context) {
        super(context);
    }

    public BlockPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !block && super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !block && super.onTouchEvent(ev);

    }
}