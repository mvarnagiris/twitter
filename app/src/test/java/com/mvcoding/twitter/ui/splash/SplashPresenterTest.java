package com.mvcoding.twitter.ui.splash;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.api.Session;
import com.mvcoding.twitter.ui.BasePresenterTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SplashPresenterTest extends BasePresenterTest<SplashPresenter, SplashPresenter.View> {
    @Mock private Session session;

    @NonNull @Override protected SplashPresenter createPresenter() {
        return new SplashPresenter(session);
    }

    @NonNull @Override protected SplashPresenter.View createView() {
        return mock(SplashPresenter.View.class);
    }

    @Test public void onViewAttached_startLoginAndClose_whenSessionIsNotLoggedIn() {
        when(session.isLoggedIn()).thenReturn(false);

        presenterOnViewAttached();

        verify(view).startLoginAndClose();
    }

    @Test public void onViewAttached_startTweetsAndClose_whenSessionIsLoggedIn() {
        when(session.isLoggedIn()).thenReturn(true);

        presenterOnViewAttached();

        verify(view).startTweetsAndClose();
    }
}
