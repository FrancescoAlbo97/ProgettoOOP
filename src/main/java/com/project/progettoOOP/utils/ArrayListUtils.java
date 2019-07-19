package com.project.progettoOOP.utils;

import java.util.ArrayList;

/**
 * Classe che permette di implementare la logica AND e OR
 * @param <T> parametro generico
 */
public class ArrayListUtils<T>{
    /**
     * implementa la logica AND
     * @param itemsList un gruppo di insieme di oggetti
     * @return un insieme di oggetti
     */
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

    /**
     * Implementa la logica OR
     * @param itemsList un gruppo di insieme di oggetti
     * @return un insieme di oggetti
     */
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
