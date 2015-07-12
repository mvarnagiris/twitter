package com.mvcoding.twitter.ui.user;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.mvcoding.twitter.api.Session;
import com.mvcoding.twitter.ui.BasePresenterTest;
import com.mvcoding.twitter.ui.ErrorPresenterView;

import org.junit.Test;
import org.mockito.Mock;

import rx.Observable;
import rx.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest extends BasePresenterTest<LoginPresenter, LoginPresenter.View> {
    private static final String VERIFIER = "verifier";
    private static final String URI = "https://api.twitter.com/oauth/authenticate?oauth_token=";

    @Mock private Session session;
    @Mock private Twitter twitter;

    private RequestToken requestToken;
    private AccessToken accessToken;

    @Override public void setUp() throws Exception {
        super.setUp();

        requestToken = new RequestToken("", "");
        accessToken = new AccessToken("token", "tokenSecret");

        when(twitter.getOAuthRequestToken(anyString())).thenReturn(requestToken);
        when(twitter.getOAuthAccessToken(requestToken, VERIFIER)).thenReturn(accessToken);
    }

    @NonNull @Override protected LoginPresenter createPresenter() throws Exception {
        return new LoginPresenter(session, twitter, Schedulers.immediate(), Schedulers.immediate());
    }

    @NonNull @Override protected LoginPresenter.View createView() {
        final LoginPresenter.View view = mock(LoginPresenter.View.class);
        final Uri resultUri = mock(Uri.class);
        when(resultUri.toString()).thenReturn("oauth://tweeterdemoapp?oauth_verifier=" + VERIFIER);
        when(resultUri.getQueryParameter("oauth_verifier")).thenReturn(VERIFIER);
        when(view.onVerifierUriProvided(URI)).thenReturn(Observable.just(resultUri));
        return view;
    }

    @Test public void onViewAttached_startTweetsAndClose_whenEverythingSucceeds() throws Exception {
        presenterOnViewAttached();

        verify(view).onVerifierUriProvided(URI);
        verify(session).setToken(accessToken.getToken(), accessToken.getTokenSecret());
        verify(twitter).setOAuthAccessToken(accessToken);
        verify(view).startTweetsAndClose();
    }

    @Test public void onViewAttached_showErrorAndClose_whenAnythingFails() throws Exception {
        final Throwable throwable = mock(Throwable.class);
        when(view.onVerifierUriProvided(URI)).thenReturn(Observable.error(throwable));

        presenterOnViewAttached();
        errorActionSubject.onNext(mock(ErrorPresenterView.ErrorAction.class));

        verify(view).showError(throwable, ErrorPresenterView.ErrorMode.Toast);
        verify(view).close();
    }
}