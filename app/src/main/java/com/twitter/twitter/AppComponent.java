package com.twitter.twitter;

import com.twitter.twitter.ui.ActivityComponent;
import com.twitter.twitter.ui.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton @Component(modules = AppModule.class) public interface AppComponent extends BaseComponent {
    ActivityComponent plus(ActivityModule module);
}
