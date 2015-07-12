package com.mvcoding.twitter.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class Presenter<V extends PresenterView> {
    private V view;
    private CompositeSubscription subscriptions;

    @CallSuper protected void onViewAttached(@NonNull V view) {
        if (this.view != null) {
            throw new IllegalStateException("View " + this.view + " is already attached. Cannot attach " + view);
        }
        this.view = view;
    }

    @CallSuper protected void onViewDetached() {
        if (view == null) {
            throw new IllegalStateException("View is already detached.");
        }
        this.view = null;

        if (subscriptions != null) {
            subscriptions.unsubscribe();
            subscriptions = null;
        }
    }

    @CallSuper protected void unsubscribeOnDetach(@NonNull Subscription subscription) {
        if (subscriptions == null) {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(subscription);
    }

    @NonNull
    protected Action1<Throwable> handleFatalError(@NonNull ErrorPresenterView errorPresenterView, @NonNull CloseablePresenterView closeablePresenterView) {
        return throwable -> errorPresenterView.showError(throwable, ErrorPresenterView.ErrorMode.Toast)
                .doOnNext(errorAction -> closeablePresenterView.close())
                .subscribe();
    }
}
