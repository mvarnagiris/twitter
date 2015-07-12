package com.mvcoding.twitter;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mvcoding.twitter.api.ApiModule;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {
    private final Map<String, ? super BaseComponent> components = new HashMap<>();

    private AppComponent appComponent;

    public static App with(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf")
                                              .setFontAttrId(R.attr.fontPath)
                                              .build());

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).apiModule(new ApiModule()).build();
    }

    public AppComponent getComponent() {
        return appComponent;
    }

    public <C extends BaseComponent> C getComponent(@NonNull String key) {
        //noinspection unchecked
        return (C) components.get(key);
    }

    public <C extends BaseComponent> void putComponent(@NonNull String key, @NonNull C component) {
        components.put(key, component);
    }

    public void removeComponent(@NonNull String key) {
        components.remove(key);
    }
}
