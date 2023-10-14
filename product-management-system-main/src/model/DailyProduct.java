/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author nklua
 */
public class DailyProduct extends Product implements Serializable {

    private String unit;
    private String size;

    public DailyProduct(String unit, String size, String code, String name) {
        super(code, name);
        this.unit = unit;
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void showProfile() {
        System.out.printf("\n|%-8s|%-15s|%-8s|%-10s|%-20s|%-20s|%-20s|%-16d|", code, name, unit, size, "N/A", "N/A", "N/A", inventoryNumber);
        System.out.println();
        printHeaderBar();
    }

    @Override
    public String toString() {
        String productInfo = String.format("\n|%-8s|%-15s|%-8s|%-10s|%-20s|%-20s|%-20s|%-16d|", code, name, unit, size, "N/A", "N/A", "N/A", inventoryNumber);
        return productInfo + "\n" + generateHeaderBar();
    }

    public void printHeaderBar() {
        for (int i = 1; i <= 126; i++) {
            System.out.print("-");
        }
    }

    public String generateHeaderBar() {
        StringBuilder headerBar = new StringBuilder("-");
        for (int i = 1; i <= 126; i++) {
            headerBar.append("-");
        }
        return headerBar.toString();
    }
}

