package com.mobilalk.orvosidopont.model;

import java.util.ArrayList;

public class Specialization {
    String name;
    ArrayList<String> doctors;

    public Specialization(String name, ArrayList<String> doctors) {
        this.name = name;
        this.doctors = doctors;
    }

    public Specialization(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<String> doctors) {
        this.doctors = doctors;
    }
}
