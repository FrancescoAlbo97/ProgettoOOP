package com.project.progettoOOP.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class FilterUtils<T> {

    private static boolean check(Object value, String operator, Object... th) {
        if (th.length == 1 && th[0] instanceof Number && value instanceof Number) {
            Float thC = ((Number)th[0]).floatValue();
            Float valueC = ((Number)value).floatValue();
            if (operator.equals("$eq"))
                return value.equals(th[0]);
            else if (operator.equals("$not"))
                return !value.equals(th[0]);
            else if (operator.equals("$gt"))
                return valueC > thC;
            else if (operator.equals("$lt"))
                return valueC < thC;
        }/*
        else if (th.length == 1 && th[0] instanceof String && value instanceof String) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateInString = "07/06/2013";
            Date date = formatter.parse(dateInString);
            if (operator.equals("$eq"))
                return value.equals(th[0]);
            else if (operator.equals("$not"))
                return !value.equals(th[0]);
            else if (operator.equals("$gt"))
                return valueC > thC;
            else if (operator.equals("$lt"))
                return valueC < thC;
        }*/
        else if(th.length > 1) {
            if (operator.equals("$bt")) {
                if(th.length == 2 && th[0] instanceof Number && th[1] instanceof Number) {
                    Float min = ((Number)th[0]).floatValue();
                    Float max = ((Number)th[1]).floatValue();
                    Float valueC = ((Number)value).floatValue();
                    return valueC > min && valueC < max;
                }
            }
        }
        return false;
    }

    public Collection<T> select(Collection<T> src, String fieldName, String operator, Object... value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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