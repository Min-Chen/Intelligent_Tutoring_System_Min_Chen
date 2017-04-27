package com.example.minchen.intelligent_tutoring_system_min_chen.model;

import java.util.ArrayList;
import java.util.List;

public class FrownContainer {
    private static List<Frown> frownList;

    public FrownContainer() {
        frownList = new ArrayList<>();
    }

    public List<Frown> getFrownList() {
        return frownList;
    }

    public void setJoyList(List<Frown> frownList) {
        this.frownList = frownList;
    }

    public void addFrown(float frownNum, float timeStamp) {
        frownList.add(new Frown(frownNum, timeStamp));
    }

    public float getFirstTimeStampInFrownList() {
        return frownList.get(0).getTimeStamp();
    }

    public List<Float> getFrownNumList() {
        List<Float> frownNumList = new ArrayList<>();
        for (Frown frown : this.frownList) {
            frownNumList.add(frown.getFrownNum());
        }
        return frownNumList;
    }

    public void removeElement(int index) {
        frownList.remove(index);
    }

    public int getSize() {
        return frownList.size();
    }
}
