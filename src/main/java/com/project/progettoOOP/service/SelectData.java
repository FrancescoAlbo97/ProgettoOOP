package com.project.progettoOOP.service;

import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.utils.DateCustom;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class SelectData {

    private ArrayList<Environment> environments;
    private ArrayList<Environment> selectedData;

    public ArrayList<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(ArrayList<Environment> environments) {
        this.environments = environments;
    }

    public ArrayList<Environment> getSelectedData() {
        return selectedData;
    }

    public void setSelectedData(ArrayList<Environment> selectedData) {
        this.selectedData = selectedData;
    }

    public SelectData(ArrayList<Environment> environments) {
        this.environments = environments;
        this.selectedData = new ArrayList<>();
    }

    public void checkDateFormat(ArrayList<String> monthString, ArrayList<String> dayString){
        if (monthString.get(0).equals("0")) {
            monthString.clear();
            for (int i = 0; i < 12; i++){
                int value = i + 1;
                monthString.add(String.valueOf(value));
            }
        }
        if (!checkDateAndParse( monthString, dayString)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid date format");
        }
    }

    private boolean checkDateAndParse( ArrayList<String> month, ArrayList<String> day) {
        HashMap<Integer,ArrayList<Integer>> daysForMonths = new HashMap<>();
        for (int i = 0; i < month.size(); i++) {
            if (month.get(i).matches("^([1-9]|1[012])$")) {
                int monthValue = Integer.parseInt(month.get(i));
                if (!(day.get(0).equals("0"))) {
                    if (monthValue == 2) {
                        ArrayList<Integer> days = new ArrayList<>();
                        for (int j = 0; j < day.size(); j++) {
                            if (day.get(j).matches("^([1-9]|1[1-9]|2[1-9])$")) {
                                days.add(Integer.parseInt(day.get(j)));
                            } else return false;
                        }
                        daysForMonths.put(monthValue,days);
                    }
                    if (monthValue == 4 || monthValue == 6 || monthValue == 9 || monthValue == 11) {
                        ArrayList<Integer> days = new ArrayList<>();
                        for (int j = 0; j < day.size(); j++) {
                            if (day.get(j).matches("^([1-9]|1[1-9]|2[1-9]|30)$")) {
                                days.add(Integer.parseInt(day.get(j)));
                            } else return false;
                        }
                        daysForMonths.put(monthValue,days);
                    }
                    else {
                        ArrayList<Integer> days = new ArrayList<>();
                        for (int j = 0; j < day.size(); j++) {
                            if (day.get(j).matches("^([1-9]|1[1-9]|2[1-9]|3[01])$")) {
                                days.add(Integer.parseInt(day.get(j)));
                            } else return false;
                        }
                        daysForMonths.put(monthValue,days);
                    }
                } else {
                    ArrayList<Integer> days = new ArrayList<>();
                    int dayOfMonth = DateCustom.getDaysOfMonth(monthValue);
                    for (int j = 1; j <= dayOfMonth; j++) {
                        days.add(j);
                    }
                    daysForMonths.put(monthValue,days);
                }
            } else{
                return false;
            }
        }
        selectDataByMonthAndDate(daysForMonths);
        return true;
    }

    private void selectDataByMonthAndDate( HashMap<Integer,ArrayList<Integer>> daysForMonths){
        for (Environment obj : environments) {
            for (Integer m : daysForMonths.keySet()){
                if (obj.getMyDate().getMonth().intValue() == m.intValue()){
                    for (Integer d: daysForMonths.get(m)){
                        if(obj.getMyDate().getDay().intValue() == d.intValue()){
                            selectedData.add(obj);
                        }
                    }
                }
            }
        }
    }

    public void selectByMolecule(ArrayList<Environment> selectedData, String[] molecule) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method m1 = null;
        Method m2 = null;
        ArrayList<Environment> arrayList = new ArrayList<>();
        for (Environment item : selectedData) {
            String nullValue = null;
            Environment environment = new Environment(item.getMyDate(),nullValue,nullValue,nullValue,nullValue,nullValue,nullValue);
            for (int i=0; i < molecule.length; i++){
                m1 = item.getClass().getMethod("get"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1),null);
                Object value = m1.invoke(item);
                if (!(value == null)){
                    m2 = environment.getClass().getMethod( "set"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1), Float.class);
                    m2.invoke(environment, (Float) value);
                }
            }
            arrayList.add(environment);
        }
        this.setSelectedData(arrayList);
    }

}
