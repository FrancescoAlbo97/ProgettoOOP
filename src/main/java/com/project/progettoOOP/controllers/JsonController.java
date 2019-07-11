package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.service.EnvironmentService;
import com.project.progettoOOP.utils.ParserCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;


@RestController
public class JsonController {

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
    //ArrayList<Environment>
    String getStatisticByMonthByDayByMolecule(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
            ) {
        ArrayList<Environment> arrayList = new ArrayList<>();
        if (!monthString.get(0).equals("0") && !dayString.get(0).equals("0")) {
            String[] monthVector = new String[monthString.size()];
            monthVector = monthString.toArray(monthVector);
            String[] dayVector = new String[dayString.size()];
            dayVector = dayString.toArray(dayVector);
            if (!checkDateAndParse(monthVector,dayVector)) {
                return "error";
            }
        }
        if (monthString.get(0).equals("0") && (!dayString.get(0).equals("0"))){
            String[] monthVector = {"1","2","3","4","5","6","7","8","9","10","11","12"};
            String[] dayVector = new String[dayString.size()];
            dayVector = dayString.toArray(dayVector);
            if (!checkDateAndParse(monthVector,dayVector)) {
                return "error";
            }
        }
        /*
        for (Environment obj : EnvironmentCollection.environments){
            if(obj.getDate_time().getMonth() == month && obj.getDate_time().getDay() == day){
                arrayList.add(obj);
            }
        }
        Statistic<Environment> statistic = new Statistic<Environment>(arrayList,Molecule); */
        //EnvironmentCollection environmentCollection = new EnvironmentCollection(ParserCSV.parser("data.csv").getEnvironments());
        //ObjectMapper mapper = new ObjectMapper();
        //return environmentService.getEnvironment();
        //return mapper.writeValueAsString(objects.getEnvironments());
        //EnvironmentCollection result = environmentService.getEnvironment();
        //System.out.println(result.getEnvironments().get(0).toString());
        //return EnvironmentCollection.environments;
        //String ciao = month.get(0).toString() + day.get(0).toString();
        return "ciao";
    }

    @RequestMapping(value = "/data/month/{month}/day/{day}", method = RequestMethod.GET, produces="application/json")
        ArrayList<Environment> getStatisticByMonthByDay(
            @PathVariable(value = "month") int month,
            @PathVariable(value = "day") int day
    ) {/*
        for (Environment obj : EnvironmentCollection.environments){
            if(obj.getDate_time().getMonth() == month && obj.getDate_time().getDay() == day){

            }
        }*/
        //EnvironmentCollection environmentCollection = new EnvironmentCollection(ParserCSV.parser("data.csv").getEnvironments());
        //ObjectMapper mapper = new ObjectMapper();
        //return environmentService.getEnvironment();
        //return mapper.writeValueAsString(objects.getEnvironments());
        //EnvironmentCollection result = environmentService.getEnvironment();
        //System.out.println(result.getEnvironments().get(0).toString());
        return EnvironmentCollection.environments;
    }

    @RequestMapping(value = "/data/month/{month}", method = RequestMethod.GET, produces="application/json")
        ArrayList<Environment> getStatisticByMonth (
            @PathVariable(value = "month") int month
    ) {/*
        for (Environment obj : EnvironmentCollection.environments){
            if(obj.getDate_time().getMonth() == month && obj.getDate_time().getDay() == day){

            }
        }*/
        //EnvironmentCollection environmentCollection = new EnvironmentCollection(ParserCSV.parser("data.csv").getEnvironments());
        //ObjectMapper mapper = new ObjectMapper();
        //return environmentService.getEnvironment();
        //return mapper.writeValueAsString(objects.getEnvironments());
        //EnvironmentCollection result = environmentService.getEnvironment();
        //System.out.println(result.getEnvironments().get(0).toString());
        return EnvironmentCollection.environments;
    }

    @RequestMapping(value="/environment", method=RequestMethod.POST, produces="application/json")
    public String saveEnvironmentPost(@RequestBody(required = false) String json) {

        return EnvironmentCollection.environments.get(0).toString();
    }

    private void inizialize() throws ParseException {
        ArrayList<Environment> environments = ParserCSV.parser("data.csv");
        //EnvironmentCollection environmentCollection = new EnvironmentCollection(environments);
        for (Environment obj : environments){
            environmentService.createEnvironment(obj);
        }
    }

    private boolean checkDateAndParse(String[] monthString, String[] dayString) {
        ArrayList<Integer> month = new ArrayList<>();
        ArrayList<Integer> day = new ArrayList<>();
        for (int i = 0; i < monthString.length; i++) {
            if (monthString[i].matches("^([1-9]|1[012])$")) {
                Integer integer = new Integer(Integer.parseInt(monthString[i]));
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