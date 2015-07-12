package com.mvcoding.twitter.ui.tweet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private static final int REQUEST_NEW_TWEET = 1;

    private final PublishSubject<RefreshEvent> refreshSubject = PublishSubject.create();
    private final PublishSubject<Status> statusSubject = PublishSubject.create();

    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.fab) FloatingActionButton fab;

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
        swipeRefreshLayout.setOnRefreshListener(() -> refreshSubject.onNext(new RefreshEvent(true)));
        fab.setOnClickListener(v -> CreateTweetActivity.startForResult(this, REQUEST_NEW_TWEET));
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

    @NonNull @Override public Observable<Status> onTweetCreated() {
        return statusSubject;
    }

    @Override public void showTweets(@NonNull List<Status> tweets) {
        adapter.setItems(tweets);
    }

    @Override public void addTweet(@NonNull Status status) {
        adapter.insertItem(status, 0);
        recyclerView.scrollToPosition(0);
    }

    @NonNull @Override public Observable<RefreshEvent> onRefreshEvent() {
        return refreshSubject;
    }

    @Override public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }
}
