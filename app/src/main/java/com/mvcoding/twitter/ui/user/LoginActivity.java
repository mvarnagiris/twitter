package com.mvcoding.twitter.ui.user;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.ActivityStarter;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Presenter;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<LoginPresenter.View, LoginComponent> implements LoginPresenter.View {
    @Inject LoginPresenter presenter;

    public static void start(@NonNull Context context) {
        ActivityStarter.with(context, LoginActivity.class).enableTransition().start();
    }

    @Override protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @NonNull @Override protected LoginComponent createComponent(@NonNull ActivityComponent component) {
        return component.plus(new LoginModule());
    }

    @Override protected void inject(@NonNull LoginComponent component) {
        component.inject(this);
    }

    @NonNull @Override protected Presenter<LoginPresenter.View> getPresenter() {
        return presenter;
    }

    @NonNull @Override protected LoginPresenter.View getPresenterView() {
        return this;
    }
}
