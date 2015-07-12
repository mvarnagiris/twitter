package com.mvcoding.twitter.ui.tweet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.ActivityStarter;
import com.mvcoding.twitter.ui.BaseActivity;
import com.mvcoding.twitter.ui.Layout;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.util.RecyclerViewUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.subjects.PublishSubject;
import twitter4j.Status;

public class TweetsActivity extends BaseActivity<TweetsPresenter.View, TweetsComponent> implements TweetsPresenter.View {
    private final PublishSubject<RefreshEvent> refreshSubject = PublishSubject.create();

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    @Inject TweetsPresenter presenter;
    @Inject Layout layout;

    private TweetsAdapter adapter;

    public static void start(@NonNull Context context) {
        ActivityStarter.with(context, TweetsActivity.class).start();
    }

    @Override protected int getLayoutId() {
        return R.layout.activity_tweets;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setNavigationIcon(null);

        RecyclerViewUtils.setupGrid(recyclerView, layout);
        adapter = new TweetsAdapter();
        recyclerView.setAdapter(adapter);
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

    @NonNull @Override public Observable<Status> startCreateTweet() {
        // TODO: Implement.
        return null;
    }

    @Override public void showTweets(@NonNull List<Status> tweets) {
        adapter.setItems(tweets);
    }

    @Override public void addTweet(@NonNull Status status) {
        // TODO: Implement.
    }

    @NonNull @Override public Observable<RefreshEvent> onRefreshEvent() {
        return refreshSubject;
    }

    @Override public void setRefreshing(boolean refreshing) {
        // TODO: Implement.
    }
}
