package com.hospital.controllers;

import com.hospital.Patient;
import com.hospital.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Get the current patient from the session
        Session session = Session.getInstance();
        Patient patient = session.getCurrentPatient();

        if (patient == null) {
            // If no patient is logged in, redirect to login page (or another appropriate page)
            return "redirect:/login";
        }

        // Add the patient data and their appointments to the model
        model.addAttribute("patient", patient);
        model.addAttribute("appointments", patient.getAppointments());

        // Return the Thymeleaf template for the dashboard
        return "dashboard";
    }
}
