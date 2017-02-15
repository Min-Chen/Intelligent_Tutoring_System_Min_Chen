package com.example.minchen.moving_average_min_chen.model;

public class Joy {
    float joyNum;
    float timeStamp;

    public Joy(float joyNum, float timeStamp) {
        this.joyNum = joyNum;
        this.timeStamp = timeStamp;
    }

    public float getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(float timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getJoyNum() {
        return joyNum;
    }

    public void setJoyNum(float joyNum) {
        this.joyNum = joyNum;
    }

}
