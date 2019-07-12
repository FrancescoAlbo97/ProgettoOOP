package com.project.progettoOOP.service;

import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EnvironmentServiceImpl {

    private static Map<Integer,Environment> environmentMap = new HashMap<>();
/*
    @Override
    public void createEnvironment(Environment environment) {
        if(environmentMap.containsKey(environment.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existing id");
        }
        environmentMap.put(environment.getId(),environment);
    }

    @Override
    public void updateEnvironment(Integer id, Environment environment) {
        if(environmentMap.containsKey(id)) {
            environmentMap.replace(id, environment);
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This id not exists");
    }

    @Override
    public void deleteEnvironment(Integer id) {
        if(environmentMap.containsKey(id)) {
            environmentMap.remove(id);
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This id not exists");
    }

    @Override
    public EnvironmentCollection getEnvironment() {
        Collection c = environmentMap.values();
        if (!c.isEmpty()) {
            ArrayList<Environment> environments = new ArrayList<Environment>(c);
            EnvironmentCollection environmentCollection = new EnvironmentCollection(environments);
            return environmentCollection;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not elements found");
    }
    */

}
