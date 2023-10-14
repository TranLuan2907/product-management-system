/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import tools.Tools;

/**
 *
 * @author nklua
 */
public class LongShelfProduct extends Product implements Serializable {

    private Date manufacturingDate;
    private Date expiredDate;
    private String supplier;

    public LongShelfProduct(Date manufacturingDate, Date expiredDate, String code, String name, String supplier) {
        super(code, name);
        this.manufacturingDate = manufacturingDate;
        this.expiredDate = expiredDate;
        this.supplier = supplier;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public void showProfile() {
        String manufacturingDateString = Tools.convertDateToString(manufacturingDate, "dd-MM-yyyy");
        String expiredDateString = Tools.convertDateToString(expiredDate, "dd-MM-yyyy");
        System.out.printf("\n|%-8s|%-15s|%-8s|%-10s|%-20s|%-20s|%-20s|%-16d|", code, name, "N/A", "N/A", manufacturingDateString, expiredDateString, supplier, inventoryNumber);
        System.out.println();
        printHeaderBar();
    }

    @Override
    public String toString() {
        String manufacturingDateString = Tools.convertDateToString(manufacturingDate, "dd-MM-yyyy");
        String expiredDateString = Tools.convertDateToString(expiredDate, "dd-MM-yyyy");
        String productInfo = String.format("\n|%-8s|%-15s|%-8s|%-10s|%-20s|%-20s|%-20s|%-16d|", code, name, "N/A", "N/A", manufacturingDateString, expiredDateString, supplier, inventoryNumber);
        return productInfo + "\n" + generateHeaderBar();
    }

    public void printHeaderBar() {
        for (int i = 1; i <= 126 ; i++) {
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
