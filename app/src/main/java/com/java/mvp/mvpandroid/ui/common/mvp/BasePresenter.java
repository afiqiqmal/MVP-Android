package com.java.mvp.mvpandroid.ui.common.mvp;

/**
 * Created by hafiq on 25/01/2018.
 */

public abstract class BasePresenter<T> {

    abstract public void showLoading();
    abstract public void showContents(T response);
    abstract public void showError(T error);
    abstract public void setView(T view);
}
