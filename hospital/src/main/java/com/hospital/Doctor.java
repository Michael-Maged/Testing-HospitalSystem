package com.hospital;

public class Doctor {
    private int doctorID;
    private String name;
    private int age;
    private String gender;
    private String specialty;

    // Constructor
    public Doctor(int doctorID, String name, int age, String gender, String specialty) {
        this.doctorID = doctorID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.specialty = specialty;
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

    // toString
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorID=" + doctorID +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
