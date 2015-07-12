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

package com.mvcoding.twitter.ui;

import com.mvcoding.twitter.BaseComponent;
import com.mvcoding.twitter.ui.splash.SplashComponent;
import com.mvcoding.twitter.ui.splash.SplashModule;
import com.mvcoding.twitter.ui.tweet.CreateTweetComponent;
import com.mvcoding.twitter.ui.tweet.CreateTweetModule;
import com.mvcoding.twitter.ui.tweet.TweetsComponent;
import com.mvcoding.twitter.ui.tweet.TweetsModule;
import com.mvcoding.twitter.ui.user.LoginComponent;
import com.mvcoding.twitter.ui.user.LoginModule;

import dagger.Subcomponent;

@ActivityScope @Subcomponent(modules = ActivityModule.class) public interface ActivityComponent extends BaseComponent {
    SplashComponent plus(SplashModule module);
    LoginComponent plus(LoginModule module);
    TweetsComponent plus(TweetsModule module);
    CreateTweetComponent plus(CreateTweetModule module);
}
