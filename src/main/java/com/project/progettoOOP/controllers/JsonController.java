package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.utils.ArrayListUtils;
import com.project.progettoOOP.utils.DateCustom;
import com.project.progettoOOP.utils.Statistic;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class JsonController {

    private ArrayList<Environment> selectedData = new ArrayList<>();

    //@Autowired
    //private EnvironmentService environmentService;

    //public JsonController(){ }

    @RequestMapping(value = "/metadata", method = RequestMethod.GET, produces="application/json")
    String getMetadata() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
            JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(Environment.class);
            return mapper.writeValueAsString(jsonSchema);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces="application/json")
    ArrayList<Environment> getData(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
            ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        selectedData.clear();
        checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")) {
            selectedData = selectByMolecule(molecule);
        }
        return selectedData;
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces="application/json")
    String getStatistics(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, JsonProcessingException {
        selectedData.clear();
        checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")){
            selectedData = selectByMolecule(molecule);
        } else{
            molecule = new String[]{"no", "no2", "nox", "so2", "o3", "co"};
        }
        HashMap<String, Statistic<Environment>> map = new HashMap<>();
        map = getFilteredStatistics(selectedData, molecule);
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(map);
        return result;
    }

    @RequestMapping(value="/filter", method=RequestMethod.POST, produces="application/json")
    public String getFilteredValues(
            @RequestBody(required = false) String jsonString) throws Exception {
        JSONObject json = null;
            ArrayList<Environment> result = null;
            if(jsonString != null){
                json = new JSONObject(jsonString);
                result = getFilteredData(EnvironmentCollection.environments, json);
            }
            ObjectMapper mapper = new ObjectMapper();
            if(result != null) {
                return mapper.writeValueAsString(result);
            } else return mapper.writeValueAsString(EnvironmentCollection.environments);
    }

    private void checkDateFormat(ArrayList<String> monthString, ArrayList<String> dayString){
        if (monthString.get(0).equals("0")) {
            monthString.clear();
            for (int i = 0; i < 12; i++){
                int value = i + 1;
                monthString.add(String.valueOf(value));
            }
        }
        if (!checkDateAndParse(monthString,dayString)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid date format");
        }
    }

    private boolean checkDateAndParse(ArrayList<String> month, ArrayList<String> day) {
        HashMap<Integer,ArrayList<Integer>> daysForMonths = new HashMap<>();
        for (int i = 0; i < month.size(); i++) {
            if (month.get(i).matches("^([1-9]|1[012])$")) {
                int monthValue = Integer.parseInt(month.get(i));
                if (!(day.get(0).equals("0"))) {
                    if (monthValue == 2) {
                        ArrayList<Integer> days = new ArrayList<>();
                        for (int j = 0; j < day.size(); j++) {
                            if (day.get(j).matches("^([1-9]|1[1-9]|2[1-9])$")) {
                                 days.add(Integer.parseInt(day.get(j)));
                            } else return false;
                        }
                        daysForMonths.put(monthValue,days);
                    }
                    if (monthValue == 4 || monthValue == 6 || monthValue == 9 || monthValue == 11) {
                        ArrayList<Integer> days = new ArrayList<>();
                        for (int j = 0; j < day.size(); j++) {
                            if (day.get(j).matches("^([1-9]|1[1-9]|2[1-9]|30)$")) {
                                days.add(Integer.parseInt(day.get(j)));
                            } else return false;
                        }
                        daysForMonths.put(monthValue,days);
                    }
                    else {
                        ArrayList<Integer> days = new ArrayList<>();
                        for (int j = 0; j < day.size(); j++) {
                            if (day.get(j).matches("^([1-9]|1[1-9]|2[1-9]|3[01])$")) {
                                days.add(Integer.parseInt(day.get(j)));
                            } else return false;
                        }
                        daysForMonths.put(monthValue,days);
                    }
                } else {
                    ArrayList<Integer> days = new ArrayList<>();
                    int dayOfMonth = DateCustom.getDaysOfMonth(monthValue);
                    for (int j = 1; j <= dayOfMonth; j++) {
                        days.add(j);
                    }
                    daysForMonths.put(monthValue,days);
                }
            } else{
                return false;
            }
        }
        selectDataByMonthAndDate(daysForMonths);
        return true;
    }

    private void selectDataByMonthAndDate(HashMap<Integer,ArrayList<Integer>> daysForMonths){
        for (Environment obj : EnvironmentCollection.environments) {
            for (Integer m : daysForMonths.keySet()){
                if (obj.getMyDate().getMonth().intValue() == m.intValue()){
                    for (Integer d: daysForMonths.get(m)){
                        if(obj.getMyDate().getDay().intValue() == d.intValue()){
                            selectedData.add(obj);
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Environment> selectByMolecule(String[] molecule) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method m1 = null;
        Method m2 = null;
        ArrayList<Environment> arrayList = new ArrayList<>();
        for (Environment item : selectedData) {
            String nullValue = null;
            Environment environment = new Environment(item.getMyDate(),nullValue,nullValue,nullValue,nullValue,nullValue,nullValue);
            for (int i=0; i < molecule.length; i++){
                m1 = item.getClass().getMethod("get"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1),null);
                Object value = m1.invoke(item);
                if (!(value == null)){
                    m2 = environment.getClass().getMethod( "set"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1), Float.class);
                    m2.invoke(environment, (Float) value);
                }
            }
            arrayList.add(environment);
        }
        return arrayList;
    }

    private HashMap<String, Statistic<Environment>> getFilteredStatistics(ArrayList<Environment> arrayList, String[] molecule) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HashMap<String, Statistic<Environment>> allStatsmap = new HashMap<>();
        for(int i = 0; i < molecule.length; i++) {
            Statistic<Environment> singleStat = new Statistic<Environment>(arrayList, molecule[i]);
            allStatsmap.put(molecule[i],singleStat);
        }
        return allStatsmap;
    }

    private ArrayList<Environment> getFilteredData(ArrayList<Environment> arrayList, JSONObject json) throws Exception {
        String field = (String) json.keys().next();
        if (field.contains("$or")) {
            ArrayListUtils<Environment> itemListUtil = new ArrayListUtils<>();
            ArrayList<Environment> itemList = new ArrayList<>();
            JSONArray jsonArray = json.getJSONArray(field);
            for (int i = 0; i < jsonArray.length()-1 ; i++) {
                if(json.getJSONArray(field).get(i) instanceof JSONObject && json.getJSONArray(field).get(i+1) instanceof JSONObject) {
                    itemList = itemListUtil.or(getFilteredData(arrayList,jsonArray.getJSONObject(i)),getFilteredData(arrayList,jsonArray.getJSONObject(i+1)));
                }
            }
            if(json.getJSONArray(field).get(0) instanceof JSONObject && itemList.isEmpty())
                return getFilteredData(arrayList,jsonArray.getJSONObject(0));
            else return itemList;
        } else if (field.contains("$and")) {
            ArrayListUtils<Environment> itemListUtil = new ArrayListUtils<>();
            ArrayList<Environment> itemList = new ArrayList<>();
            JSONArray jsonArray = json.getJSONArray(field);
            for (int i = 0; i < jsonArray.length()-1 ; i++) {
                if(json.getJSONArray(field).get(i) instanceof JSONObject && json.getJSONArray(field).get(i+1) instanceof JSONObject) {
                    itemList = itemListUtil.and(getFilteredData(arrayList,jsonArray.getJSONObject(i)),getFilteredData(arrayList,jsonArray.getJSONObject(i+1)));
                }
            }
            if(json.getJSONArray(field).get(0) instanceof JSONObject && itemList.isEmpty())
                return getFilteredData(arrayList,jsonArray.getJSONObject(0));
            else return itemList;
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