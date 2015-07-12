/*
 * Copyright (C) 2015 Mantas Varnagiris.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.mvcoding.twitter.util;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import javax.inject.Inject;

public class PreferencesHelper {
    private final SharedPreferences sharedPreferences;

    @Inject public PreferencesHelper(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void put(@NonNull String key, @Nullable Object value) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value != null) {
            editor.putString(key, new Gson().toJson(value)).apply();
        } else {
            editor.remove(key).apply();
        }
    }

    @Nullable public <T> T get(@NonNull String key, @NonNull Class<T> cls) {
        if (!sharedPreferences.contains(key)) {
            return null;
        }

        final String serialized = sharedPreferences.getString(key, null);
        if (serialized == null) {
            return null;
        }

        return new Gson().fromJson(serialized, cls);
    }
}
