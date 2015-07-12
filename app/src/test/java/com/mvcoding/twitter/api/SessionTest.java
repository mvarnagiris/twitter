package com.mvcoding.twitter.api;

import com.mvcoding.twitter.BaseTest;
import com.mvcoding.twitter.util.PreferencesHelper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SessionTest extends BaseTest {
    private Session session;

    @Override public void setUp() throws Exception {
        super.setUp();

        final PreferencesHelper preferencesHelper = mock(PreferencesHelper.class);

        session = new Session();
        session.preferencesHelper = preferencesHelper;
    }

    @Test public void isLoggedIn_returnsTrue_whenTokenIsNotEmpty() throws Exception {
        session.setToken("token", "tokenSecret");

        assertThat(session.isLoggedIn()).isTrue();
    }

    @Test public void isLoggedIn_returnsFalse_whenTokenIsNullOrEmpty() throws Exception {
        assertThat(session.isLoggedIn()).isFalse();

        session.setToken("", "");
        assertThat(session.isLoggedIn()).isFalse();
    }
}