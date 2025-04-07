package com.hospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hospital.Hospital;
import com.hospital.Patient;
import com.hospital.Session;

@Controller
public class PatientLoginController {

    private final Hospital hospital = new Hospital();

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "patient-login";
    }

    @PostMapping("/login")
public String handleLogin(@RequestParam String name, @RequestParam String phone, Model model) {
    try {
        hospital.loginPatient(phone, name);
        Patient patient = Session.getInstance().getCurrentPatient();
        if (patient != null) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid name or phone");
            return "patient-login";
        }
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("error", "Something went wrong");
        return "patient-login";
    }
}

}
