package com.javarunner.lesson2.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.javarunner.lesson2.events.ActivityEvent;
import com.javarunner.lesson2.events.ClickEvent;
import com.javarunner.lesson2.events.EventBus;
import com.javarunner.lesson2.events.IntervalEvent;
import com.javarunner.lesson2.model.CounterModel;
import com.javarunner.lesson2.presentation.view.MainView;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private Disposable eventBusSubscription;
    private CompositeDisposable disposables;
    private Scheduler uiScheduler;
    private CounterModel model;

    public MainPresenter(Scheduler uiScheduler) {
        Timber.d("MainPresenter constructor");
        this.disposables = new CompositeDisposable();
        this.uiScheduler = uiScheduler;
        this.model = new CounterModel();
    }

    public void subscribeEditTextChanges(Observable<CharSequence> observable) {
        Timber.d("subscribeEditTextChanges");
        disposables.add(observable
                .observeOn(uiScheduler)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        getViewState().setTextViewText(charSequence);
                    }
                }));
    }

    public void subscribeButtonOneClicks(Observable<Object> observable) {
        Timber.d("subscribeButtonOneClicks");
        disposables.add(observable
                .observeOn(uiScheduler)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getViewState().setButtonOneText(String.format(Locale.getDefault(), "%d", model.calculate(0)));
                    }
                }));
    }

    public void subscribeButtonTwoClicks(Observable<Object> observable) {
        Timber.d("subscribeButtonTwoClicks");
        disposables.add(observable
                .observeOn(uiScheduler)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getViewState().setButtonTwoText(String.format(Locale.getDefault(), "%d", model.calculate(1)));
                    }
                }));
    }

    public void subscribeButtonThreeClicks(Observable<Object> observable) {
        Timber.d("subscribeButtonThreeClicks");
        disposables.add(observable
                .observeOn(uiScheduler)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        getViewState().setButtonThreeText(String.format(Locale.getDefault(), "%d", model.calculate(2)));
                    }
                }));
    }

    public void subscribeEventBus(EventBus eventBus) {
        if (eventBusSubscription != null) return;

        Timber.d("subscribeEventBus");
        eventBusSubscription = eventBus.getSubject()
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o instanceof IntervalEvent) {
                            IntervalEvent event = (IntervalEvent) o;
                            getViewState().setIntervalCounterText(String.format(Locale.getDefault(), "%d", event.getValue()));
                        }

                        if (o instanceof ClickEvent) {
                            ClickEvent event = (ClickEvent) o;
                            getViewState().setButtonListenerText(event.getValue());
                        }

                        if (o instanceof ActivityEvent) {
                            ActivityEvent event = (ActivityEvent) o;
                            getViewState().showToast(event.getValue());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        eventBusSubscription.dispose();
        disposables.dispose();
    }
}
