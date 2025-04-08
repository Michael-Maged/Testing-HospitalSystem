package com.hospital.controllers;

import com.hospital.*;


import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    Hospital hospital = new Hospital();
    Admin admin = new Admin();

    @GetMapping
    public String getAdminDashboard(Model model) {
        hospital.fetchAppointments();
        hospital.fetchDoctors();
        hospital.fetchMedicalRecords();
        model.addAttribute("doctors", hospital.getDoctors());
        model.addAttribute("inventory", hospital.getInventory());
        model.addAttribute("records", hospital.getRecords());
        return "adminPage";
    }

    //TODO: Function te automate el docID w el itemID mn el database 
    // POST: Add doctor
    @PostMapping("/add-doctor")
    public String addDoctor(@RequestParam int age,
                            @RequestParam String name,
                            @RequestParam String gender,
                            @RequestParam String specialty) {
        admin.addDoctor(hospital.getNextDoctorId(),name,age,gender,specialty);
        hospital.getDoctors().add(new Doctor(hospital.getNextDoctorId(), name, age, gender, specialty));
        return "redirect:/admin";
    }

    // POST: Delete doctor
    @PostMapping("/delete-doctor")
    public String deleteDoctor(@RequestParam int id) {
        admin.deleteDoctor(id);
        hospital.getDoctors().remove(hospital.findById(hospital.getDoctors(), id));
        return "redirect:/admin";
    }

    // POST: Add inventory item
    @PostMapping("/add-inventory")
    public String addInventory(@RequestParam String name,
                            @RequestParam int quantity) {
        admin.addInventoryItem(hospital.getNextInventoryId(), name, quantity);
        hospital.getInventory().add(new InventoryItem(hospital.getNextInventoryId(), name, quantity));
        return "redirect:/admin";
    }

    // POST: Delete inventory item
    @PostMapping("/delete-inventory")
    public String deleteInventory(@RequestParam int id) {
        admin.deleteInventoryItem(id);
        hospital.getInventory().remove(hospital.findById(hospital.getInventory(), id));
        return "redirect:/admin";
    }

    @PostMapping("/add-medical-record")
    public String addMedicalRecord(@RequestParam int patientId,
                               @RequestParam String diagnosis,
                               @RequestParam String treatment, 
                               @RequestParam Date date
                               ) {
    hospital.addMedicalRecord(hospital.getNextRecordId(), patientId, diagnosis, treatment, date);
    return "redirect:/admin";
    }

    @PostMapping("/delete-record")
    public String deleteRecord(@RequestParam int id) {
        admin.deleteMedicalRecord(id);
        hospital.getRecords().remove(hospital.findById(hospital.getRecords(), id));
        return "redirect:/admin";
    }
}
