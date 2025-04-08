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
            model.addAttribute("appointments", patient.FetchUserAppointments());
            model.addAttribute("records", patient.FetchUserRecords());            
            hospital.fetchDoctors();
            model.addAttribute("doctors", hospital.getDoctors()); // Pass doctors list for dropdown
            
            return "dashboard"; // Pass the updated model to the template
        } else {
            model.addAttribute("error", "No patient logged in");
            return "patient-login";
        }
    }

    // Handle the form submission for adding a new appointment with the selected doctor
    @PostMapping("/appointments/add")
    public String addAppointment(@RequestParam Date date,
                                @RequestParam String time,
                                @RequestParam String Docname, // Doctor ID selected from the dropdown
                                Model model) {
        Patient patient = Session.getInstance().getCurrentPatient();

        if (patient != null) {
            if (time.length() == 5) {
                time += ":00";
            }

            Time sqlTime = Time.valueOf(time);
            
            // Create a new appointment with the selected doctor ID
            Appointment appointment = new Appointment(hospital.getNextAppointmentId(), patient.getPatientID(), hospital.findDoctorByName(Docname).getSpecialty(), date, sqlTime, hospital.findDoctorByName(Docname).getDoctorID());
            hospital.scheduleAppointment(hospital.getNextAppointmentId(), patient.getPatientID(),  hospital.findDoctorByName(Docname).getSpecialty(), date, sqlTime,  hospital.findDoctorByName(Docname).getDoctorID());
            patient.addAppointment(appointment);
            
            return "redirect:/dashboard"; // Redirect to dashboard after appointment is added
        } else {
            model.addAttribute("error", "No patient logged in");
            return "patient-login"; // Redirect to login if no patient is logged in
        }
    }

    // Handle the cancellation of an appointment
    @PostMapping("/appointments/cancel")
    public String deleteAppointment(@RequestParam int id) {
        hospital.cancelAppointment(id);
        hospital.getAppointments().remove(hospital.findById(hospital.getAppointments(), id));
        return "redirect:/dashboard"; // Redirect back to the dashboard
    }
}
