package com.mvcoding.twitter.ui.tweet;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.ActivityStarter;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Presenter;

import javax.inject.Inject;

public class TweetsActivity extends BaseActivity<TweetsPresenter.View, TweetsComponent> implements TweetsPresenter.View {
    @Inject TweetsPresenter presenter;

    public static void start(@NonNull Context context) {
        ActivityStarter.with(context, TweetsActivity.class).start();
    }

    @Override protected int getLayoutId() {
        return R.layout.activity_tweets;
    }

    @NonNull @Override protected TweetsComponent createComponent(@NonNull ActivityComponent component) {
        return component.plus(new TweetsModule());
    }

    @Override protected void inject(@NonNull TweetsComponent component) {
        component.inject(this);
    }

    @NonNull @Override protected Presenter<TweetsPresenter.View> getPresenter() {
        return presenter;
    }

    @NonNull @Override protected TweetsPresenter.View getPresenterView() {
        return this;
    }
}
