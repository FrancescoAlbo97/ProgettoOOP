
package com.project.progettoOOP.model;

import com.project.progettoOOP.Filter;
import com.project.progettoOOP.utils.FilterUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Classe che raggruppa tutti gli oggetti {@link Environment} in una collezione,
 */
public class EnvironmentCollection implements Filter<Environment, Object[]> {

    public static ArrayList<Environment> environments;
    private ArrayList<Environment> environmentsList;
    private FilterUtils<Environment> utils;
    /**
     * Costruttore dell'oggetto
     * @param environmentsList una ArrayList di {@link Environment}
     * @param utils utils viene istanziato per accedere ai metodi di FilterUtils
     */
    public EnvironmentCollection(ArrayList<Environment> environmentsList, FilterUtils<Environment> utils) {
        this.environmentsList = environmentsList;
        this.utils = utils;
    }
    /**
     * Costruttore dell'oggetto
     * @param environmentsList Una ArrayList di {@link Environment}
     */
    public EnvironmentCollection(ArrayList<Environment> environmentsList) {
        this.environmentsList = environmentsList;
        this.utils = new FilterUtils<>();
    }

    /**
     * Metodo che restituisce il contenuto della colezione.
     * @return restituisce la colezione di oggetti.
     */
    public ArrayList<Environment> getEnvironmentsList() {
        return environmentsList;
    }

    /**
     *Metodo capace di settare una nuova collezione di  {@link Environment} per la classe
     * @param environmentsList oggetti da inserire.
     */
    public void setEnvironments(ArrayList<Environment> environmentsList) {
        this.environmentsList = environmentsList;
    }
    /**
     * Metodo che permette di applicare i filtri specificati sulla collezione di oggetti.
     * @param fieldName Campo su cui vi vuole specificare la condizione di filtro.
     * @param operator Condizione di filtro.
     * @param value Valori che caratterizzano la condizione di filtro.
     * @return La collezione di oggetti filtrata.
     */
    @Override
    public ArrayList<Environment> filterField(String fieldName, String operator, Object... value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        return (ArrayList<Environment>) utils.select(this.getEnvironmentsList(), fieldName, operator, value);
    }
}
