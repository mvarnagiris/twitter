package com.mvcoding.twitter.ui.tweet;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Presenter;

import javax.inject.Inject;

public class CreateTweetActivity extends BaseActivity<CreateTweetPresenter.View, CreateTweetComponent> implements CreateTweetPresenter.View {
    @Inject CreateTweetPresenter presenter;

    @Override protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @NonNull @Override protected CreateTweetComponent createComponent(@NonNull ActivityComponent component) {
        return component.plus(new CreateTweetModule());
    }

    @Override protected void inject(@NonNull CreateTweetComponent component) {
        component.inject(this);
    }

    @NonNull @Override protected Presenter<CreateTweetPresenter.View> getPresenter() {
        return presenter;
    }

    @NonNull @Override protected CreateTweetPresenter.View getPresenterView() {
        return this;
    }
}