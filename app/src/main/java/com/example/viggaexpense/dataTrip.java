package com.example.viggaexpense;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class dataTrip implements Serializable {
    private int id;
    private String name;
    private String level;
    private String desti;
    private String startDate;
    private String endDate;
    private String desc;
    private String parking;
    private String length;
    private String budget;
    public dataTrip(int id, String name, String level, String desti, String startDate, String endDate, String desc, String parking, String length, String budget) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.desti = desti;
        this.startDate = startDate;
        this.endDate = endDate;
        this.desc = desc;
        this.parking = parking;
        this.length = length;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesti() {
        return desti;
    }

    public void setDesti(String desti) {
        this.desti = desti;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}

