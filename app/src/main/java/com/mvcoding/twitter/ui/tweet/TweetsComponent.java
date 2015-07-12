package com.mvcoding.twitter.ui.tweet;

import com.mvcoding.twitter.BaseComponent;
import com.mvcoding.twitter.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope @Subcomponent(modules = TweetsModule.class) public interface TweetsComponent extends BaseComponent {
    void inject(TweetsActivity activity);
}
