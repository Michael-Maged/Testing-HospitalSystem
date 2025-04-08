package com.hospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hospital.Hospital;

@Controller
public class PatientRegistrationController {

    private Hospital hospital = new Hospital();
    @GetMapping("/register")
    public String showRegistrationForm() {
        hospital.fetchPatients();
        return "patient-registration";
    }

    @PostMapping("/register")
    public String registerPatient(
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("gender") String gender,
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") String phoneNumber,
            Model model
    ) {
        // Validate full name: only letters and spaces
        if (!name.matches("^[A-Za-z]+( [A-Za-z]+)*$")) {
            model.addAttribute("error", "Invalid name. Use letters and spaces only.");
            return "patient-registration";
        }

        // Validate age > 0
        if (age <= 0) {
            model.addAttribute("error", "Age must be a positive number.");
            return "patient-registration";
        }

        // Validate phone number: 11 digits, starts with 012 or 010
        if (!phoneNumber.matches("^01[20][0-9]{8}$")) {
            model.addAttribute("error", "Phone number must be 11 digits and start with 012 or 010.");
            return "patient-registration";
        }

        //TODO: handle registration of duplicate phone number
        // Check for duplicate phone number
        if (hospital.phoneExists(phoneNumber)) {
            model.addAttribute("error", "Phone number is already registered.");
            return "patient-registration";
        }

        // Register if all is valid
        hospital.registerPatient(name, age, gender, address, phoneNumber);
        return "patient-login";
    }
}
