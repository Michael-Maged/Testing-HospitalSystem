package com.hospital.controllers;

import com.hospital.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping
    public String getAdminDashboard(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("inventory", inventoryRepository.findAll());
        model.addAttribute("records", medicalRecordRepository.findAll());
        return "adminPage";
    }

    // POST: Add doctor
    @PostMapping("/add-doctor")
    public String addDoctor(@RequestParam String name,
                            @RequestParam String specialty) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialty(specialty);
        doctorRepository.save(doctor);
        return "redirect:/admin";
    }

    // POST: Delete doctor
    @PostMapping("/delete-doctor")
    public String deleteDoctor(@RequestParam Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/admin";
    }

    // POST: Add inventory item
    @PostMapping("/add-inventory")
    public String addInventory(@RequestParam String name,
                               @RequestParam int quantity) {
        InventoryItem item = new InventoryItem();
        item.setName(name);
        item.setQuantity(quantity);
        inventoryRepository.save(item);
        return "redirect:/admin";
    }

    // POST: Delete inventory item
    @PostMapping("/delete-inventory")
    public String deleteInventory(@RequestParam Long id) {
        inventoryRepository.deleteById(id);
        return "redirect:/admin";
    }
}
