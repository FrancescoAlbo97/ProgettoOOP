package com.project.progettoOOP.service;

import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.utils.ArrayListUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Filters {

    public static ArrayList<Environment> getFilteredData(ArrayList<Environment> arrayList, JSONObject json) throws Exception {
        String field = (String) json.keys().next();
        if (field.contains("$or")) {
            ArrayListUtils<Environment> itemListUtil = new ArrayListUtils<>();
            ArrayList<ArrayList<Environment>> itemArrayList = new ArrayList<>();
            ArrayList<Environment> itemList = new ArrayList<>();
            JSONArray jsonArray = json.getJSONArray(field);
            for (int i = 0; i < jsonArray.length() ; i++) {
                if(json.getJSONArray(field).get(i) instanceof JSONObject) {
                    itemArrayList.add(getFilteredData(arrayList,jsonArray.getJSONObject(i)));
                }
            }
            itemList = itemListUtil.or(itemArrayList);
            return itemList;
        } else if (field.contains("$and")) {
            ArrayListUtils<Environment> itemListUtil = new ArrayListUtils<>();
            ArrayList<ArrayList<Environment>> itemArrayList = new ArrayList<>();
            ArrayList<Environment> itemList = new ArrayList<>();
            JSONArray jsonArray = json.getJSONArray(field);
            for (int i = 0; i < jsonArray.length() ; i++) {
                if(json.getJSONArray(field).get(i) instanceof JSONObject) {
                    itemArrayList.add(getFilteredData(arrayList,jsonArray.getJSONObject(i)));
                }
            }
            itemList = itemListUtil.and(itemArrayList);
            return itemList;
        } else {
            JSONObject newJson = json.getJSONObject(field);
            String operator = (String) newJson.keys().next();
            EnvironmentCollection environmentCollection = new EnvironmentCollection(arrayList);
            switch (operator) {
                case "$bt":
                    double min = newJson.getJSONArray(operator).getDouble(0);
                    double max = newJson.getJSONArray(operator).getDouble(1);
                    return environmentCollection.filterField(field, operator,  min, max);
                case "$in":
                case "$nin":
                    double[] values = null;
                    for(int i= 0; i < newJson.getJSONArray(operator).length(); i++) {
                        values[i] = newJson.getJSONArray(operator).getDouble(i);
                    }
                    return environmentCollection.filterField(field, operator, values);
                case "$eq":
                case "$not":
                case "$lt":
                case "$gt":
                    double value = newJson.getDouble(operator);
                    return environmentCollection.filterField(field, operator, value);
            }
        }
        throw new Exception();
    }
}
