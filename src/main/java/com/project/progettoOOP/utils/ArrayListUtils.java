package com.project.progettoOOP.utils;

import java.util.ArrayList;

public class ArrayListUtils<T>{

    public ArrayList<T> and(ArrayList<ArrayList<T>> itemsList) {

        ArrayList<T> list = new ArrayList<>();
        int length = itemsList.size();
        for (int i = 0; i < length-1; i++){
            for (T item1 : itemsList.get(i)){
                for (T item2 : itemsList.get(i+1)) {
                    if (item1.equals(item2)){
                        list.add(item2);
                        break;
                    }
                }
            }
            itemsList.get(i+1).clear();
            for (T item : list){
                itemsList.get(i+1).add(item);
            }
            list.clear();
        }
        return itemsList.get(length-1);
    }

    public ArrayList<T> or(ArrayList<ArrayList<T>> itemsList) {
        ArrayList<T> list = new ArrayList<T>();
        for (ArrayList<T> arrayList : itemsList){
            for (T item : arrayList){
                list.add(item);
            }
        }
        return list;
    }
}
