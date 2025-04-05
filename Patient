import java.util.ArrayList;

public class Patient {
    // Attributes
    private String patientID;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String phoneNumber;
    private String medicalHistory;

    // New attributes for relationships
    private ArrayList<Appointment> appointments;
    private ArrayList<Bill> bills;

    // Constructor
    public Patient(String patientID, String name, int age, String gender, String address, String phoneNumber, String medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.medicalHistory = medicalHistory;
        this.appointments = new ArrayList<>();
        this.bills = new ArrayList<>();
    }

    // Getters
    public String getPatientID() { return patientID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getMedicalHistory() { return medicalHistory; }
    public ArrayList<Appointment> getAppointments() { return appointments; }
    public ArrayList<Bill> getBills() { return bills; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    // Functions
    public void updateMedicalHistory(String newRecord) {
        this.medicalHistory += "\n" + newRecord;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Patient ID: ").append(patientID).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Age: ").append(age).append("\n");
        sb.append("Gender: ").append(gender).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Phone Number: ").append(phoneNumber).append("\n");
        sb.append("Medical History: ").append(medicalHistory).append("\n");

        sb.append("\nAppointments:\n");
        for (Appointment a : appointments) {
            sb.append(a.toString()).append("\n");
        }

        sb.append("\nBills:\n");
        for (Bill b : bills) {
            sb.append(b.toString()).append("\n");
        }

        return sb.toString();
    }
}
