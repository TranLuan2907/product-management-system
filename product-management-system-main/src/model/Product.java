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
public abstract class Product implements Serializable {

    protected String code;
    protected String name;
    protected int inventoryNumber = 0;

    public Product(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Product(String code, String name, int inventoryNumber) {
        this.code = code;
        this.name = name;
        this.inventoryNumber = inventoryNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }
    
    public void updateInventoryNumber(int newQuantity) {
        this.inventoryNumber = newQuantity;
    }

    public int getCurrentInventoryNumber() {
        return  this.inventoryNumber;
    }
    
    public abstract void showProfile();
}
