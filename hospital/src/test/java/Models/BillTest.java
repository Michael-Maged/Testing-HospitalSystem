package Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.hospital.Bill;
import java.util.Date;

public class BillTest {

    @Test
    public void testBillConstructorAndGetters() {
        // Create a new Bill instance with the correct constructor
        Date billingDate = new Date();
        Bill bill = new Bill(1, 1001, 500.0, billingDate);

        // Verify the values are correctly set
        assertEquals(1, bill.getPatientID()); // Test for patientID
        assertEquals(1001, bill.getBillId()); // Test for billId
        assertEquals(500.0, bill.getAmount()); // Test for amount
        assertEquals(billingDate, bill.getBillingDate()); // Test for billingDate
    }

    @Test
    public void testBillSetters() {
        // Create a new Bill instance with initial values
        Bill bill = new Bill(0, 0, 0.0, new Date());

        // Set the values using setters
        Date billingDate = new Date();
        bill.setBillId(2);
        bill.setAmount(350.0);
        bill.setBillingDate(billingDate);
        bill.setPatientID(10); // Set patientID

        // Verify the values are correctly set
        assertEquals(10, bill.getPatientID()); // Test the modified setter for patientID
        assertEquals(2, bill.getBillId()); // Verify billId
        assertEquals(350.0, bill.getAmount()); // Verify amount
        assertEquals(billingDate, bill.getBillingDate()); // Verify billingDate
    }

    @Test
    public void testToString() {
        // Create a new Bill instance with correct parameters
        Date billingDate = new Date();
        Bill bill = new Bill(1, 1001, 500.0, billingDate);

        // Verify the toString() method works as expected
        String expectedOutput = "Bill ID: 1001, Amount: $500.0Billing Date: " + billingDate;
        assertEquals(expectedOutput, bill.toString());
    }
}
