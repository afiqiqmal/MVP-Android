package com.java.mvp.mvpandroid.ui.custom.circle_reveal;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author : hafiq on 29/10/2017.
 */

public class RevealUtils {

    public static void openRevealCircular(View v, int cx, int cy, int radius) {
        Animator reveal = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, radius);
        reveal.setInterpolator(new DecelerateInterpolator(2f));
        reveal.setDuration(1000);
        reveal.start();
    }

    public static Animator prepareUnrevealAnimator(View v,float cx, float cy) {
        int radius = getEnclosingCircleRadius(v, (int) cx, (int) cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(v, (int) cx, (int) cy, radius, 0);
        anim.setInterpolator(new AccelerateInterpolator(2f));
        anim.setDuration(450);
        return anim;
    }

    private static int getEnclosingCircleRadius(View v, int cx, int cy) {
        int realCenterX = cx + v.getLeft();
        int realCenterY = cy + v.getTop();
        int distanceTopLeft = (int) Math.hypot(realCenterX - v.getLeft(), realCenterY - v.getTop());
        int distanceTopRight = (int) Math.hypot(v.getRight() - realCenterX, realCenterY - v.getTop());
        int distanceBottomLeft = (int) Math.hypot(realCenterX - v.getLeft(), v.getBottom() - realCenterY);
        int distanceBottomRight = (int) Math.hypot(v.getRight() - realCenterX, v.getBottom() - realCenterY);

        Integer[] distances = new Integer[]{distanceTopLeft, distanceTopRight, distanceBottomLeft, distanceBottomRight};

        return Collections.max(Arrays.asList(distances));
    }
}
