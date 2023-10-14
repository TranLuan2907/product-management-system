/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ProductManagement;
import java.io.File;
import tools.Tools;


/**
 *
 * @author nklua
 */
public class StoreManagement {
    public static void main(String[] args) {
        int userChoice;
        boolean saveBeforeExit;
        boolean exitProgram;

        DecorateUserInterface mainMenu = new DecorateUserInterface();
        DecorateUserInterface productMenu = new DecorateUserInterface();
        DecorateUserInterface productChooseTypeMenu = new DecorateUserInterface();
        DecorateUserInterface wareHouseMenu = new DecorateUserInterface();
        DecorateUserInterface reportMenu = new DecorateUserInterface();

        printMainMenu(mainMenu);
        printProductMenu(productMenu);
        printProductChooseTypeMenu(productChooseTypeMenu);
        printWareHouseMenu(wareHouseMenu);
        printReportMenu(reportMenu);
        
        ProductManagement productManagement = new ProductManagement(productChooseTypeMenu);

        processingFile(productManagement);
        
        do {
            userChoice = mainMenu.getUserChoiceMenu();
            switch (userChoice) {
                case 1 -> manageProduct(userChoice, productMenu, productManagement);
                case 2 -> manageWareHouse(userChoice, wareHouseMenu, productManagement);
                case 3 -> report(userChoice, reportMenu, productManagement);
                case 4 -> storeDataToFile(productManagement, "products.dat", "warehouse.dat");
                case 5 -> {
                    if (productManagement.isChanged()) {
                        saveBeforeExit = Tools.confirmYesNo("SOME INFORMATION ARE CHANGED! DO YOU WANT TO SAVE IT? (Y/N): ", "AN VALID ANSWER IS REQUIRED!");
                        if (saveBeforeExit) {
                            storeDataToFile(productManagement, "products.dat", "warehouse.dat");
                        }
                        exitProgram = Tools.confirmYesNo("DO YOU REALLY WANT TO EXIT! (Y/N): ", "AN VALID ANSWER IS REQUIRED!");
                        if (exitProgram) {
                            System.out.println("SEE YOU NEXT TIME!");
                            break;
                        }
                    }
                }
            }
        } while (userChoice != mainMenu.size() - 1);
    }

    public static void printMainMenu(DecorateUserInterface mainMenu) {
        mainMenu.add("WELCOME TO THE STORE MANAGEMENT SYSTEM");
        mainMenu.add(" MANAGE PRODUCTS                                                      |");
        mainMenu.add(" MANAGE WAREHOUSE                                                     |");
        mainMenu.add(" REPORT                                                               |");
        mainMenu.add(" STORE DATA TO FILE                                                   |");
        mainMenu.add(" CLOSE THE APPLICATION                                                |");
    }

    public static void printProductMenu(DecorateUserInterface productMenu) {
        productMenu.add(" MANAGE PRODUCT");
        productMenu.add(" ADD A PRODUCT                                                        |");
        productMenu.add(" UPDATE PRODUCT INFORMATION                                           |");
        productMenu.add(" DELETE PRODUCT                                                       |");
        productMenu.add(" SHOW ALL PRODUCT                                                     |");
        productMenu.add(" BACK TO THE MAIN MENU                                                |");
    }

    public static void printProductChooseTypeMenu(DecorateUserInterface productChooseTypeMenu) {
        productChooseTypeMenu.add("WHICH PRODUCT DO YOU WANT TO CHOOSE?");
        productChooseTypeMenu.add(" DAILY PRODUCT                                                        |");
        productChooseTypeMenu.add(" LONG SHELF PRODUCT                                                   |");
    }

    public static void printReportMenu(DecorateUserInterface reportMenu) {
        reportMenu.add(" REPORT");
        reportMenu.add(" PRODUCTS THAT HAVE EXPIRED                                           |");
        reportMenu.add(" THE PRODUCTS THAT THE STORE IS SELLING                               |");
        reportMenu.add(" PRODUCTS THAT ARE RUNNING OUT OF STOCK (SORTED IN ASCENDING ORDER)   |");
        reportMenu.add(" IMPORT/EXPORT RECEIPT OF A PRODUCT                                   |");
        reportMenu.add(" BACK TO THE MAIN MENU                                                |");
    }

    public static void printWareHouseMenu(DecorateUserInterface wareHouseMenu) {
        wareHouseMenu.add(" MANAGE WAREHOUSE");
        wareHouseMenu.add(" CREATE AN IMPORT RECEIPT                                             |");
        wareHouseMenu.add(" CREATE AN EXPORT RECEIPT                                             |");
        wareHouseMenu.add(" BACK TO THE MAIN MENU                                                |");
    }

    

    public static void manageProduct(int userChoice, DecorateUserInterface productMenu, ProductManagement productManagement) {
       do {
            userChoice = productMenu.getUserChoiceMenu();
            switch (userChoice) {
                case 1 -> productManagement.addAProduct();
                case 2 -> productManagement.updateProductInformation();
                case 3 -> productManagement.deleteProduct();
                case 4 -> productManagement.showAllProduct();
                case 5 -> {
                    
                }
            }
        } while (userChoice != productMenu.size() - 1);
    }

    public static void manageWareHouse(int userChoice, DecorateUserInterface wareHouseMenu, ProductManagement productMangement) {
        do {
            userChoice = wareHouseMenu.getUserChoiceMenu();
            switch (userChoice) {
                case 1 -> productMangement.createImportReceipt();
                case 2 -> productMangement.createExportReceipt();
                case 3 -> {
                    
                }
            }
        } while (userChoice != wareHouseMenu.size() - 1);
    }

    public static void report(int userChoice, DecorateUserInterface reportMenu, ProductManagement productManagement) {
        do {
            userChoice = reportMenu.getUserChoiceMenu();
            switch (userChoice) {
                case 1 -> productManagement.expiredProduct();
                case 2 -> productManagement.sellingProduct();
                case 3 -> productManagement.outOfStockProduct();
                case 4 -> productManagement.importExportProduct();
            }
        } while (userChoice != reportMenu.size() - 1);
    }
    
    public static void processingFile(ProductManagement productManagement) {
        String PRODUCTS_FILE = "products.dat";
        String ORDERS_FILE = "warehouse.dat";
        File f = new File ("products.dat");
        if (f.exists()) {
            productManagement.loadProductsFromFile(f);
        }
        f = new File("warehouse.dat");
        if (f.exists()) {
            productManagement.loadOrdersFromFile(f);
        }
    }

    public static void storeDataToFile(ProductManagement productManagement, String PRODUCTS_FILE, String ORDERS_FILE) {
        productManagement.saveProductsToFile(PRODUCTS_FILE);
        productManagement.saveOrdersToFile(ORDERS_FILE);
        productManagement.setChanged(false);
    }
}
