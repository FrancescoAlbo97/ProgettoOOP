package com.project.progettoOOP.service;

import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.utils.Statistic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe che permette di prendere le statistiche sui filtri fatti.
 */
public class Statistics {

    public static HashMap<String, Statistic<Environment>> getFilteredStatistics(ArrayList<Environment> arrayList, String[] molecule) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        HashMap<String, Statistic<Environment>> allStatsmap = new HashMap<>();
        for(int i = 0; i < molecule.length; i++) {
            Statistic<Environment> singleStat = new Statistic<Environment>(arrayList, molecule[i]);
            allStatsmap.put(molecule[i],singleStat);
        }
        return allStatsmap;
    }
}
