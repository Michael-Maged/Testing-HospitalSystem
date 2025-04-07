package com.hospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.Session;
import com.hospital.Patient;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Patient patient = Session.getInstance().getCurrentPatient();
    
        if (patient != null) {
            model.addAttribute("patient", patient);
            model.addAttribute("appointments", patient.getAppointments()); // ✅ add this
            return "dashboard";
        } else {
            model.addAttribute("error", "No patient logged in");
            return "patient-login";
        }
    }
}
