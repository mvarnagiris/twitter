package com.mvcoding.twitter.api;

import android.support.annotation.NonNull;

import com.mvcoding.twitter.util.PreferencesHelper;

import javax.inject.Singleton;

@Singleton public class Session {
    transient PreferencesHelper preferencesHelper;

    private String token;
    private String tokenSecret;

    private static void persist(@NonNull PreferencesHelper preferencesHelper, @NonNull Session session) {
        preferencesHelper.put(Session.class.getName(), session);
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setToken(@NonNull String token, @NonNull String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
        persist(preferencesHelper, this);
    }

    public boolean isLoggedIn() {
        return token != null && !token.isEmpty() && tokenSecret != null && !tokenSecret.isEmpty();
    }

    public void clear() {
        token = null;
        tokenSecret = null;
        persist(preferencesHelper, this);
    }
}
