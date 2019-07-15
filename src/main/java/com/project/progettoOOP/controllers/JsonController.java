package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.service.EnvironmentService;
import com.project.progettoOOP.utils.DateCustom;
import com.project.progettoOOP.utils.Statistic;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
            ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, ParseException {
        selectedData.clear();
        checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")) {
            ArrayList<Environment> selectedDataByMolecule = new ArrayList<>();
            selectedDataByMolecule = selectByMolecule(molecule);
            return selectedDataByMolecule;
        }
        else return selectedData;
    }

    @RequestMapping(value = "/statistic", method = RequestMethod.GET, produces="application/json")
    String getStatistics(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException, JsonProcessingException {
        selectedData.clear();
        checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")){
            ArrayList<Environment> selectedDataByMolecule = new ArrayList<>();
            selectedDataByMolecule = selectByMolecule(molecule);
            HashMap<String, Statistic<Environment>> map = new HashMap<>();
            map = getFilteredStatistics(selectedDataByMolecule, molecule);
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(map);
            return result;
        } else{
            String[] molecules = new String[]{"no", "no2", "nox", "so2", "o3", "co"};
            HashMap<String, Statistic<Environment>> map = new HashMap<>();
            map = getFilteredStatistics(selectedData, molecules);
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(map);
            return result;
        }
    }

    @RequestMapping(value="/environment", method=RequestMethod.POST, produces="application/json")
    public String saveEnvironmentPost(@RequestBody(required = false) String json) {

        return EnvironmentCollection.environments.get(0).toString();
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

    private ArrayList<Environment> selectByMolecule(String[] molecule) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        Method m1 = null;
        Method m2 = null;
        ArrayList<Environment> arrayList = new ArrayList<>();
        for (Environment item : selectedData) {
            //Float value = null;
            String nullValue = null;
            Environment environment = new Environment(item.getMyDate(),nullValue,nullValue,nullValue,nullValue,nullValue,nullValue);
            for (int i=0; i < molecule.length; i++){
                m1 = item.getClass().getMethod("get"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1),null);
                Float value = (Float) m1.invoke(item);
                m2 = environment.getClass().getMethod( "set"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1), float.class);
                if (!(value == null)) m2.invoke(environment, value.floatValue());
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
/*
    private HashMap<String, HashMap<String,Float>> getStatisticOfMolecule(ArrayList<Environment> arrayList, String[] molecule) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        HashMap<String, HashMap<String,Float>> allStatsmap = new HashMap<>();
        for(int i = 0; i < molecule.length; i++){
            Statistic<Environment> singleStat = new Statistic<Environment>(arrayList, molecule[i]);
            HashMap<String, Float> statMap = new HashMap<>();
            statMap.put("Avg", singleStat.getAvg());
            statMap.put("Min", singleStat.getMin());
            statMap.put("Max", singleStat.getMax());
            statMap.put("Dev", singleStat.getDev());
            statMap.put("Sum", singleStat.getSum());
            allStatsmap.put(molecule[i],statMap);
        }
        return allStatsmap;
    }*/
}