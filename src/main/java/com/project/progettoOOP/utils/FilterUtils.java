package com.project.progettoOOP.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class FilterUtils<T> {
    private static boolean check(Object value, String operator, Object... th) {
        if (th.length == 1 && th[0] instanceof Number && value instanceof Number) {
            Double thC = ((Number)th[0]).doubleValue();
            Double valueC = ((Number)value).doubleValue();
            if (operator.equals("$eq"))
                return value.equals(th[0]);
            else if (operator.equals("$not"))
                return !value.equals(th[0]);
            else if (operator.equals("$gt"))
                return valueC > thC;
            else if (operator.equals("$lt"))
                return valueC < thC;
        } else if(th.length > 1) {
            if (operator.equals("$bt")) {
                if(th.length == 2 && th[0] instanceof Number && th[1] instanceof Number) {
                    Double min = ((Number)th[0]).doubleValue();
                    Double max = ((Number)th[1]).doubleValue();
                    Double valueC = ((Number)value).doubleValue();
                    return valueC > min && valueC < max;
                }
            }
            else if (operator.equals("$in"))
                return Arrays.asList(th).contains(value);
            else if (operator.equals("$nin"))
                return !Arrays.asList(th).contains(value);
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