
package com.project.progettoOOP.model;

import com.project.progettoOOP.Filter;
import com.project.progettoOOP.utils.FilterUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EnvironmentCollection implements Filter<Environment, Object[]> {

    public static ArrayList<Environment> environments;
    private ArrayList<Environment> environmentsList;
    private FilterUtils<Environment> utils;

    public EnvironmentCollection(ArrayList<Environment> environmentsList, FilterUtils<Environment> utils) {
        this.environmentsList = environmentsList;
        this.utils = utils;
    }

    public EnvironmentCollection(ArrayList<Environment> environmentsList) {
        this.environmentsList = environmentsList;
        this.utils = new FilterUtils<>();
    }

    public ArrayList<Environment> getEnvironmentsList() {
        return environmentsList;
    }

    public void setEnvironments(ArrayList<Environment> environmentsList) {
        this.environmentsList = environmentsList;
    }

    @Override
    public ArrayList<Environment> filterField(String fieldName, String operator, Object... value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (ArrayList<Environment>) utils.select(this.getEnvironmentsList(), fieldName, operator, value);
    }
}
