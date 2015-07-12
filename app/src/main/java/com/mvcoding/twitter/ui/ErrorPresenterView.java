package com.mvcoding.twitter.ui;

import android.support.annotation.NonNull;

import rx.Observable;

public interface ErrorPresenterView {
    @NonNull Observable<ErrorAction> showError(@NonNull Throwable throwable, @NonNull ErrorMode errorMode);

    enum ErrorMode {
        Toast
    }

    class ErrorAction {
    }
}
