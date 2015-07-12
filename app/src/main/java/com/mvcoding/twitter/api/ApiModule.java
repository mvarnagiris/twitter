package com.mvcoding.twitter.api;

import com.mvcoding.twitter.util.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Module public class ApiModule {
    private static final String CONSUMER_KEY = "BfXC9GR1GLjBoLoMnb0KzszIc";
    private static final String CONSUMER_SECRET = "uS71L8IKzcIZ3aGMLcLqXXH4lIepNQ2jzRK6QF7Rj79rGScCRb";

    @Provides @Singleton public Twitter provideTwitter(Session session) {
        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET);
        if (session.isLoggedIn()) {
            configurationBuilder.setOAuthAccessToken(session.getToken()).setOAuthAccessTokenSecret(session.getTokenSecret());
        }

        return new TwitterFactory(configurationBuilder.build()).getInstance();
    }

    @Provides @Singleton public Session provideSession(PreferencesHelper preferencesHelper) {
        Session session = preferencesHelper.get(Session.class.getName(), Session.class);
        if (session == null) {
            session = new Session();
        }
        session.preferencesHelper = preferencesHelper;
        return session;
    }
}
