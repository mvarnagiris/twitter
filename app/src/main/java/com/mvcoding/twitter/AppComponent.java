package com.mvcoding.twitter;

import com.mvcoding.twitter.api.ApiModule;
import com.mvcoding.twitter.ui.ActivityComponent;
import com.mvcoding.twitter.ui.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = {AppModule.class, ApiModule.class}) public interface AppComponent extends BaseComponent {
    ActivityComponent plus(ActivityModule module);
}
