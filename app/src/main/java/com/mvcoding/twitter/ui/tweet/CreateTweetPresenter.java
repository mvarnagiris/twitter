package com.mvcoding.twitter.ui.tweet;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.ui.ActivityScope;
import com.mvcoding.twitter.ui.CloseablePresenterView;
import com.mvcoding.twitter.ui.ErrorPresenterView;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.PresenterView;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@ActivityScope class CreateTweetPresenter extends Presenter<CreateTweetPresenter.View> {
    private final Twitter twitter;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;

    @Inject
    CreateTweetPresenter(@NonNull Twitter twitter, @NonNull @Named("ui") Scheduler uiScheduler, @NonNull @Named("io") Scheduler ioScheduler) {
        this.twitter = twitter;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    @Override protected void onViewAttached(@NonNull View view) {
        super.onViewAttached(view);

        unsubscribeOnDetach(view.onSend()
                                    .withLatestFrom(view.onMessageChanged(), (v, message) -> message)
                                    .observeOn(ioScheduler)
                                    .flatMap(this::postMessage)
                                    .subscribeOn(uiScheduler)
                                    .observeOn(uiScheduler)
                                    .subscribe(view::startStatusCreated, handleFatalError(view, view)));
    }

    @NonNull Observable<Status> postMessage(@NonNull String message) {
        return Observable.defer(() -> Observable.create(subscriber -> {
            try {
                subscriber.onNext(twitter.updateStatus(message));
                subscriber.onCompleted();
            } catch (TwitterException e) {
                subscriber.onError(e);
            }
        }));
    }

    public interface View extends PresenterView, ErrorPresenterView, CloseablePresenterView {
        @NonNull Observable<String> onMessageChanged();
        @NonNull Observable<Void> onSend();
        void startStatusCreated(@NonNull Status status);
    }
}
