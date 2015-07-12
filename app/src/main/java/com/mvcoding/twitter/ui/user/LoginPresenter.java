package com.mvcoding.twitter.ui.user;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.api.Session;
import com.mvcoding.twitter.ui.ActivityScope;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.PresenterView;

import javax.inject.Inject;

@ActivityScope class LoginPresenter extends Presenter<LoginPresenter.View> {
    private final Session session;

    @Inject LoginPresenter(@NonNull Session session) {
        this.session = session;
    }

    @Override protected void onViewAttached(@NonNull View view) {
        super.onViewAttached(view);
    }

    public interface View extends PresenterView {
    }
}
