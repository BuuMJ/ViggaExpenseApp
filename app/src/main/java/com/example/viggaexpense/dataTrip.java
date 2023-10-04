package com.example.viggaexpense;

public class dataTrip {
    private int id;
    private String name;
    private String budget;
    private String desti;
    private String startDate;
    private String endDate;
    private String desc;

    public dataTrip(int id, String name, String budget, String desti, String startDate, String endDate, String desc) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.desti = desti;
        this.startDate = startDate;
        this.endDate = endDate;
        this.desc = desc;
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

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
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
}
