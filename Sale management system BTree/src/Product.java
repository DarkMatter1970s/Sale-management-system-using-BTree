/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Product {

    private String ID;
    private String name;
    private String importDate;
    private String manufacturer;
    private Double unitPrice;
    private int yearOfPublish;
    private int totalAmount;

    public Product(String ID, String name, String importDate, String manufacturer, Double unitPrice, int yearOfPublish, int totalAmount) {
        this.ID = ID;
        this.name = name;
        this.importDate = importDate;
        this.manufacturer = manufacturer;
        this.unitPrice = unitPrice;
        this.yearOfPublish = yearOfPublish;
        this.totalAmount = totalAmount;
    }

    public Product(String ID, String name, Double unitPrice, int totalAmount) {
        this.ID = ID;
        this.name = name;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.manufacturer = "null";
        this.importDate = "null";
        this.yearOfPublish = 0;
    }

    public Product() {
        this.ID = null;
        this.importDate = "null";
        this.manufacturer = "null";
        this.name = "null";
        this.totalAmount = 0;
        this.unitPrice = 0.0;
        this.yearOfPublish = 0;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getImportDate() {
        return importDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public int getTotalAmount() {
        return this.totalAmount;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public void setTotalAmount(int amount) {
        this.totalAmount = amount;
    }

    @Override
    public String toString() {
        return "ProductID: " + ID + "\n\tName: " + name + "\n\tImportDate: " + importDate + "\n\tManufacturer: " + manufacturer + "\n\tUnitPrice: " + unitPrice + "\n\tyearOfPublish: " + yearOfPublish + "\n\tTotalAmount: " + totalAmount;
    }

    public String displayTofile() {
        return String.format("%-20s | %-30s | %-20s | %-30s | %-15.2f | %-6d | %-7d", ID, name, importDate, manufacturer, unitPrice, yearOfPublish, totalAmount) + "\n";
    }
}
