package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.service.Filters;
import com.project.progettoOOP.service.SelectData;
import com.project.progettoOOP.service.Statistics;
import com.project.progettoOOP.utils.Statistic;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class JsonController {

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
            ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SelectData newData = new SelectData(EnvironmentCollection.environments);
        newData.checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")) {
            newData.selectByMolecule(newData.getSelectedData(),molecule);
        }
        return newData.getSelectedData();
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces="application/json")
    HashMap<String, Statistic<Environment>> getStatistics(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SelectData newData = new SelectData(EnvironmentCollection.environments);
        newData.checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")) {
            newData.selectByMolecule(newData.getSelectedData(),molecule);
        }else{
            molecule = new String[]{"no", "no2", "nox", "so2", "o3", "co"};
        }
        HashMap<String, Statistic<Environment>> map;
        map = Statistics.getFilteredStatistics(newData.getSelectedData(), molecule);
        return map;
    }

    @RequestMapping(value="/filter", method=RequestMethod.POST, produces="application/json")
    public String getFilteredValues(
            @RequestBody(required = false) String jsonString) throws Exception {
        JSONObject json = null;
        ArrayList<Environment> result = null;
        if(jsonString != null){
            json = new JSONObject(jsonString);
            result = Filters.getFilteredData(EnvironmentCollection.environments, json);
        }
        ObjectMapper mapper = new ObjectMapper();
        if(result != null) {
            return mapper.writeValueAsString(result);
        } else return mapper.writeValueAsString(EnvironmentCollection.environments);
    }

}