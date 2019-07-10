
package com.project.progettoOOP.model;

import com.project.progettoOOP.Filter;
import com.project.progettoOOP.utils.FilterUtils;

import java.util.ArrayList;

public class EnvironmentCollection implements Filter<Environment, Object[]> {

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

    public static ArrayList<Environment> environments;
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
