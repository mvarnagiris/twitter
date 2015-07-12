package com.mvcoding.twitter.ui;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.BaseTest;

import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public abstract class BasePresenterTest<P extends Presenter<V>, V extends PresenterView> extends BaseTest {
    protected final PublishSubject<ErrorPresenterView.ErrorAction> errorActionSubject = PublishSubject.create();

    protected P presenter;
    protected V view;

    @Override public void setUp() throws Exception {
        super.setUp();
        presenter = createPresenter();
        view = createView();

        if (view instanceof ErrorPresenterView) {
            when(((ErrorPresenterView) view).showError(any(Throwable.class), any(ErrorPresenterView.ErrorMode.class))).thenReturn(errorActionSubject);
        }
    }

    @NonNull protected abstract P createPresenter() throws Exception;

    @NonNull protected abstract V createView() throws Exception;

    protected void presenterOnViewAttached() {
        presenter.onViewAttached(view);
    }

    protected void presenterOnViewDetached() {
        presenter.onViewDetached();
    }
}
