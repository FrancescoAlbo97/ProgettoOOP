package com.project.progettoOOP.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Environment implements Serializable {

    //Datetime and
    //Unvalidated 10 min averages in UTC time-base of NO, NO2, O3 and SO2, all in ppb, CO in ppm

    @JsonPropertyDescription("Data e ora esatta")
    private Date date_time;
    private float NO;
    private float NO2;
    private float NOx;
    private float SO2;
    private float O3;
    private float CO;

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public float getNO() {
        return NO;
    }

    public void setNO(float NO) {
        this.NO = NO;
    }

    public float getNO2() {
        return NO2;
    }

    public void setNO2(float NO2) {
        this.NO2 = NO2;
    }

    public float getNOx() {
        return NOx;
    }

    public void setNOx(float NOx) {
        this.NOx = NOx;
    }

    public float getSO2() {
        return SO2;
    }

    public void setSO2(float SO2) {
        this.SO2 = SO2;
    }

    public float getO3() {
        return O3;
    }

    public void setO3(float o3) {
        O3 = o3;
    }

    public float getCO() {
        return CO;
    }

    public void setCO(float CO) {
        this.CO = CO;
    }

    public Environment(float no, float no2, float nox, float so2, float o3, float co){
        //this.date_time = date_time;
        NO = no;
        NO2 = no2;
        NOx = nox;
        SO2 = so2;
        O3 = o3;
        CO = co;
    }

    public Environment(Date date_time, float no, float no2, float nox, float so2, float o3, float co){
        this.date_time = date_time;
        NO = no;
        NO2 = no2;
        NOx = nox;
        SO2 = so2;
        O3 = o3;
        CO = co;
    }

    public Environment(String date_time, String no, String no2, String nox, String so2, String o3, String co) throws ParseException {
        this(Float.parseFloat(no), Float.parseFloat(no2), Float.parseFloat(nox), Float.parseFloat(so2), Float.parseFloat(o3), Float.parseFloat(co));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = formatter.parse(date_time);
        this.date_time = date;
    }

    public Environment(){}

    @Override
    public String toString() {
        return "Environment{" +
                "date_time=" + date_time +
                ", NO=" + NO +
                ", NO2=" + NO2 +
                ", NOx=" + NOx +
                ", SO2=" + SO2 +
                ", O3=" + O3 +
                ", CO=" + CO +
                '}';
    }

}