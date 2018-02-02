package com.java.mvp.mvpandroid.ui.custom.circle_reveal;

import android.animation.Animator;
import android.view.View;


import com.java.mvp.mvpandroid.BuildConfig;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public final class ViewAnimationUtils {
  private final static boolean DEBUG = BuildConfig.DEBUG;

  private final static boolean LOLLIPOP_PLUS = SDK_INT >= LOLLIPOP;

  /**
   * Returns an Animator which can animate a clipping circle.
   * <p>
   * Any shadow cast by the View will respect the circular clip from this animator.
   * <p>
   * Only a single non-rectangular clip can be applied on a View at any time.
   * Views clipped by a circular reveal animation take priority over
   * {@link View#setClipToOutline(boolean) View Outline clipping}.
   * <p>
   * Note that the animation returned here is a one-shot animation. It cannot
   * be re-used, and once started it cannot be paused or resumed.
   *
   * @param view The View will be clipped to the clip circle.
   * @param centerX The x coordinate of the center of the clip circle.
   * @param centerY The y coordinate of the center of the clip circle.
   * @param startRadius The starting radius of the clip circle.
   * @param endRadius The ending radius of the clip circle.
   */
  public static Animator createCircularReveal(View view, int centerX, int centerY,
      float startRadius, float endRadius) {

    return createCircularReveal(view, centerX, centerY, startRadius, endRadius,
        View.LAYER_TYPE_SOFTWARE);
  }

  /**
   * Returns an Animator which can animate a clipping circle.
   * <p>
   * Any shadow cast by the View will respect the circular clip from this animator.
   * <p>
   * Only a single non-rectangular clip can be applied on a View at any time.
   * Views clipped by a circular reveal animation take priority over
   * {@link View#setClipToOutline(boolean) View Outline clipping}.
   * <p>
   * Note that the animation returned here is a one-shot animation. It cannot
   * be re-used, and once started it cannot be paused or resumed.
   *
   * @param view The View will be clipped to the clip circle.
   * @param centerX The x coordinate of the center of the clip circle.
   * @param centerY The y coordinate of the center of the clip circle.
   * @param startRadius The starting radius of the clip circle.
   * @param endRadius The ending radius of the clip circle.
   * @param layerType View layer type {@link View#LAYER_TYPE_HARDWARE} or {@link
   * View#LAYER_TYPE_SOFTWARE}
   */
  public static Animator createCircularReveal(View view, int centerX, int centerY,
      float startRadius, float endRadius, int layerType) {

    if (!(view.getParent() instanceof RevealViewGroup)) {
      throw new IllegalArgumentException("Parent must be instance of RevealViewGroup");
    }

    final RevealViewGroup viewGroup = (RevealViewGroup) view.getParent();
    final ViewRevealManager rm = viewGroup.getViewRevealManager();

    if (!rm.overrideNativeAnimator() && LOLLIPOP_PLUS) {
      return android.view.ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
          startRadius, endRadius);
    }

    final ViewRevealManager.RevealValues viewData = new ViewRevealManager.RevealValues(view, centerX, centerY, startRadius, endRadius);
    final Animator animator = rm.dispatchCreateAnimator(viewData);

    if (layerType != view.getLayerType()) {
      animator.addListener(new ViewRevealManager.ChangeViewLayerTypeAdapter(viewData, layerType));
    }

    return animator;
  }
}
