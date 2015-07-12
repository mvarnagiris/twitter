package com.mvcoding.twitter.api;

import com.mvcoding.twitter.util.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module public class ApiModule {
    @Provides @Singleton public Session provideSession(PreferencesHelper preferencesHelper) {
        Session session = preferencesHelper.get(Session.class.getName(), Session.class);
        if (session == null) {
            session = new Session();
        }
        session.preferencesHelper = preferencesHelper;
        return session;
    }
}
