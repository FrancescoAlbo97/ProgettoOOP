package com.project.progettoOOP.utils;

public class DateCustom{

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer milliSecond;

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

    public DateCustom(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer milliSecond) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.milliSecond = milliSecond;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getMilliSecond() {
        return milliSecond;
    }

    public void setMilliSecond(Integer milliSecond) {
        this.milliSecond = milliSecond;
    }

    public String toString(){
        String result = (year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"."+milliSecond);
        return result;
    }

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
