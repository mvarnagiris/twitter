package com.mvcoding.twitter.ui.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.ActivityStarter;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.tweet.TweetsActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class LoginActivity extends BaseActivity<LoginPresenter.View, LoginComponent> implements LoginPresenter.View {
    private final BehaviorSubject<Uri> verifierSubject = BehaviorSubject.create();

    @Inject LoginPresenter presenter;

    public static void start(@NonNull Context context) {
        ActivityStarter.with(context, LoginActivity.class).start();
    }

    @Override protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        verifierSubject.onNext(intent.getData());
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

    @NonNull @Override public Observable<Uri> onVerifierUriProvided(@NonNull Uri uri) {
        if (!verifierSubject.hasValue()) {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

        return verifierSubject;
    }

    @Override public void startTweetsAndClose() {
        TweetsActivity.start(this);
        close();
    }
}
