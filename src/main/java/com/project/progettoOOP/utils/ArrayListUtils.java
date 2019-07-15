package com.project.progettoOOP.utils;

import java.util.ArrayList;

public class ArrayListUtils<T>{

    public ArrayList<T> and(ArrayList<T> itemsList1, ArrayList<T> itemsList2) {
        ArrayList<T> list = new ArrayList<T>();
        for(T item1 : itemsList1) {
            for (T item2 : itemsList2){
                if (item1.equals(item2)) list.add(item1);
            }
        }
        return list;
    }

    public ArrayList<T> or (ArrayList<T> itemsList1, ArrayList<T> itemsList2) {
        ArrayList<T> list = new ArrayList<T>();
        for (T item2 : itemsList2){
            itemsList1.add(item2);
        }
        return itemsList1;
    }
}
