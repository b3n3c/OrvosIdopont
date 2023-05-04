package com.mobilalk.orvosidopont.model;

public class Appointment {
    String doctorID;
    String email;
    String startDate;
    String endDate;
    String service;
    String doctorName;
    String id;

    public Appointment(String doctorID, String email, String startDate, String endDate, String service, String doctorName, String id) {
        this.doctorID = doctorID;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.service = service;
        this.doctorName = doctorName;
        this.id = id;
    }

    public Appointment(){}

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
