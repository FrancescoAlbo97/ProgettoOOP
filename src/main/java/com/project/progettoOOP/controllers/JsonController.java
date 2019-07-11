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

import java.util.ArrayList;



@RestController
public class JsonController {

    private ArrayList<Integer> month = new ArrayList<>();
    private ArrayList<Integer> day = new ArrayList<>();

    @Autowired
    private EnvironmentService environmentService;

    //public JsonController(){ }

    @RequestMapping(value = "/metadata", method = RequestMethod.GET, produces="application/json")
    String getMetadataOfJson() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
            JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(Environment.class);
            return mapper.writeValueAsString(jsonSchema);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces="application/json")
    ArrayList<Environment> getStatisticByMonthByDayByMolecule(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
            ) {
        month.clear();
        day.clear();
        if (!monthString.get(0).equals("0") && !dayString.get(0).equals("0")) {
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
        /*
        for (Environment obj : EnvironmentCollection.environments){
            if(obj.getDate_time().getMonth() == month && obj.getDate_time().getDay() == day){
                arrayList.add(obj);
            }
        }
        Statistic<Environment> statistic = new Statistic<Environment>(arrayList,Molecule); */
        ArrayList<Environment> arrayList = new ArrayList<>();
        for (Integer m : month) {
            for (Integer d : day) {
                for (Environment obj : EnvironmentCollection.environments) {
                    int v = obj.getDate_time().getMonth();
                    if (obj.getDate_time().getMonth()+1 == m) {
                        int c = obj.getDate_time().getDate();
                        if (obj.getDate_time().getDate() == d) {
                            arrayList.add(obj);
                        }
                    }
                }
            }
        }
        return arrayList;
    }


    @RequestMapping(value="/environment", method=RequestMethod.POST, produces="application/json")
    public String saveEnvironmentPost(@RequestBody(required = false) String json) {

        return EnvironmentCollection.environments.get(0).toString();
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
                }
            } else return false;
        }
        return true;
    }

}