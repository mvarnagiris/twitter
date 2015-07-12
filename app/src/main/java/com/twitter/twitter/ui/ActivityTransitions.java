package com.twitter.twitter.ui;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

public interface ActivityTransitions {
    @NonNull ActivityTransitions slide(int gravity, @NonNull View view, View... views);
    @NonNull ActivityTransitions slide(int gravity, @IdRes int viewId, @IdRes int... viewIds);
    @NonNull ActivityTransitions fade(@NonNull View view, View... views);
    @NonNull ActivityTransitions fade(@IdRes int viewId, @IdRes int... viewIds);
    @NonNull ActivityTransitions explode(@NonNull View view, View... views);
    @NonNull ActivityTransitions explode(@IdRes int viewId, @IdRes int... viewIds);
    @NonNull ActivityTransitions fadeStatusBar();
    @NonNull ActivityTransitions fadeNavigationBar();
    @NonNull ActivityTransitions reveal(@NonNull View view, View... views);
    @NonNull ActivityTransitions sharedElementOverlay(boolean overlay);
    void asExitTransition();
    void asEnterTransition();
    void asReturnTransition();
    void asReenterTransition();
}
