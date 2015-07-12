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
