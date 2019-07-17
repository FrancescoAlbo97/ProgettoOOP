package com.project.progettoOOP.service;

import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.utils.ArrayListUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
                    Object min = newJson.getJSONArray(operator).get(0);
                    Object max = newJson.getJSONArray(operator).get(1);
                    return environmentCollection.filterField(field, operator,  min, max);
                case "$in":
                case "$nin":
                    ArrayList<Object> values = new ArrayList<>();
                    try{
                        JSONArray jsonArray = newJson.getJSONArray(operator);
                        for(int i= 0; i < newJson.getJSONArray(operator).length(); i++) {
                            values.add(newJson.getJSONArray(operator).get(i));
                        }
                        return environmentCollection.filterField(field, operator, values.toArray());
                    }catch(Exception e) {
                        Object obj = newJson.get(operator);
                        return environmentCollection.filterField(field, operator, obj);
                    }
                case "$eq":
                case "$not":
                case "$lt":
                case "$gt":
                    Object value = newJson.get(operator);
                    return environmentCollection.filterField(field, operator, value);
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid filter json format");
            }
        }
    }
}
