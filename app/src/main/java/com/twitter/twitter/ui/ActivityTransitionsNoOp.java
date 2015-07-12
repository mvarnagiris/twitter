package com.twitter.twitter.ui;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

class ActivityTransitionsNoOp implements ActivityTransitions {
    @NonNull @Override public ActivityTransitions slide(int gravity, @NonNull View view, View... views) {
        return this;
    }

    @NonNull @Override public ActivityTransitions slide(int gravity, @IdRes int viewId, @IdRes int... viewIds) {
        return this;
    }

    @NonNull @Override public ActivityTransitions fade(@NonNull View view, View... views) {
        return this;
    }

    @NonNull @Override public ActivityTransitions fade(@IdRes int viewId, @IdRes int... viewIds) {
        return this;
    }

    @NonNull @Override public ActivityTransitions explode(@NonNull View view, View... views) {
        return this;
    }

    @NonNull @Override public ActivityTransitions explode(@IdRes int viewId, @IdRes int... viewIds) {
        return this;
    }

    @NonNull @Override public ActivityTransitions fadeStatusBar() {
        return this;
    }

    @NonNull @Override public ActivityTransitions fadeNavigationBar() {
        return this;
    }

    @NonNull @Override public ActivityTransitions reveal(@NonNull View view, View... views) {
        return this;
    }

    @NonNull @Override public ActivityTransitions sharedElementOverlay(boolean overlay) {
        return this;
    }

    @Override public void asExitTransition() {
    }

    @Override public void asEnterTransition() {
    }

    @Override public void asReturnTransition() {
    }

    @Override public void asReenterTransition() {
    }
}
