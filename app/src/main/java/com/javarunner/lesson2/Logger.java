package com.javarunner.lesson2;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class Logger {
    private Observer<Object> eventObserver;
    private Disposable disposable;

    private static final Logger ourInstance = new Logger();

    public static Logger getInstance() {
        return ourInstance;
    }

    private Logger() {
        eventObserver = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d("onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(Object o) {
                Timber.d(o.toString());
            }

            @Override
            public void onError(Throwable e) {
                Timber.d("onError");
            }

            @Override
            public void onComplete() {
                Timber.d("onComplete");
                disposable.dispose();
            }
        };
    }

    public Observer<Object> getEventObserver() {
        return eventObserver;
    }
}
