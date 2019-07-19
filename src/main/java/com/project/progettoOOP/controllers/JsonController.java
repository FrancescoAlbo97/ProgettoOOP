package com.project.progettoOOP.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;
import com.project.progettoOOP.service.Filters;
import com.project.progettoOOP.service.SelectData;
import com.project.progettoOOP.service.Statistics;
import com.project.progettoOOP.utils.Statistic;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller dell'applicazione: è il cuore del''applicazione.Gestisce tutte le rotte dell'applicazione.
 */
@RestController
public class JsonController {
    /**
     * Rotta che mostra i metadati di ogni oggetto presente nel dataset.
     * @return Restituisce i metadati sotto forma di JSON.
     * @throws JsonProcessingException dovuta ai metodi generateSchema e writeValueAsString
     */
    @RequestMapping(value = "/metadata", method = RequestMethod.GET, produces="application/json")
    String getMetadata() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
            JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(Environment.class);
            return mapper.writeValueAsString(jsonSchema);
    }

    /**
     * Rotta che mostra i dati sotto forma di JSON.
     * @param monthString parametri preso dal URL.
     * @param dayString parametri preso dal URL.
     * @param molecule parametri preso dal URL.
     * @return Ritorna i dati sotto forma di JSON.
     * @throws NoSuchMethodException dovuto al  metodo selectByMolecule
     * @throws IllegalAccessException dovuto al  metodo selectByMolecule
     * @throws InvocationTargetException dovuto al  metodo selectByMolecule
     */
    @RequestMapping(value = "/data", method = RequestMethod.GET, produces="application/json")
    ArrayList<Environment> getData(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
            ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SelectData newData = new SelectData(EnvironmentCollection.environments);
        newData.checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")) {
            newData.selectByMolecule(newData.getSelectedData(),molecule);
        }
        return newData.getSelectedData();
    }

    /**
     * Rotta che permette di calcolare qualsiasi tipo di statistica sui dati.
     * @param monthString parametri preso dal URL.
     * @param dayString parametri preso dal URL.
     * @param molecule parametri preso dal URL.
     * @return Ritorna la statistica voluta.
     * @throws NoSuchMethodException quando un metodo non viene trovato.
     * @throws IllegalAccessException  viene lanciata quando un'applicazione prova
     * a creare in modo riflessivo un'istanza
     * @throws InvocationTargetException è un'eccezione controllata
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces="application/json")
    HashMap<String, Statistic<Environment>> getStatistics(
            @RequestParam(value = "month", required = false, defaultValue = "0") ArrayList<String> monthString,
            @RequestParam(value = "day", required = false, defaultValue = "0") ArrayList<String> dayString,
            @RequestParam(value = "molecule", required = false, defaultValue = "all") String[] molecule
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SelectData newData = new SelectData(EnvironmentCollection.environments);
        newData.checkDateFormat(monthString, dayString);
        if (!molecule[0].equals("all")) {
            newData.selectByMolecule(newData.getSelectedData(),molecule);
        }else{
            molecule = new String[]{"no", "no2", "nox", "so2", "o3", "co"};
        }
        HashMap<String, Statistic<Environment>> map;
        map = Statistics.getFilteredStatistics(newData.getSelectedData(), molecule);
        return map;
    }

    /**
     *
     * @param jsonString Filtri voluti dall'utente
     * @return I dati filtrati
     * @throws Exception eccezione generale
     */
    @RequestMapping(value="/filter", method=RequestMethod.POST, produces="application/json")
    public ArrayList<Environment> getFilteredValues(
            @RequestBody(required = false) String jsonString) throws Exception {
        JSONObject json = null;
        ArrayList<Environment> result = null;
        if(jsonString != null){
            json = new JSONObject(jsonString);
            result = Filters.getFilteredData(EnvironmentCollection.environments, json);
        }
        if(result != null){
            return result;
        } else return EnvironmentCollection.environments;
    }

    /**
     *
     * @param jsonString Filtri voluti dall'utente.
     * @return statistiche sui dati precedentemente filtrati
     * @throws Exception eccezione generale
     */
    @RequestMapping(value="/filter/statistics", method=RequestMethod.POST, produces="application/json")
    public HashMap<String, Statistic<Environment>> getStatisticsOfFilteredValues(
            @RequestBody(required = false) String jsonString) throws Exception {
        JSONObject json = null;
        ArrayList<Environment> result;
        ArrayList<Environment> startList;
        startList = getFilteredValues(jsonString);
        String[] molecule = {"no", "no2", "nox", "so2", "o3", "co"};
        json = new JSONObject(jsonString);
        result = Filters.getFilteredData(startList, json);
        HashMap<String, Statistic<Environment>> map;
        map = Statistics.getFilteredStatistics(result, molecule);
        return map;
    }

}