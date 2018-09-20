package com.javarunner.lesson2.events;

import io.reactivex.subjects.PublishSubject;

public class EventBus {
    private PublishSubject<Object> subject;

    private static final EventBus ourInstance = new EventBus();

    public static EventBus getInstance() {
        return ourInstance;
    }

    private EventBus() {
        subject = PublishSubject.create();
    }

    public void post(Object o) {
        subject.onNext(o);
    }

    public PublishSubject<Object> getSubject() {
        return subject;
    }
}
