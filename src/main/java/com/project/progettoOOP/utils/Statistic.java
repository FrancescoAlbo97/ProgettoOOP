package com.project.progettoOOP.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Statistic<T> {

    private float avg;
    private float min;
    private float max;
    private float dev;
    private float sum;

    public Statistic(ArrayList<T> arrayList, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Float> values = new ArrayList<>();
        for (T item : arrayList) {
            Method m = null;
            m = item.getClass().getMethod("get"+name.substring(0, 1).toUpperCase()+name.substring(1),null);
            Float value = (Float) m.invoke(item);
            if(value != null) values.add(value);
        }
        this.avg = getAvg(values);
        this.min = getMin(values);
        this.max = getMax(values);
        this.dev = getDev(values);
        this.sum = getSum(values);
    }

    public Statistic(float avg, float min, float max, float dev, float sum) {
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.dev = dev;
        this.sum = sum;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getDev() {
        return dev;
    }

    public void setDev(float dev) {
        this.dev = dev;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    private float getAvg(ArrayList<Float> arrayList) {
        int count = 0;
        float sum = 0;
        for (Float item : arrayList) {
            sum += item;
            count++;
        }
        return sum/count;
    }

    private float getDev(ArrayList<Float> arrayList) {
        float avg = getAvg(arrayList);
        int count = 0;
        float sum = 0;
        for (Float item : arrayList) {
            sum += Math.pow(item - avg, 2);
            count++;
        }
        return (float) Math.pow(sum/count, 0.5);
    }

    private float getMin(ArrayList<Float> arrayList) {
        float currentMin = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) < currentMin){
                currentMin = arrayList.get(i);
            }
        }
        return currentMin;
    }

    private float getMax(ArrayList<Float> arrayList) {
        float currentMax = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) > currentMax){
                currentMax = arrayList.get(i);
            }
        }
        return currentMax;
    }

    private float getSum(ArrayList<Float> arrayList) {
        float sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i);
            }
        return sum;
    }

}
