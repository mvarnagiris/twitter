package com.mvcoding.twitter.ui.splash;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.user.LoginActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<SplashPresenter.View, SplashComponent> implements SplashPresenter.View {
    @Inject SplashPresenter presenter;

    @Override protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @NonNull @Override protected SplashComponent createComponent(@NonNull ActivityComponent component) {
        return component.plus(new SplashModule());
    }

    @Override protected void inject(@NonNull SplashComponent component) {
        component.inject(this);
    }

    @NonNull @Override protected Presenter<SplashPresenter.View> getPresenter() {
        return presenter;
    }

    @NonNull @Override protected SplashPresenter.View getPresenterView() {
        return this;
    }

    @Override public void startTweetsAndClose() {
        // TODO: Start tweets.
        close();
    }

    @Override public void startLoginAndClose() {
        LoginActivity.start(this);
        close();
    }
}
