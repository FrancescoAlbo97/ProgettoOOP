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

import java.text.ParseException;
import java.util.ArrayList;


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
    ArrayList<Environment> getCompleteJson() {

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
}