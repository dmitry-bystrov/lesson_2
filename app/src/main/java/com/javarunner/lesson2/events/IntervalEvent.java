package com.javarunner.lesson2.events;

import java.util.Locale;

public class IntervalEvent {
    private Long value;

    public IntervalEvent(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s: %d", this.getClass().getName(), value);
    }
}