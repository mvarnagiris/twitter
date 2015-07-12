package com.mvcoding.twitter.ui.user;

import com.mvcoding.twitter.BaseComponent;
import com.mvcoding.twitter.ui.ActivityScope;

import dagger.Subcomponent;

@ActivityScope @Subcomponent(modules = LoginModule.class) public interface LoginComponent extends BaseComponent {
    void inject(LoginActivity activity);
}
