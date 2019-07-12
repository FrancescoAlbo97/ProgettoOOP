package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class JsonController {

    private ArrayList<Integer> month = new ArrayList<>();
    private ArrayList<Integer> day = new ArrayList<>();
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
            ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, JsonProcessingException, NoSuchFieldException {
        month.clear();
        day.clear();
        selectedData.clear();
        checkDateFormat(monthString, dayString);
        //Statistic<Environment> statistic = new Statistic<Environment>(arrayList,Molecule);
        selectDataByMonthAndDate();
        ObjectMapper mapper = new ObjectMapper();
        if (!molecule[0].equals("all")){
            ArrayList<Environment> selectedDataByMolecule = new ArrayList<>();
            selectedDataByMolecule = selectByMolecule(molecule);
            return selectedDataByMolecule;
        }
        else return selectedData;
    }

    @RequestMapping(value="/environment", method=RequestMethod.POST, produces="application/json")
    public String saveEnvironmentPost(@RequestBody(required = false) String json) {

        return EnvironmentCollection.environments.get(0).toString();
    }

    private void checkDateFormat(ArrayList<String> monthString, ArrayList<String> dayString){
        if (!monthString.get(0).equals("0")) {
            String[] monthVector = new String[monthString.size()];
            monthVector = monthString.toArray(monthVector);
            String[] dayVector = new String[dayString.size()];
            dayVector = dayString.toArray(dayVector);
            if (!checkDateAndParse(monthVector,dayVector)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid date format");
            }
        }
        if (monthString.get(0).equals("0") && (!dayString.get(0).equals("0"))){
            String[] monthVector = {"1","2","3","4","5","6","7","8","9","10","11","12"};
            String[] dayVector = new String[dayString.size()];
            dayVector = dayString.toArray(dayVector);
            if (!checkDateAndParse(monthVector,dayVector)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid date format");
            }
        }
    }

    private boolean checkDateAndParse(String[] monthString, String[] dayString) {
        for (int i = 0; i < monthString.length; i++) {
            if (monthString[i].matches("^([1-9]|1[012])$")) {
                Integer integer = Integer.parseInt(monthString[i]);
                month.add(integer);
                if (!dayString[0].equals("0")){
                    if (month.get(i) == 2) {
                        for (int j = 0; j < dayString.length; j++) {
                            if (dayString[j].matches("^([1-9]|1[1-9]|2[1-9])$")) {
                                day.add(Integer.parseInt(dayString[j]));
                            } else return false;
                        }
                    }
                    if (month.get(i) == 4 || month.get(i) == 6 || month.get(i) == 9 || month.get(i) == 11) {
                        for (int j = 0; j < dayString.length; j++) {
                            if (dayString[j].matches("^([1-9]|1[1-9]|2[1-9]|30)$")) {
                                day.add(Integer.parseInt(dayString[j]));
                            } else return false;
                        }
                    }
                    else {
                        for (int j = 0; j < dayString.length; j++) {
                            if (dayString[j].matches("^([1-9]|1[1-9]|2[1-9]|3[01])$")) {
                                day.add(Integer.parseInt(dayString[j]));
                            } else return false;
                        }
                    }
                } else day.add(-1);
            } else return false;
        }
        return true;
    }

    private void selectDataByMonthAndDate(){
        for (Environment obj : EnvironmentCollection.environments) {
            for (Integer m : month) {
                if (obj.getDate_time().getMonth()+1 == m) {
                    if(day.get(0) == -1){
                        selectedData.add(obj);
                    }
                    else {
                        for (Integer d : day) {
                            if (obj.getDate_time().getDate() == d) {
                                selectedData.add(obj);
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList<Environment> selectByMolecule(String[] molecule) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method m1 = null;
        Method m2 = null;
        ArrayList<Environment> arrayList = new ArrayList<>();
        for (Environment item : selectedData) {
            float[] values = new float[molecule.length];
            Environment environment = new Environment(null,null,null,null,null,null);
            environment.setDate_time(item.getDate_time());
            for (int i=0; i < molecule.length; i++){
                m1 = item.getClass().getMethod("get"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1),null);
                values[i] = (float) m1.invoke(item);
                m2 = environment.getClass().getMethod( "set"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1), Float.TYPE);
                m2.invoke(environment, values[i]);
            }
            arrayList.add(environment);
        }
        return arrayList;
    }

}