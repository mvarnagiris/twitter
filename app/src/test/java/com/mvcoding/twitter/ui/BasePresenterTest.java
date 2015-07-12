package com.mvcoding.twitter.ui;

import com.mvcoding.twitter.BaseTest;

public abstract class BasePresenterTest<P extends Presenter<V>, V extends PresenterView> extends BaseTest {
    protected P presenter;
    protected V view;

    @Override public void setUp() throws Exception {
        super.setUp();
        presenter = createPresenter();
        view = createView();
    }

    protected abstract P createPresenter();

    protected abstract V createView();

    protected void presenterOnViewAttached() {
        presenter.onViewAttached(view);
    }

    protected void presenterOnViewDetached() {
        presenter.onViewDetached();
    }
}
