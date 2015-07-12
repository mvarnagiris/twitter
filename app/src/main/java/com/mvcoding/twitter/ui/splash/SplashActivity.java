/*
 * Copyright (C) 2015 Mantas Varnagiris.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.mvcoding.twitter.ui.splash;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Presenter;

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
        // TODO: Start login.
        close();
    }
}
