package com.project.progettoOOP.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Environment implements Serializable {

    //Datetime and
    //Unvalidated 10 min averages in UTC time-base of NO, NO2, O3 and SO2, all in ppb, CO in ppm

    @JsonPropertyDescription("Data, ora, minuti del 2016 a Varese")
    @JsonFormat( timezone = "GMT+1", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date date_time;
    @JsonPropertyDescription("Monossido di azoto")
    private Float no;
    @JsonPropertyDescription("Biossido di azoto")
    private Float no2;
    @JsonPropertyDescription("Altri ossidi di azoto")
    private Float nox;
    @JsonPropertyDescription("Biossido di zolfo")
    private Float so2;
    @JsonPropertyDescription("Ozono")
    private Float o3;
    @JsonPropertyDescription("Monossido di carbonio")
    private Float co;

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public Float getNo() {
        return no;
    }

    public void setNo(float no) {
        this.no = no;
    }

    public Float getNo2() {
        return no2;
    }

    public void setNo2(float no2) {
        this.no2 = no2;
    }

    public Float getNox() {
        return nox;
    }

    public void setNox(float nox) {
        this.nox = nox;
    }

    public Float getSo2() {
        return so2;
    }

    public void setSo2(float so2) {
        this.so2 = so2;
    }

    public Float getO3() {
        return o3;
    }

    public void setO3(float o3) {
        this.o3 = o3;
    }

    public Float getCo() {
        return co;
    }

    public void setCo(float co) {
        this.co = co;
    }
/*
    public void selectMolecules(String[] molecule, float[] value, Date date, Environment environment) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = null;
        this.setDate_time(date);
        for (int i = 0; i < molecule.length; i++){
            m = environment.getClass().getMethod("set"+molecule[i].substring(0, 1).toUpperCase()+molecule[i].substring(1),Float.class);
            m.invoke(this, value[i]);
        }
    }*/

    public Environment(Date date_time, Float no, Float no2, Float nox, Float so2, Float o3, Float co) {
        this.date_time = date_time;
        this.no = no;
        this.no2 = no2;
        this.nox = nox;
        this.so2 = so2;
        this.o3 = o3;
        this.co = co;
    }

    public Environment( Float no, Float no2, Float nox, Float so2, Float o3, Float co) {
        this.no = no;
        this.no2 = no2;
        this.nox = nox;
        this.so2 = so2;
        this.o3 = o3;
        this.co = co;
    }

    public Environment(String date_time, String no, String no2, String nox, String so2, String o3, String co) throws ParseException {
        this(Float.parseFloat(no), Float.parseFloat(no2), Float.parseFloat(nox), Float.parseFloat(so2), Float.parseFloat(o3), Float.parseFloat(co));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        Date date = formatter.parse(date_time);
        this.date_time = date;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "date_time=" + date_time +
                ", no=" + no +
                ", no2=" + no2 +
                ", nox=" + nox +
                ", so2=" + so2 +
                ", o3=" + o3 +
                ", co=" + co +
                '}';
    }

}