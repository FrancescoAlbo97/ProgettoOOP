package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.utils.ParserCSV;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class JsonController {

    @RequestMapping(value = "/metadata", method = RequestMethod.GET, produces="application/json")
    String getMetadataOfJson() throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
            JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(Environment.class);
            return mapper.writeValueAsString(jsonSchema);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces="application/json")
    String getCompleteJson() throws ParseException, JsonProcessingException {

        EnvironmentCollection objects = ParserCSV.parser("data.csv");
        ObjectMapper mapper = new ObjectMapper();
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //Date date = formatter.parse("1451602800000");

        return mapper.writeValueAsString(objects.getEnvironments());
        //in questo passaggio ho problemi con la data: mi da il formato fastTime
    }

    @RequestMapping(value="/environment", method=RequestMethod.POST, produces="application/json")
    public String saveEnvironmentPost(@RequestBody(required = false) String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        Environment environment = new Environment("2111-03-22 20:12:00.000",obj.getString("no"),obj.getString("no2"),obj.getString("nox"),obj.getString("so2"), obj.getString("o3"), obj.getString("co"));
        //EnvironmentCollection collection = ParserCSV.parser("data.csv");
        return environment.toString();
    }
}