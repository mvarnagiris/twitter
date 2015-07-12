package com.mvcoding.twitter.ui.splash;

import com.mvcoding.twitter.BaseComponent;
import com.mvcoding.twitter.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope @Subcomponent(modules = SplashModule.class) public interface SplashComponent extends BaseComponent {
    void inject(SplashActivity activity);
}
