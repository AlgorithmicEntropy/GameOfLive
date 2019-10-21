package com.mygdx.game.util;

public class Counter {

    private float currentValue;
    private float defaultValue;

    public Counter(float duration) {
        currentValue = duration;
        defaultValue = duration;
    }

    public boolean isFinished() {
        return currentValue <= 0;
    }

    public void reset() {
        currentValue = defaultValue;
    }

    public void subtract(float value) {
        currentValue -= value;
    }

    public void setDuration(float value) {
        defaultValue = value;
    }

    public float getDuration() {
        return defaultValue;
    }
}
