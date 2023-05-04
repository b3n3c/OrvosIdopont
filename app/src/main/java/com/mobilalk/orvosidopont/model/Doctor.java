package com.mobilalk.orvosidopont.model;

import java.util.HashMap;

public class Doctor {
    String name, id;
    HashMap<String, Integer> services;

    public Doctor(String name, String id, HashMap<String, Integer> services) {
        this.name = name;
        this.id = id;
        this.services = services;
    }

    public Doctor(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getServices() {
        return services;
    }

    public void setServices(HashMap<String, Integer> services) {
        this.services = services;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
