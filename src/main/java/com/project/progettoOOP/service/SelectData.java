package com.project.progettoOOP.service;

import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.utils.DateCustom;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe implementata per gestire i dati dell'array list
 */
public class SelectData {

    private ArrayList<Environment> environments;
    private ArrayList<Environment> selectedData;

    /**
     * Permette di prendere una collezione di dati.
     * @return Ritorna la collezione di dati.
     */
    public ArrayList<Environment> getEnvironments() {
        return environments;
    }

    /**
     * Permette di inserire una collezione
     * @param environments collezione da inserire.
     */
    public void setEnvironments(ArrayList<Environment> environments) {
        this.environments = environments;
    }

    /**
     * Permette di prendere una collezione di dati.
     * @return Ritorna la collezione di dati.
     */
    public ArrayList<Environment> getSelectedData() {
        return selectedData;
    }
    /**
     * Permette di inserire una collezione
     * @param selectedData collezione da inserire passata come parametro.
     */
    public void setSelectedData(ArrayList<Environment> selectedData) {
        this.selectedData = selectedData;
    }

    /**
     * Costruttore della classe
     * @param environments Array di oggetti  passato come parametro.
     */
    public SelectData(ArrayList<Environment> environments) {
        this.environments = environments;
        this.selectedData = new ArrayList<>();
    }

    /**
     * Verifica se il formato della data è giusto.
     * @param monthString array di stringhe contenente i mesi.
     * @param dayString array di stringhe contenente i giorni.
     */
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

    /**
     * Funzione booleana che garantisce che ogni mese ha il giusto numero di giorni.
     * @param month array di stringhe contenente i mesi.
     * @param day array di stringhe contenente i giorni.
     * @return falso nel caso i cui i numeri di giorni di un mese non sono inseriti correttamente,vero altrimenti.
     */
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

    /**
     * Funzione che seleziona solo gli oggetti che rispettano i giorni e mesi scelti dal utente.
     * @param daysForMonths
     */
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

    /**
     *
     * @param selectedData collezione di oggetti
     * @param molecule array di stringhe che rappresenta le molecole.
     * @throws NoSuchMethodException quando un metodo non viene trovato.
     * @throws InvocationTargetException è un'eccezione controllata
     * @throws IllegalAccessException viene lanciata quando un'applicazione prova
     * a creare in modo riflessivo un'istanza
     */
    public void selectByMolecule(ArrayList<Environment> selectedData, String[] molecule) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method m1 = null;
        Method m2 = null;
        ArrayList<Environment> arrayList = new ArrayList<>();
        for (Environment item : selectedData) {
            String nullValue = null;
            Environment environment = new Environment(item.getMyDate(),nullValue,nullValue,nullValue,nullValue,nullValue,nullValue);
            for (int i=0; i < molecule.length; i++){
                try {
                    m1 = item.getClass().getMethod("get" + molecule[i].substring(0, 1).toUpperCase() + molecule[i].substring(1), null);
                    Object value = m1.invoke(item);
                    if (!(value == null)) {
                        m2 = environment.getClass().getMethod("set" + molecule[i].substring(0, 1).toUpperCase() + molecule[i].substring(1), Float.class);
                        m2.invoke(environment, (Float) value);
                    }
                }catch (Exception e){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid molecule format, insert: \"no\", \"no2\", \"nox\", \"so2\", \"o3\", \"co\" ");
                }
            }
            arrayList.add(environment);
        }
        this.setSelectedData(arrayList);
    }

}
