package com.hospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hospital.Hospital;

@Controller
public class PatientRegistrationController {

    private Hospital hospital = new Hospital();

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "patient-registration";
    }

    @PostMapping("/register")
    public String registerPatient(
            @RequestParam("patientID") int patientID,
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("gender") String gender,
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") String phoneNumber
    ) {
        hospital.registerPatient(patientID, name, age, gender, address, phoneNumber);
        return "patient-login";
    }
}
