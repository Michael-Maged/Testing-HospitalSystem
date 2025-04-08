package com.hospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.hospital.Hospital;

@Controller
public class HomepageController {

    @GetMapping("/")
    public String showHomepage() {
        Hospital hospital = new Hospital();
        hospital.fetchAppointments();
        hospital.fetchDoctors();
        hospital.fetchMedicalRecords();
        hospital.fetchPatients();
        return "homepage";
    }
}
