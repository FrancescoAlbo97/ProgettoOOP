package com.project.progettoOOP.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

public class Environment implements Serializable {

    //Datetime and
    //Unvalidated 10 min averages in UTC time-base of NO, NO2, O3 and SO2, all in ppb, CO in ppm

    @JsonPropertyDescription("Data e ora esatta")
    @JsonFormat( timezone = "GMT+1", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private static AtomicLong idCounter = new AtomicLong();
    private Integer id;
    private Date date_time;
    private float no;
    private float no2;
    private float nox;
    private float so2;
    private float o3;
    private float co;

    private static Integer createID() {
        return Integer.valueOf(String.valueOf(idCounter.getAndIncrement()));
    }

    public Integer getId() {
        return id;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public float getNo() {
        return no;
    }

    public void setNo(float no) {
        this.no = no;
    }

    public float getNo2() {
        return no2;
    }

    public void setNo2(float no2) {
        this.no2 = no2;
    }

    public float getNox() {
        return nox;
    }

    public void setNox(float nox) {
        this.nox = nox;
    }

    public float getSo2() {
        return so2;
    }

    public void setSo2(float so2) {
        this.so2 = so2;
    }

    public float getO3() {
        return o3;
    }

    public void setO3(float o3) {
        this.o3 = o3;
    }

    public float getCo() {
        return co;
    }

    public void setCo(float co) {
        this.co = co;
    }

    public Environment(Date date_time, float no, float no2, float nox, float so2, float o3, float co) {
        id = createID();
        this.date_time = date_time;
        this.no = no;
        this.no2 = no2;
        this.nox = nox;
        this.so2 = so2;
        this.o3 = o3;
        this.co = co;
    }

    public Environment( float no, float no2, float nox, float so2, float o3, float co) {
        id = createID();
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
        //ParsePosition parsePosition = new ParsePosition(2);
        //date_time += "+01:00";
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        Date date = formatter.parse(date_time);
        this.date_time = date;
    }

    public Environment(){
        id = createID();
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