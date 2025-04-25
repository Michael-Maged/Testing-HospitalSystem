package com.hospital.controllers;

import com.hospital.*;
import java.sql.Date;
import java.util.regex.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    Hospital hospital = new Hospital();
    Admin admin = new Admin();

    // Regex patterns for validation
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+$");
    private static final Pattern ITEM_NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    @GetMapping
    public String getAdminDashboard(Model model) {
        hospital.fetchAppointments();
        hospital.fetchDoctors();
        hospital.fetchMedicalRecords();
        hospital.fetchPatients();
        hospital.fetchInventoryItems();
        hospital.fetchBills();
        model.addAttribute("doctors", hospital.getDoctors());
        model.addAttribute("inventory", hospital.getInventory());
        model.addAttribute("records", hospital.getRecords());
        model.addAttribute("departments", hospital.getDepartments());
        model.addAttribute("patients", hospital.getPatients());
        model.addAttribute("hospital", hospital);
        model.addAttribute("bills", hospital.getBills());
        return "adminPage";
    }

    // POST: Add doctor with validation
    @PostMapping("/add-doctor")
    public String addDoctor(@RequestParam int age,
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam String specialty,
            RedirectAttributes redirectAttributes) {
        // Validate doctor name
        if (!NAME_PATTERN.matcher(name).matches()) {
            redirectAttributes.addFlashAttribute("errorDoctors", "Doctor name should only contain letters and spaces.");
            return "redirect:/admin";
        }

        // Validate doctor age (must be greater than 25)
        if (age <= 25) {
            redirectAttributes.addFlashAttribute("errorDoctors", "Doctor's age should be greater than 25.");
            return "redirect:/admin";
        }

        admin.addDoctor(hospital.getNextDoctorId(), name, age, gender, specialty);
        hospital.getDoctors().add(new Doctor(hospital.getNextDoctorId(), name, age, gender, specialty));
        return "redirect:/admin";
    }

    // POST: Delete doctor
    @PostMapping("/delete-doctor")
    public String deleteDoctor(@RequestParam int id, RedirectAttributes redirectAttributes) {
        if(hospital.getAppointments().stream().anyMatch(a -> a.getDocID() == id)) {
            redirectAttributes.addFlashAttribute("errorDoctors", "Doctor has appointments and cannot be deleted.");
            return "redirect:/admin";
        }
        admin.deleteDoctor(id);
        hospital.getDoctors().remove(hospital.findById(hospital.getDoctors(), id));
        return "redirect:/admin";
    }

    // POST: Add inventory item with validation
    @PostMapping("/add-inventory")
    public String addInventory(@RequestParam String name,
            @RequestParam int quantity,
            RedirectAttributes redirectAttributes) {
        // Validate item name
        if (!ITEM_NAME_PATTERN.matcher(name).matches()) {
            redirectAttributes.addFlashAttribute("errorInventory", "Item name should only contain letters and spaces.");
            return "redirect:/admin";
        }

        // Validate item quantity (should be 0 or more)
        if (quantity < 0) {
            redirectAttributes.addFlashAttribute("errorInventory", "Item quantity should be 0 or more.");
            return "redirect:/admin";
        }

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

    // POST: Add medical record
    @PostMapping("/add-medical-record")
    public String addMedicalRecord(@RequestParam int patientId,
            @RequestParam String diagnosis,
            @RequestParam String treatment,
            @RequestParam Date date,
            RedirectAttributes redirectAttributes) {
            // Check if the patient exists
            Patient patient = hospital.findPatientById(patientId);
            if (patient == null) {
                redirectAttributes.addFlashAttribute("errorMedical", "Patient ID not found.");
                return "redirect:/admin";
            }
            admin.addMedicalRecord(hospital.getNextRecordId(), patientId, diagnosis, treatment, date);
            return "redirect:/admin";
    }

    // POST: Delete record
    @PostMapping("/delete-record")
    public String deleteRecord(@RequestParam int id) {
        admin.deleteMedicalRecord(id);
        hospital.getRecords().remove(hospital.findById(hospital.getRecords(), id));
        return "redirect:/admin";
    }

    // POST: Add a new bill
    @PostMapping("/add-bill")
    public String addBill(@RequestParam int patientID,
            @RequestParam double amount,
            @RequestParam Date billingDate,
            RedirectAttributes redirectAttributes) {
            // Validate the amount
        if (amount <= 0) {
            redirectAttributes.addFlashAttribute("errorBill", "Amount must be greater than 0.");
            return "redirect:/admin";
        }

        // Check if the patient exists
        Patient patient = hospital.findPatientById(patientID);
        if (patient == null) {
            redirectAttributes.addFlashAttribute("errorBill", "Patient ID not found.");
            return "redirect:/admin";
        }

        // Create a new Bill object and add it to the hospital's records
        admin.addBill(patientID, amount, billingDate);
        return "redirect:/admin";
    }

    // POST: Delete a bill
    @PostMapping("/delete-bill")
    public String deleteBill(@RequestParam int id) {
        // Find and remove the bill by ID
        admin.deleteBill(id);
        return "redirect:/admin";
    }

}
