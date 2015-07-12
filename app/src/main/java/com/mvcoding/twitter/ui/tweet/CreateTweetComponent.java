package com.mvcoding.twitter.ui.tweet;

import com.mvcoding.twitter.BaseComponent;
import com.mvcoding.twitter.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope @Subcomponent(modules = CreateTweetModule.class) public interface CreateTweetComponent extends BaseComponent {
    void inject(CreateTweetActivity activity);
}
