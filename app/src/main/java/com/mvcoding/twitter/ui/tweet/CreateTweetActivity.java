package com.mvcoding.twitter.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.ActivityStarter;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Presenter;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.view.ViewObservable;
import rx.android.widget.WidgetObservable;
import twitter4j.Status;

public class CreateTweetActivity extends BaseActivity<CreateTweetPresenter.View, CreateTweetComponent> implements CreateTweetPresenter.View {
    private static final String RESULT_EXTRA_STATUS = "RESULT_EXTRA_STATUS";

    @Bind(R.id.messageEditText) EditText messageEditText;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Inject CreateTweetPresenter presenter;

    public static void startForResult(@NonNull Context context, int requestCode) {
        ActivityStarter.with(context, CreateTweetActivity.class).startForResult(requestCode);
    }

    public static Status getResultExtraStatus(@NonNull Intent data) {
        return (Status) data.getSerializableExtra(RESULT_EXTRA_STATUS);
    }

    @Override protected int getLayoutId() {
        return R.layout.activity_create_tweet;
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

    @NonNull @Override public Observable<String> onMessageChanged() {
        return WidgetObservable.text(messageEditText, true).map(onTextChangeEvent -> onTextChangeEvent.text().toString());
    }

    @NonNull @Override public Observable<Void> onSend() {
        return ViewObservable.clicks(fab).map(onClickEvent -> null);
    }

    @Override public void startStatusCreated(@NonNull Status status) {
        final Intent data = new Intent();
        data.putExtra(RESULT_EXTRA_STATUS, status);
        setResult(RESULT_OK, data);
        close();
    }
}
