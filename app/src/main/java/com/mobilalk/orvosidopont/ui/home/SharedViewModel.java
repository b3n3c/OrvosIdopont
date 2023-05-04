package com.mobilalk.orvosidopont.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobilalk.orvosidopont.model.Appointment;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> category = new MutableLiveData<>();
    private MutableLiveData<String> doctor = new MutableLiveData<>();
    private MutableLiveData<String> doctorName = new MutableLiveData<>();
    private MutableLiveData<String> serviceName = new MutableLiveData<>();
    private MutableLiveData<Integer> serviceDuration = new MutableLiveData<>();

    public String getServiceName() {
        return serviceName.getValue();
    }

    public void setServiceName(String serviceName) {
        this.serviceName.setValue(serviceName);
    }

    public Integer getServiceDuration() {
        return serviceDuration.getValue();
    }

    public void setServiceDuration(Integer serviceDuration) {
        this.serviceDuration.setValue(serviceDuration);
    }

    public String getCategory() {
        return category.getValue();
    }

    public void setCategory(String category) {
        this.category.setValue(category);
    }

    public String getDoctor() {
        return doctor.getValue();
    }

    public void setDoctor(String doctor) {
        this.doctor.setValue(doctor);
    }

    public String getDoctorName() {
        return doctorName.getValue();
    }

    public void setDoctorName(String doctor) {
        this.doctorName.setValue(doctor);
    }
}
