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

package com.twitter.twitter.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.twitter.twitter.App;
import com.twitter.twitter.BaseComponent;
import com.twitter.twitter.R;

import java.util.UUID;

import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.Icicle;

public abstract class BaseActivity<V extends PresenterView, C extends BaseComponent> extends AppCompatActivity implements CloseablePresenterView {
    @Icicle String componentKey;

    @LayoutRes protected abstract int getLayoutId();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        inject(getComponent());
        setContentView(getLayoutId());
        setupToolbar();
        ButterKnife.bind(this);
        getPresenter().onViewAttached(getPresenterView());
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        getPresenter().onViewDetached();
        ButterKnife.unbind(this);

        if (isFinishing()) {
            App.with(this).removeComponent(componentKey);
        }
    }

    @Override public void close() {
        ActivityCompat.finishAfterTransition(this);
    }

    @NonNull protected abstract C createComponent(@NonNull ActivityComponent component);
    protected abstract void inject(@NonNull C component);
    @NonNull protected abstract Presenter<V> getPresenter();
    @NonNull protected abstract V getPresenterView();

    @NonNull protected Toolbar getToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            throw new IllegalStateException("Toolbar is null.");
        }
        return toolbar;
    }

    @NonNull protected ActivityTransitions transitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new ActivityTransitionsLollipop(this);
        } else {
            return new ActivityTransitionsNoOp();
        }
    }

    private C getComponent() {
        final App app = App.with(this);
        final C component;
        if (componentKey == null) {
            componentKey = UUID.randomUUID().toString();
            component = createComponent(app.getComponent().plus(new ActivityModule()));
            app.putComponent(componentKey, component);
        } else {
            component = app.getComponent(componentKey);
            if (component == null) {
                throw new IllegalStateException("Component was not properly stored.");
            }
        }

        return component;
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
}
