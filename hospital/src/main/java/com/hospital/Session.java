package com.hospital;

public class Session {
    private static Session instance;
    private Patient currentPatient;

    // Private constructor to prevent instantiation
    private Session() {
    }

    // Get the singleton instance of the session
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Set the current patient (when they log in)
    public void setCurrentPatient(Patient patient) {
        this.currentPatient = patient;
    }

    // Get the current patient
    public Patient getCurrentPatient() {
        return currentPatient;
    }

    // Clear the session (e.g., when logging out)
    public void clearSession() {
        this.currentPatient = null;
    }
}
