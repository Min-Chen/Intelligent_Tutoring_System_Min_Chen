package com.example.minchen.moving_average_min_chen;

public class Joy {
    float joyness;
    float timeStamp;

    public Joy(float joyness, float timeStamp) {
        this.joyness = joyness;
        this.timeStamp = timeStamp;
    }

    public float getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(float timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getJoyness() {
        return joyness;
    }

    public void setJoyness(float joyness) {
        this.joyness = joyness;
    }

}
