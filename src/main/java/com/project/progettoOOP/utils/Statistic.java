package com.project.progettoOOP.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Classe che permette di fare le statistiche su qualsiasi tipo di dato.
 * @param <T> Oggetto generico su cui fare la statistica
 */
public class Statistic<T> {

    private float avg;
    private float min;
    private float max;
    private float dev;
    private float sum;

    /**
     * Costruttore della classe che permette di fare la statistica su un qualsiasi array list passato come parametro
     *
     * @param arrayList Array list generico.
     * @param name Parametro su cui fare la statistica
     * @throws NoSuchMethodException dovuta al metodo getMethod.
     * @throws InvocationTargetException dovuta al metodo invoke.
     * @throws IllegalAccessException dovuta al metodo invoke.
     */
    public Statistic(ArrayList<T> arrayList, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Float> values = new ArrayList<>();
        for (T item : arrayList) {
            Method m = null;
            m = item.getClass().getMethod("get"+name.substring(0, 1).toUpperCase()+name.substring(1),null);
            Object value = m.invoke(item);
            if(value != null) values.add((Float) value);
        }
        this.avg = getAvg(values);
        this.min = getMin(values);
        this.max = getMax(values);
        this.dev = getDev(values);
        this.sum = getSum(values);
    }

    /**
     * Costruttore della classe
     * @param avg Indica la media.
     * @param min Indica il minimo.
     * @param max Indica il massimo .
     * @param dev Indica la deviazione standard.
     * @param sum Indica la somma.
     */
    public Statistic(float avg, float min, float max, float dev, float sum) {
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.dev = dev;
        this.sum = sum;
    }

    /**
     * Metodo che permettere di vedere la media.
     * @return Ritorna la media.
     */
    public float getAvg() {
        return avg;
    }

    /**
     * Metodo che permettere di inserire la media.
     * @param avg Valore da inserire.
     */
    public void setAvg(float avg) {
        this.avg = avg;
    }

    /**
     * Metodo che permettere di vedere il minimo.
     * @return Ritorna il minimo.
     */
    public float getMin() {
        return min;
    }

    /**
     * Metodo che permettere di inserire il minimo.
     * @param min Valore da inserire.
     */
    public void setMin(float min) {
        this.min = min;
    }

    /**
     * Metodo che permettere di vedere il massimo.
     * @return Ritorna il massimo.
     */
    public float getMax() {
        return max;
    }

    /**
     * Metodo che permettere di inserire il massimo.
     * @param max Valore da inserire.
     */
    public void setMax(float max) {
        this.max = max;
    }

    /**
     * Metodo che permettere di vedere la deviazione standard.
     * @return Ritorna la deviazione standard.
     */
    public float getDev() {
        return dev;
    }

    /**
     * Metodo che permettere di inserire la deviazione standard.
     * @param dev Valore da inserire
     */
    public void setDev(float dev) {
        this.dev = dev;
    }

    /**
     * Metodo che permettere di vedere la somma.
     * @return Ritorna la somma.
     */
    public float getSum() {
        return sum;
    }

    /**
     * Metodo che permettere di inserire la somma.
     * @param sum Valore da inserire.
     */
    public void setSum(float sum) {
        this.sum = sum;
    }

    /**
     * Metodo che permette di calcolare la media di un qualsiasi array list passato come parametro.
     * @param arrayList array generico.
     * @return Ritorna la media.
     */
    private float getAvg(ArrayList<Float> arrayList) {
        int count = 0;
        float sum = 0;
        for (Float item : arrayList) {
            sum += item;
            count++;
        }
        return sum/count;
    }

    /**
     * Metodo che permette di calcolare la deviazione standard di un qualsiasi array list passato come parametro.
     * @param arrayList Arraylist generico.
     * @return Ritorna la deviazione standard.
     */
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

    /**
     * Metodo che permette di calcolare il minimo di un qualsiasi array list passato come parametro.
     * @param arrayList Arraylist generico.
     * @return Ritorna il minimo calcolato.
     */
    private float getMin(ArrayList<Float> arrayList) {
        float currentMin = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) < currentMin){
                currentMin = arrayList.get(i);
            }
        }
        return currentMin;
    }

    /**
     *  Metodo che permette di calcolare il massimo di un qualsiasi array list passato come parametro.
     * @param arrayList Arraylist generico
     * @return Ritorna il massimo calcolato.
     */
    private float getMax(ArrayList<Float> arrayList) {
        float currentMax = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) > currentMax){
                currentMax = arrayList.get(i);
            }
        }
        return currentMax;
    }

    /**
     *  Metodo che permette di calcolare la somma degli elementi di un qualsiasi array list passato come parametro.
     * @param arrayList Arraylist generico.
     * @return Ritorna la somma calcolata.
     */
    private float getSum(ArrayList<Float> arrayList) {
        float sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i);
            }
        return sum;
    }

}
