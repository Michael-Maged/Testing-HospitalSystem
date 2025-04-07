package Models;
import org.junit.jupiter.api.Test;

import com.hospital.Bill;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class BillTest {
    @Test
    public void testBillConstructorAndGetters() {
        // Create a new Bill instance
        Date billingDate = new Date();
        Bill bill = new Bill(1L, 500.0, billingDate);

        // Verify the values are correctly set
        assertEquals(1L, bill.getBillId());
        assertEquals(500.0, bill.getAmount());
        assertEquals(billingDate, bill.getBillingDate());
    }

    @Test
    public void testBillSetters() {
        // Create a new Bill instance
        Bill bill = new Bill(0L, 0.0, new Date());

        // Set the values using setters
        Date billingDate = new Date();
        bill.setBillId(2L);
        bill.setAmount(350.0);
        bill.setBillingDate(billingDate);

        // Verify the values are correctly set
        assertEquals(2L, bill.getBillId());
        assertEquals(350.0, bill.getAmount());
        assertEquals(billingDate, bill.getBillingDate());
    }

    @Test
    public void testToString() {
        // Create a new Bill instance
        Date billingDate = new Date();
        Bill bill = new Bill(1L, 500.0, billingDate);

        // Verify the toString() method works as expected
        String expectedOutput = "Bill ID: 1, Amount: $500.0Billing Date: " + billingDate;
        assertEquals(expectedOutput, bill.toString());
    }
}
