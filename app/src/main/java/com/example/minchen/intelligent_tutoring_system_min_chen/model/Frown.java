package com.example.minchen.intelligent_tutoring_system_min_chen.model;

public class Frown {
    float frownNum;
    float timeStamp;

    public Frown(float frownNum, float timeStamp) {
        this.frownNum = frownNum;
        this.timeStamp = timeStamp;
    }

    public float getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(float timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getFrownNum() {
        return frownNum;
    }

    public void setFrownNum(float frownNum) {
        this.frownNum = frownNum;
    }

}
