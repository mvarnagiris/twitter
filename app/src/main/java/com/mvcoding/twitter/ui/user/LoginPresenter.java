package com.mvcoding.twitter.ui.user;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.mvcoding.twitter.api.Session;
import com.mvcoding.twitter.ui.ActivityScope;
import com.mvcoding.twitter.ui.CloseablePresenterView;
import com.mvcoding.twitter.ui.ErrorPresenterView;
import com.mvcoding.twitter.ui.Presenter;
import com.mvcoding.twitter.ui.PresenterView;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@ActivityScope class LoginPresenter extends Presenter<LoginPresenter.View> {
    private static final String CALLBACK_URL = "oauth://tweeterdemoapp";
    private static final String VERIFIER_QUERY_PARAM = "oauth_verifier";

    private final Session session;
    private final Twitter twitter;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;

    private Observable<RequestToken> requestTokenObservable;
    private Observable<AccessToken> accessTokenObservable;

    @Inject
    LoginPresenter(@NonNull Session session, @NonNull Twitter twitter, @NonNull @Named("ui") Scheduler uiScheduler, @NonNull @Named("io") Scheduler ioScheduler) {
        this.session = session;
        this.twitter = twitter;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
    }

    @Override protected void onViewAttached(@NonNull View view) {
        super.onViewAttached(view);

        unsubscribeOnDetach(getRequestToken().observeOn(uiScheduler)
                                    .flatMap(requestToken -> getVerifierData(view, requestToken))
                                    .observeOn(ioScheduler)
                                    .flatMap(this::getAccessToken)
                                    .observeOn(uiScheduler)
                                    .doOnNext(accessToken -> session.setToken(accessToken.getToken(), accessToken.getTokenSecret()))
                                    .doOnNext(twitter::setOAuthAccessToken)
                                    .subscribeOn(ioScheduler)
                                    .subscribe(accessToken -> view.startTweetsAndClose(), handleFatalError(view, view)));
    }

    @NonNull private Observable<RequestToken> getRequestToken() {
        if (requestTokenObservable != null) {
            return requestTokenObservable;
        }

        requestTokenObservable = Observable.defer(() -> Observable.create(subscriber -> {
            try {
                final RequestToken requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
                subscriber.onNext(requestToken);
                subscriber.onCompleted();
            } catch (TwitterException e) {
                subscriber.onError(e);
            }
        }));

        requestTokenObservable = requestTokenObservable.cache();
        return requestTokenObservable;
    }

    @NonNull private Observable<VerifierData> getVerifierData(@NonNull View view, @NonNull RequestToken requestToken) {
        return view.onVerifierUriProvided(requestToken.getAuthenticationURL()).map(uri -> {
            if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
                return new VerifierData(requestToken, uri.getQueryParameter(VERIFIER_QUERY_PARAM));
            } else {
                throw new IllegalStateException("Verifier url cannot be empty.");
            }
        });
    }

    @NonNull private Observable<AccessToken> getAccessToken(@NonNull VerifierData verifierData) {
        if (accessTokenObservable != null) {
            return accessTokenObservable;
        }

        accessTokenObservable = Observable.defer(() -> Observable.create(subscriber -> {
            try {
                final AccessToken accessToken = twitter.getOAuthAccessToken(verifierData.requestToken, verifierData.verifier);
                subscriber.onNext(accessToken);
                subscriber.onCompleted();
            } catch (TwitterException e) {
                subscriber.onError(e);
            }
        }));

        accessTokenObservable = accessTokenObservable.cache();
        return accessTokenObservable;
    }

    public interface View extends PresenterView, ErrorPresenterView, CloseablePresenterView {
        @NonNull Observable<Uri> onVerifierUriProvided(@NonNull String uri);
        void startTweetsAndClose();
    }

    private static class VerifierData {
        private final RequestToken requestToken;
        private final String verifier;

        private VerifierData(@NonNull RequestToken requestToken, @NonNull String verifier) {
            this.requestToken = requestToken;
            this.verifier = verifier;
        }
    }
}
