package com.example.minchen.moving_average_min_chen.model;

import java.util.ArrayList;
import java.util.List;

public class JoyContainer {
    private static List<Joy> joyList;

    public JoyContainer() {
        joyList = new ArrayList<>();
    }

    public List<Joy> getJoyList() {
        return joyList;
    }

    public void setJoyList(List<Joy> joyList) {
        this.joyList = joyList;
    }

    public void addJoy(float joyNum, float timeStamp) {
        joyList.add(new Joy(joyNum, timeStamp));
    }

    public float getFirstTimeStampInJoyList() {
        return joyList.get(0).getTimeStamp();
    }

    public List<Float> getJoyNumList() {
        List<Float> joyNumList = new ArrayList<>();
        for (Joy joy : this.joyList) {
            joyNumList.add(joy.getJoyNum());
        }
        return joyNumList;
    }

    public void removeElement(int index) {
        joyList.remove(index);
    }

    public int getSize() {
        return joyList.size();
    }
}
