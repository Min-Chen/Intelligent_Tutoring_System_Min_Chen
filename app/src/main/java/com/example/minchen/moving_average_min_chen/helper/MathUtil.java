package com.example.minchen.moving_average_min_chen.helper;

import java.util.List;

public class MathUtil {
    public static float sum(List<Float> floatList) {
        float sum = 0;
        for (Float i : floatList) {
            sum += i;
        }
        return sum;
    }

    public static float ave(List<Float> floatList) {
        return sum(floatList)/floatList.size();
    }
}
