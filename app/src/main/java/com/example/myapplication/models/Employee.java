package com.example.myapplication.models;

public class Employee {
    private int id;
    private String name;
    private String doB;
    private String homeTown;
    private int position_id;

    public Employee(int id, String name, String doB, String homeTown, int position_id) {
        this.id = id;
        this.name = name;
        this.doB = doB;
        this.homeTown = homeTown;
        this.position_id = position_id;
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

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }
}
