package com.javarunner.lesson2.events;

public class ActivityEvent {
    private String value;

    public ActivityEvent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.getClass().getName(), value);
    }
}
