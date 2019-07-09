package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.service.EnvironmentService;
import com.project.progettoOOP.utils.DownloadCSV;
import com.project.progettoOOP.utils.ParserCSV;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;


@RestController
public class JsonController {

    @Autowired
    private EnvironmentService environmentService;
    private EnvironmentCollection result;
    public JsonController() throws Exception {
        File file = new File("data.csv");
        if(!file.exists()){
            DownloadCSV.getCSV();
        }
        result = new EnvironmentCollection(ParserCSV.parser("data.csv"));
    }

    @RequestMapping(value = "/metadata", method = RequestMethod.GET, produces="application/json")
    String getMetadataOfJson() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
            JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(Environment.class);
            return mapper.writeValueAsString(jsonSchema);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces="application/json")
    EnvironmentCollection getCompleteJson() throws ParseException, JsonProcessingException {

        //EnvironmentCollection environmentCollection = new EnvironmentCollection(ParserCSV.parser("data.csv").getEnvironments());
        //ObjectMapper mapper = new ObjectMapper();
        //return environmentService.getEnvironment();
        //return mapper.writeValueAsString(objects.getEnvironments());
        //EnvironmentCollection result = environmentService.getEnvironment();
        System.out.println(result.getEnvironments().get(0).toString());
        return result;
    }

    @RequestMapping(value="/environment", method=RequestMethod.POST, produces="application/json")
    public String saveEnvironmentPost(@RequestBody(required = false) String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        //Environment environment = new Environment("2111-03-22 20:12:00.000",obj.getString("no"),obj.getString("no2"),obj.getString("nox"),obj.getString("so2"), obj.getString("o3"), obj.getString("co"));
        EnvironmentCollection collection = new EnvironmentCollection(ParserCSV.parser("data.csv"));
        //return environment.toString();
        return collection.getEnvironments().get(0).toString();
    }

    private void inizialize() throws ParseException {
        ArrayList<Environment> environments = ParserCSV.parser("data.csv");
        //EnvironmentCollection environmentCollection = new EnvironmentCollection(environments);
        for (Environment obj : environments){
            environmentService.createEnvironment(obj);
        }
    }
}