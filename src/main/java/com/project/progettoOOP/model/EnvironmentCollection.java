
package com.project.progettoOOP.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.progettoOOP.Filter;
import com.project.progettoOOP.service.EnvironmentService;
import com.project.progettoOOP.utils.FilterUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@JsonFormat( timezone = "GMT+1", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
public class EnvironmentCollection implements Filter<Environment, Object[]> {

    @Autowired
    EnvironmentService environmentService;

    @Override
    public String toString() {
        String line = "";
        int conta = 0;
        for(Environment e : environments){
            if (conta < 30){
                line += e.toString();
                conta++;
            }
        }
        return line;
    }

    private ArrayList<Environment> environments;
    private FilterUtils<Environment> utils;

    public EnvironmentCollection(ArrayList<Environment> environments) {
        this.environments = environments;
    }

    public ArrayList<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(ArrayList<Environment> environments) {
        this.environments = environments;
    }

    @Override
    public ArrayList<Environment> filterField(String fieldName, String operator, Object... value) {
        return (ArrayList<Environment>) utils.select(this.getEnvironments(), fieldName, operator, value);
    }
}
