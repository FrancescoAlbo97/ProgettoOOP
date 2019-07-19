package com.project.progettoOOP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.project.progettoOOP.utils.DateCustom;

import java.io.Serializable;

/**
 * Classe che modella un singolo elemento del dataset.Ogni elemento indica i valori di Monossido di azoto(no),biossido di azoto(no2)
 * altri ossidi di azoto(nox),biossido di zolfo(so2),ozono(o3) e monossido di carbonio(co) che sono calcolati nella citta di Varese,
 * durante l'anno 2016, ogni 10 minuti.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Environment implements Serializable {

    //Datetime and
    //Unvalidated 10 min averages in UTC time-base of NO, NO2, O3 and SO2, all in ppb, CO in ppm

    @JsonPropertyDescription("Data, ora, minuti del 2016 a Varese")
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

    /**
     * Metodo che permette di prendere la Data di tipo DateCustom.
     * @return Ritorna la Data.
     */
    public DateCustom getMyDate() {
        return myDate;
    }

    /**
     * Metodo che permette di inserire la Data di tipo DateCustom.
     * @param myDate Data da inserire.
     */
    public void setMyDate(DateCustom myDate) {
        this.myDate = myDate;
    }

    /**
     * Metodo che permette di prendere la Data di tipo stringa.
     * @return Ritorna la stringa che indica la Data.
     */
    public String getDate_time() {
        return date_time;
    }

    /**
     * Metodo che permette di inserire la Data di tipo stringa.
     * @param date_time Stringa da inserire che indica la Data.
     */
    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    /**
     * Metodo che permette di prendere la molecola di No.
     * @return Rotorna il valore della molecola di No.
     */
    public Float getNo() {
        return no;
    }

    /**
     * Metodo che permette di inserire la molecola No.
     * @param no Valore da inserire.
     */
    public void setNo(Float no) {
        this.no = no;
    }

    /**
     * Metodo che permette di prendere la molecola No2.
     * @return Ritrona il valore della molecola di No2
     */
    public Float getNo2() {
        return no2;
    }

    /**
     * Metodo che permette di inserire la molecola No2.
     * @param no2 Valore da inserire
     */
    public void setNo2(Float no2) {
        this.no2 = no2;
    }

    /**
     * Metodo che permette di prendere la molecola Nox.
     * @return Ritorna il valore della molecola di Nox.
     */
    public Float getNox() {
        return nox;
    }

    /**
     * Metodo che permette di inserire la molecola Nox.
     * @param nox Valore da inserire.
     */
    public void setNox(Float nox) {
        this.nox = nox;
    }

    /**
     * Metodo che permette di prendere la molecola So2.
     * @return Ritorna il valore della molecola di So2.
     */
    public Float getSo2() {
        return so2;
    }

    /**
     * Metodo che permette di inserire la molecola So2.
     * @param so2 Valore da inserire.
     */
    public void setSo2(Float so2) {
        this.so2 = so2;
    }

    /**
     * Metodo che permette di prendere la molecola O3.
     * @return Ritorna il valore della molecola O3.
     */
    public Float getO3() {
        return o3;
    }

    /**
     * Metodo che permette di inserire la molecola O3.
     * @param o3 Valore da inserire.
     */
    public void setO3(Float o3) {
        this.o3 = o3;
    }

    /**
     * Metodo che permette di inserire la molecola Co.
     * @return Ritorna il valore della molecola di Co.
     */
    public Float getCo() {
        return co;
    }

    /**
     * Metodo che permette di inserire la molecola Co.
     * @param co valore da inserire.
     */
    public void setCo(Float co) {
        this.co = co;
    }

    /**
     * Costruttore della classe.
     * @param date_time la Data passata come parametro.
     * @param no Indica la molecola di No.
     * @param no2 Indica la molecola di No2.
     * @param nox Indica la molecola di NoX.
     * @param so2 Indica la molecola di So2.
     * @param o3 Indica la molecola di O3.
     * @param co Indica la molecola di CO.
     */
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

    /**
     * Costruttore della classe.
     * @param no Indica la molecola di No.
     * @param no2 Indica la molecola di No2.
     * @param nox Indica la molecola di NoX.
     * @param so2 Indica la molecola di So2.
     * @param o3 Indica la molecola di O3.
     * @param co Indica la molecola di CO.
     */
    public Environment( Float no, Float no2, Float nox, Float so2, Float o3, Float co) {
        this.no = no;
        this.no2 = no2;
        this.nox = nox;
        this.so2 = so2;
        this.o3 = o3;
        this.co = co;
    }

    /**
     * Costruttore che serve per settare a null una variabile nel caso in cui il suo valore e nullo
     * @param no Indica la molecola di No.
     * @param no2 Indica la molecola di No2.
     * @param nox Indica la molecola di NoX.
     * @param so2 Indica la molecola di So2.
     * @param o3 Indica la molecola di O3.
     * @param co Indica la molecola di CO.
     */
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

    /**
     * Costruttore della classe che serve nel caso in cui la Data è una stringa.
     * @param date_time Indica la Data.
     * @param no Indica la molecola di No.
     * @param no2 Indica la molecola di No2.
     * @param nox Indica la molecola di NoX.
     * @param so2 Indica la molecola di So2.
     * @param o3 Indica la molecola di O3.
     * @param co Indica la molecola di CO.
     */
    public Environment(String date_time, String no, String no2, String nox, String so2, String o3, String co)  {
        this(no, no2, nox, so2, o3, co);
        DateCustom date = new DateCustom(date_time);
        this.date_time = date.toString();
        this.myDate = date;
    }

    /**
     * Costruttore che permette di trasformare la Data in tipo stringa.
     * @param date_time Indica la Data
     * @param no Indica la molecola di No.
     * @param no2 Indica la molecola di No2.
     * @param nox Indica la molecola di NoX.
     * @param so2 Indica la molecola di So2.
     * @param o3 Indica la molecola di O3.
     * @param co Indica la molecola di CO.
     */
    public Environment(DateCustom date_time, String no, String no2, String nox, String so2, String o3, String co)  {
        this(no, no2, nox, so2, o3, co);
        this.myDate = date_time;
        this.date_time = date_time.toString();
    }

    /**
     * Metodo che permette la visualizzazione
     * @return Ritorna l'elemento in formato stringa.
     */
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