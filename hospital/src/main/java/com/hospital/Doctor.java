package com.hospital;

public class Doctor {
    private int doctorID;
    private String name;
    private String specialty;
    private String phone;

    // Constructor
    public Doctor(int doctorID, String name, String specialty, String phone) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
    }

    // Getters
    public int getDoctorID() {
        return doctorID;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // toString
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorID=" + doctorID +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
