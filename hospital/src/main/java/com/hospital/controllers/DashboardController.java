package com.hospital.controllers;

import java.sql.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.Session;
import com.hospital.Appointment;
import com.hospital.Hospital;
import com.hospital.Patient;

@Controller
public class DashboardController {
    Hospital hospital = new Hospital();

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Patient patient = Session.getInstance().getCurrentPatient();

        if (patient != null) {
            model.addAttribute("patient", patient);
            model.addAttribute("appointments", patient.getAppointments());
            return "dashboard";
        } else {
            model.addAttribute("error", "No patient logged in");
            return "patient-login";
        }
    }

    //TODO:lesa el function di makhelsetsh
    @PostMapping("/appointments/add")
    public String addAppointment(@RequestParam String type,
                                @RequestParam Date date,
                                @RequestParam Time time,
                                @RequestParam int docID,
                                Model model) {
        Patient patient = Session.getInstance().getCurrentPatient();

        if (patient != null) {
            
            Appointment appointment = new Appointment(hospital.getNextAppointmentId(type),patient.getPatientID(),type,date,time,docID);

            patient.addAppointment(appointment); 
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "No patient logged in");
            return "patient-login";
        }
    }

}
