package com.mvcoding.twitter.ui.splash;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.api.Session;
import com.mvcoding.twitter.ui.ActivityScope;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.PresenterView;

import javax.inject.Inject;

@ActivityScope class SplashPresenter extends Presenter<SplashPresenter.View> {
    private final Session session;

    @Inject SplashPresenter(@NonNull Session session) {
        this.session = session;
    }

    @Override protected void onViewAttached(@NonNull View view) {
        super.onViewAttached(view);

        if (session.isLoggedIn()) {
            view.startTweetsAndClose();
        } else {
            view.startLoginAndClose();
        }
    }

    public interface View extends PresenterView {
        void startTweetsAndClose();
        void startLoginAndClose();
    }
}
