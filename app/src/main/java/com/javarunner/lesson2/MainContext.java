package com.javarunner.lesson2;

import android.app.Application;

import com.javarunner.lesson2.events.EventBus;
import com.javarunner.lesson2.events.IntervalEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class MainContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        Observable<Long> intervalObservable = Observable.interval(1, TimeUnit.SECONDS);
        intervalObservable
                .map(new Function<Long, IntervalEvent>() {
                    @Override
                    public IntervalEvent apply(Long aLong) throws Exception {
                        return new IntervalEvent(aLong);
                    }
                })
                .subscribe(EventBus.getInstance().getSubject());

        EventBus.getInstance().getSubject()
                .subscribe(Logger.getInstance().getEventObserver());
    }
}
