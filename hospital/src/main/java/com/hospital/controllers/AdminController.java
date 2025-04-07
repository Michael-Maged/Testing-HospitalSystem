package com.hospital.controllers;

import com.hospital.*;

import org.springframework.beans.factory.annotation.Autowired;
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
        model.addAttribute("doctors", hospital.getDoctors());
        model.addAttribute("inventory", hospital.getInventory());
        return "adminPage";
    }

    // POST: Add doctor
    @PostMapping("/add-doctor")
    public String addDoctor(@RequestParam int age,
                            @RequestParam Long Docid,
                            @RequestParam String name,
                            @RequestParam String gender,
                            @RequestParam String specialty) {
        admin.addDoctor(Docid,name,age,gender,specialty);
        hospital.getDoctors().add(new Doctor(age, name, gender, specialty));
        return "redirect:/admin";
    }

    // POST: Delete doctor
    @PostMapping("/delete-doctor")
    public String deleteDoctor(@RequestParam Long id) {
        admin.deleteDoctor(id);
        hospital.getDoctors().remove(hospital.findById(hospital.getDoctors(), id));
        return "redirect:/admin";
    }

// POST: Add inventory item
@PostMapping("/add-inventory")
public String addInventory(@RequestParam Long itemID,
                           @RequestParam String name,
                           @RequestParam int quantity) {
    admin.addInventoryItem(itemID, name, quantity);
    hospital.getInventory().add(new InventoryItem(itemID, name, quantity));
    return "redirect:/admin";
}

// POST: Delete inventory item
@PostMapping("/delete-inventory")
public String deleteInventory(@RequestParam int id) {
    admin.deleteInventoryItem(id);
    hospital.getInventory().remove(hospital.findById(hospital.getInventory(), id));
    return "redirect:/admin";
}
}
