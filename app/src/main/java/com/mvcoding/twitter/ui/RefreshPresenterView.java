package com.mvcoding.twitter.ui;

import android.support.annotation.NonNull;

import rx.Observable;

public interface RefreshPresenterView {
    @NonNull Observable<RefreshEvent> onRefreshEvent();
    void setRefreshing(boolean refreshing);

    class RefreshEvent {
        private final boolean isForce;

        public RefreshEvent(boolean isForce) {
            this.isForce = isForce;
        }

        public boolean isForce() {
            return isForce;
        }
    }
}
