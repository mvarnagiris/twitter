package com.mvcoding.twitter.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mvcoding.twitter.util.PreferencesHelper;

import javax.inject.Singleton;

@Singleton public class Session {
    transient PreferencesHelper preferencesHelper;

    private String token;

    private static void persist(@NonNull PreferencesHelper preferencesHelper, @NonNull Session session) {
        preferencesHelper.put(Session.class.getName(), session);
    }

    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
        persist(preferencesHelper, this);
    }

    public boolean isLoggedIn() {
        return token != null && !token.isEmpty();
    }

    public void clear() {
        token = null;
        persist(preferencesHelper, this);
    }
}
