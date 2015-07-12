package com.mvcoding.twitter.ui.tweet;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.ui.ActivityScope;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.PresenterView;

import javax.inject.Inject;

@ActivityScope class TweetsPresenter extends Presenter<TweetsPresenter.View> {
    @Inject TweetsPresenter() {
    }

    @Override protected void onViewAttached(@NonNull View view) {
        super.onViewAttached(view);
    }

    public interface View extends PresenterView {
    }
}
