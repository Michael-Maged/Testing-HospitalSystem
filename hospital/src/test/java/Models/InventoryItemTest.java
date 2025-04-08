package Models;
import org.junit.jupiter.api.Test;

import com.hospital.InventoryItem;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryItemTest {
    @Test
    public void testConstructorAndGetters() {
        InventoryItem item = new InventoryItem(1, "Gloves", 100);

        assertEquals(1, item.getItemId());
        assertEquals("Gloves", item.getName());
        assertEquals(100, item.getQuantity());
    }

    @Test
    public void testSetters() {
        InventoryItem item = new InventoryItem(2, "Masks", 50);

        item.setItemId(3);
        item.setName("Sanitizer");
        item.setQuantity(200);

        assertEquals(3L, item.getItemId());
        assertEquals("Sanitizer", item.getName());
        assertEquals(200, item.getQuantity());
    }

    @Test
    public void testNegativeQuantityThrowsException() {
        InventoryItem item = new InventoryItem(4, "Syringes", 30);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            item.setQuantity(-10);
        });

        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    public void testToString() {
        InventoryItem item = new InventoryItem(5, "Bandages", 20);

        String expectedOutput = "Item: Bandages | Quantity: 20";
        assertEquals(expectedOutput, item.toString());
    }
}
