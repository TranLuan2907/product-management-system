/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author nklua
 */
public class Order implements Serializable{
    private HashMap<String, Integer> listItem = new HashMap<>();
    private String orderType;
    private String orderDate;
    private String orderCode;
    private Date time;

    public Order(String orderType, String orderDate, String orderCode, Date time) {
        this.orderType = orderType;
        this.orderDate = orderDate;
        this.orderCode = orderCode;
        this.time = time;
    }
    
    public Order(String orderCode, String orderDate, HashMap<String, Integer> listItem, String orderType) {
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.listItem = listItem;
        this.time = new Date();
        this.orderType = orderType;
    }

    public HashMap<String, Integer> getListItem() {
        return listItem;
    }

    public void setListItem(HashMap<String, Integer> listItem) {
        this.listItem = listItem;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
