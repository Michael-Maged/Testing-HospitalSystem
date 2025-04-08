Base Classes: -

- Hospital(URL, USER, PASSWORD, patients, appointments, records, inventory, doctors)
    fetchInvenotoryItems: get items from DB and save to array "inventory"
    fetchMedicalRecords: get records from DB and save to array "records"
    fetchDoctors: get docs from DB and save to array "doctors"
    fetchPatients: get patients from DB and save to array "patients"
    fetchAppointments: get appointments from DB and save to array "appointments"

    registerPatient: registers a new patient into the DB
    loginPatient: gets logged in patient data and creates a session instance
    
    scheduleAppointment: adds a new appointment in the DB
    addMedicalRecord: adds a new record in the DB

- Patient(patientID, name, age, gender, address, phoneNumber, appointments, records, bills)
    getAppointments: get the current patient's appointments from the DB
    getRecords:  get the current patient's medical records from the DB

- Admin(URL, USER, PASSWORD)
    addDoctor: adds a doctor to the DB
    deleteDoctor: removes doctor from DB
    addInventoryItem: adds item to DB
    deleteInventoryItem: removes item from DB
    addMedicalRecord: adds record to DB
    deleteMedicalRecord: removes record from DB

- Doctor(doctorID, name, age, gender, specialty)

- InventoryItem(itemId, name, quantity)

- Appointment(appID, patientID, docID, type, date, time)

- MedicalRecord(recordID, patientID, diagnosis, treatement, date)

- Bill(billId, amount, billingDate)

- Session(instance, currentPatient)