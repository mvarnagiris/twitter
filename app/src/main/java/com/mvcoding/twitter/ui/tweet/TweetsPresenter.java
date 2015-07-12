package com.mvcoding.twitter.ui.tweet;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.ui.ActivityScope;
import com.mvcoding.twitter.ui.CloseablePresenterView;
import com.mvcoding.twitter.ui.ErrorPresenterView;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.PresenterView;
import com.mvcoding.twitter.ui.RefreshPresenterView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@ActivityScope class TweetsPresenter extends Presenter<TweetsPresenter.View> {
    private final Twitter twitter;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;
    private final Paging paging;

    private Observable<List<Status>> tweetsObservable;

    @Inject
    TweetsPresenter(@NonNull Twitter twitter, @NonNull @Named("ui") Scheduler uiScheduler, @NonNull @Named("io") Scheduler ioScheduler) {
        this.twitter = twitter;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;

        paging = new Paging(1, 20);
    }

    @Override protected void onViewAttached(@NonNull View view) {
        super.onViewAttached(view);

        unsubscribeOnDetach(onRefreshEvent(view).observeOn(ioScheduler)
                                    .flatMap(refreshEvent -> getTweetsObservable())
                                    .observeOn(uiScheduler)
                                    .doOnNext(statuses -> view.setRefreshing(false))
                                    .subscribeOn(uiScheduler)
                                    .subscribe(view::showTweets, handleFatalError(view, view)));
    }

    @NonNull private Observable<RefreshPresenterView.RefreshEvent> onRefreshEvent(@NonNull View view) {
        return view.onRefreshEvent().startWith(new RefreshPresenterView.RefreshEvent(false)).doOnNext(refreshEvent -> {
            if (refreshEvent.isForce()) {
                tweetsObservable = null;
            }

            if (tweetsObservable == null) {
                view.setRefreshing(true);
            }
        });
    }

    @NonNull private Observable<List<Status>> getTweetsObservable() {
        if (tweetsObservable != null) {
            return tweetsObservable;
        }

        tweetsObservable = Observable.defer(() -> Observable.create(subscriber -> {
            try {
                subscriber.onNext(twitter.getHomeTimeline(paging));
                subscriber.onCompleted();
            } catch (TwitterException e) {
                subscriber.onError(e);
            }
        }));

        tweetsObservable = tweetsObservable.cache();
        return tweetsObservable;
    }

    public interface View extends PresenterView, ErrorPresenterView, CloseablePresenterView, RefreshPresenterView {
        @NonNull Observable<Status> onTweetCreated();
        void showTweets(@NonNull List<Status> tweets);
        void addTweet(@NonNull Status status);
    }
}
