package com.project.progettoOOP.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.project.progettoOOP.utils.DateCustom;

import java.io.Serializable;
import java.text.ParseException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Environment implements Serializable {

    //Datetime and
    //Unvalidated 10 min averages in UTC time-base of NO, NO2, O3 and SO2, all in ppb, CO in ppm

    @JsonPropertyDescription("Data, ora, minuti del 2016 a Varese")
    @JsonFormat( timezone = "GMT+1", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private String date_time;
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
    @JsonIgnore
    private DateCustom myDate;

    public DateCustom getMyDate() {
        return myDate;
    }

    public void setMyDate(DateCustom myDate) {
        this.myDate = myDate;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public Float getNo() {
        return no;
    }

    public void setNo(Float no) {
        this.no = no;
    }
    public void setNo(float no) {
        this.no = no;
    }

    public Float getNo2() {
        return no2;
    }

    public void setNo2(Float no2) {
        this.no2 = no2;
    }
    public void setNo2(float no2) {
        this.no2 = no2;
    }


    public Float getNox() {
        return nox;
    }

    public void setNox(Float nox) {
        this.nox = nox;
    }
    public void setNox(float nox) {
        this.nox = nox;
    }

    public Float getSo2() {
        return so2;
    }

    public void setSo2(Float so2) {
        this.so2 = so2;
    }
    public void setSo2(float so2) {
        this.so2 = so2;
    }

    public Float getO3() {
        return o3;
    }

    public void setO3(Float o3) {
        this.o3 = o3;
    }
    public void setO3(float o3) {
        this.o3 = o3;
    }

    public Float getCo() {
        return co;
    }

    public void setCo(Float co) {
        this.co = co;
    }
    public void setCo(float co) {
        this.co = co;
    }


    public Environment(DateCustom date_time, Float no, Float no2, Float nox, Float so2, Float o3, Float co) {
        this.myDate = date_time;
        this.date_time = date_time.toString();
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

    public Environment( String no, String no2, String nox, String so2, String o3, String co) {
       if (no == null) setNo(null);
       else setNo(Float.parseFloat(no));
        if (no2 == null) setNo2(null);
        else setNo2(Float.parseFloat(no2));
        if (nox == null) setNox(null);
        else setNox(Float.parseFloat(nox));
        if (so2 == null) setSo2(null);
        else setSo2(Float.parseFloat(so2));
        if (o3 == null) setO3(null);
        else setO3(Float.parseFloat(o3));
        if (co == null) setCo(null);
        else setCo(Float.parseFloat(co));
    }

    public Environment(String date_time, String no, String no2, String nox, String so2, String o3, String co) throws ParseException {
        this(no, no2, nox, so2, o3, co);
        DateCustom date = new DateCustom(date_time);
        this.date_time = date.toString();
        this.myDate = date;
    }

    public Environment(DateCustom date_time, String no, String no2, String nox, String so2, String o3, String co) throws ParseException {
        this(no, no2, nox, so2, o3, co);
        this.myDate = date_time;
        this.date_time = date_time.toString();
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