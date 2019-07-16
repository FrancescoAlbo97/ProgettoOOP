package com.project.progettoOOP.utils;

/**
 * Serve per recuperare il formato data dal CSV senza avere il problema dell'ora legale.
 */
public class DateCustom{

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer milliSecond;

    /**
     * Costruttore che prende la Stringa,che rappresenta la data presa dal CSV, e la parsa.
     * @param dataString Stringa che rappresenta la data
     */
    public DateCustom(String dataString) {              //ex 2016-12-29 04:40:00.000
        String[] dataTime = dataString.split(" ");
        String[] data = dataTime[0].split("-");
        String[] time = dataTime[1].split(":");
        String[] milliSecond = time[2].split("\\.");
        this.year = Integer.parseInt(data[0]);
        this.month = Integer.parseInt(data[1]);
        this.day = Integer.parseInt(data[2]);
        this.hour = Integer.parseInt(time[0]);
        this.minute = Integer.parseInt(time[1]);
        this.second = Integer.parseInt(milliSecond[0]);
        this.milliSecond = Integer.parseInt(milliSecond[1]);
    }

    /**
     * Costruttore della classe
     * @param year indica l'anno
     * @param month indica il mese
     * @param day indica il giorno
     * @param hour indica l'ora
     * @param minute indica i minuti
     * @param second indica i secondi
     * @param milliSecond indica i millisecondi
     */
    public DateCustom(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer milliSecond) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.milliSecond = milliSecond;
    }

    /**
     * Metodo che mi permettere di prendere l'anno.
     * @return Ritorna l'anno
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Metodo che permette di inserire un anno
     * @param year Anno che si vuole inserire
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Metodo che mi permettere di prendere il mese.
     * @return Ritorna il mese.
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * Metodo che mi permettere di inserire un mese.
     * @param month Mese da inserire.
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * Metodo che mi permettere di prendere un giorno.
     * @return Ritorna il giorno.
     */
    public Integer getDay() {
        return day;
    }

    /**
     * Metodo che mi permettere di inserire un giorno.
     * @param day Giorno da inserire
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * Metodo che mi permettere di prendere l'ora
     * @return Ritorna l'ora.
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * Metodo che mi permettere di inserire l'ora.
     * @param hour Ora da inserire.
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     * Metodo che mi permettere di prendere i minuti.
     * @return Ritorna i minuti.
     */
    public Integer getMinute() {
        return minute;
    }

    /**
     * Metodo che mi permettere di inserire i minuti.
     * @param minute Minuti da inserire.
     */
    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    /**
     * Metodo che mi permettere di prendere i secondi.
     * @return Ritorna i secondi.
     */
    public Integer getSecond() {
        return second;
    }

    /**
     * Metodo che mi permettere di inserire i secondi.
     * @param second Secondi da inserire.
     */
    public void setSecond(Integer second) {
        this.second = second;
    }

    /**
     * Metodo che mi permettere di prendere i Millisecondi.
     * @return Ritorna i Millisecondi.
     */
    public Integer getMilliSecond() {
        return milliSecond;
    }

    /**
     * Metodo che mi permettere di inserire i Millisecondi.
     * @param milliSecond Millisecondi da inserire.
     */
    public void setMilliSecond(Integer milliSecond) {
        this.milliSecond = milliSecond;
    }

    /**
     * Metodo che fa vedere la data in formato stringa.
     * @return Ritorna la stringa.
     */
    public String toString(){  //ex 2016-12-29 04:40:00.000
        String result = (year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + "." + milliSecond);

        return result;
    }

    /**
     * Metodo statico che restiruisce quanti giorni ha il mese passato come parametro.
     * @param month Mese di cui interessa il Numero di Giorni.
     * @return Ritorna il numero di giorni.
     */
    public static int getDaysOfMonth(int month){
        if (month == 2) {
            return 29;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        else{
            return 31;
        }
    }

}
