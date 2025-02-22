
package com.project.progettoOOP.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
/**
 * Classe che permette di filtrare gli oggetti secondo i parametri specificati
 * @param <T> Tipo generico
 */
public class FilterUtils<T> {
    /**
     * Metodo che determina per ciscun oggetto della collezione se deve essere incluso nella risposta,in base al filtro, oppure no.
     * @param value Oggetto che viene considerato nel test
     * @param operator Condizione del filtro
     * @param th Oggetti che caratterizzano il filtro
     * @return Un booleano che determina se l'oggetto deve essere tenuto o meno
     * @throws ParseException Dovuto al metodo parse.
     */
    private static boolean check(Object value, String operator, Object... th) throws ParseException {
        if (th.length == 1 && th[0] instanceof Number && value instanceof Number) {
            Float thC = ((Number)th[0]).floatValue();
            Float valueC = ((Number)value).floatValue();
            if (operator.equals("$gt"))
                return valueC > thC;
            else if (operator.equals("$lt"))
                return valueC < thC;
        }else if (th.length == 1 && th[0] instanceof String && value instanceof String) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String dateInString = (String) th[0];
            String objDate = (String) value;
            Date thC = formatter.parse(dateInString); //2016-12-29 04:40:00.000
            Date valueC = formatter.parse(objDate);
            if (operator.equals("$gt"))
                return valueC.after(thC);
            else if (operator.equals("$lt"))
                return !valueC.after(thC);
        }
        else if(th.length > 1) {
            if (operator.equals("$bt")) {
                if (th.length == 2 && th[0] instanceof String && value instanceof String){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    String dateInStringMin = (String) th[0];
                    String dateInStringMax = (String) th[1];
                    String objDate = (String) value;
                    Date min = formatter.parse(dateInStringMin);
                    Date max = formatter.parse(dateInStringMax);
                    Date valueC = formatter.parse(objDate);
                    return valueC.after(min) && !valueC.after(max);
                }
                else if(th.length == 2 && th[0] instanceof Number && th[1] instanceof Number) {
                    Float min = ((Number)th[0]).floatValue();
                    Float max = ((Number)th[1]).floatValue();
                    Float valueC = ((Number)value).floatValue();
                    return valueC > min && valueC < max;
                }
            }
        }
        return false;
    }

    /**
     * Metodo che riceve l'intera collezione di oggetti ed il filtro e restituisce una collezione parziale con gli
     * oggetti selezionati
     * @param src L'intera collezione di oggetti
     * @param fieldName Campo su cui opera il filtro
     * @param operator Condizione del filtro
     * @param value Oggetti che caratterizzano il filtro
     * @return Collezione risultante
     * @throws NoSuchMethodException quando un metodo non viene trovato.
     * @throws InvocationTargetException
     * @throws IllegalAccessException viene lanciata quando un'applicazione prova a creare in modo riflessivo un'istanza
     * @throws ParseException dovuta al metodo check().
     */
    public Collection<T> select(Collection<T> src, String fieldName, String operator, Object... value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        Collection<T> out = new ArrayList<T>();
        for(T item:src) {
            Method m = null;
            m = item.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
            Object tmp = null;
            tmp = m.invoke(item);
            if (tmp != null){
                if (FilterUtils.check(tmp, operator, value)) {
                    out.add(item);
                }
            }
        }return out;
    }
}