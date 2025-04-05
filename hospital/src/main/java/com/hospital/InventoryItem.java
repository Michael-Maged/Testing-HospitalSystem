package com.hospital;

public class InventoryItem {
    private Long itemId;
    private String name;
    private int quantity;

    public InventoryItem(Long itemId, String name, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item: " + name + " | Quantity: " + quantity;
    }

}
