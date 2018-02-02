package com.java.mvp.mvpandroid.ui.custom.circle_reveal;

import android.view.ViewGroup;

/**
 * Indicator for internal API that {@link ViewGroup} support
 * Circular Reveal animation
 */
public interface RevealViewGroup {

  /**
   * @return Bridge between view and circular reveal animation
   */
  ViewRevealManager getViewRevealManager();

  /**
   *
   * @param manager
   */
  void setViewRevealManager(ViewRevealManager manager);
}