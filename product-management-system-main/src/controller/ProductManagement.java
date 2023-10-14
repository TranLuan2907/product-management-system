/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.DailyProduct;
import model.LongShelfProduct;
import model.Order;
import model.Product;
import tools.Tools;
import view.DecorateUserInterface;

/**
 * This class contains methods responsible for managing product information,
 * including adding, removing, updating, retrieving data and so on. It provides
 * a central place for product-related operations.
 *
 * @author nklua
 */
public class ProductManagement {

    private static int orderNumberCounter = 1;
    private static final List<Product> productList = new ArrayList<>();
    private static final List<Order> orderList = new ArrayList<>();
    private final DecorateUserInterface productChooseTypeMenu;
    private static final String HEADER = "|CODE    |NAME           |UNIT    |SIZE      |MANUFACTURING DATE  |EXPIRED DATE        |SUPPLIER            |INVENTORY NUMBER|";
    public HashMap<String, Integer> productInventory = new HashMap<>();
    private boolean changed = false;

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public ProductManagement(DecorateUserInterface productChooseTypeMenu) {
        this.productChooseTypeMenu = productChooseTypeMenu;
    }

    public void updateProductInformation() {
        String code;
        Product product;

        if (productList.isEmpty()) {
            System.out.println("NOTHING TO UPDATE!");
            return;
        }
        printHeaderBar();
        System.out.println();
        showAllProduct();
        code = Tools.getProductCode("INPUT A PRODUCT CODE YOU WANT TO UPDATE: ", "A PRODUCT CODE MUST BE IN FORMAT (SEXXXXXX)!", "^SE\\d{6}$");
        product = findProductByCode(code);

        if (product == null) {
            System.out.println("NOT FOUND!");
            return;
        } else {
            System.out.println("FOUND! HERE IS THE PRODUCT!");
            printHeaderBar();
            System.out.println();
            System.out.print(HEADER);
            System.out.println();
            printHeaderBar();
            product.showProfile();
        }
        System.out.println();

        if (product instanceof DailyProduct) {
            updateDailyProduct(product);
        } else {
            updateLongShelfProduct(product);
        }
    }

    public void addAProduct() {
        int userChoice = productChooseTypeMenu.getUserChoiceMenu();
        String code, name;
        System.out.println("ADDING THE PRODUCT #" + (productList.size() + 1));
        do {
            code = Tools.getProductCode("INPUT A PRODUCT CODE IN FORMAT SEXXXXXX (X STANDS FOR DIGIT):  ", "A PRODUCT CODE MUST BE IN FORMAT SEXXXXXX! AND CANNOT BE LEAVE EMPTY!", "^SE\\d{6}$");
            findProductByCode(code);
            if (findProductByCode(code) != null) {
                System.out.println("DUPLICATED ID! PLEASE TRY AGAIN!");
            }
        } while (findProductByCode(code) != null);
        name = Tools.getAString("INPUT A PRODUCT NAME: ", "A PRODUCT NAME IS REQUIRED!");
        if (userChoice == 1) {
            addDailyProduct(code, name);
        } else {
            addLongShelfProduct(code, name);
        }
    }

    public Product findProductByCode(String code) {
        for (Product product : productList) {
            if (product.getCode().equalsIgnoreCase(code)) {
                return product;
            }
        }
        return null;
    }

    public void deleteProduct() {
        String code;
        boolean answer;
        Product product;
        if (productList.isEmpty()) {
            System.out.println("NOTHING TO DELETE!");
            return;
        }

        showAllProduct();
        code = Tools.getProductCode("INPUT A PRODUCT CODE TO DELETE IN FORMAT SEXXXXXX (X STANDS FOR DIGIT): ", "A PRODUCT CODE MUST BE VALID IN FORMAT SEXXXXXX (X STANDS FOR DIGIT) AND CANNOT LEAVE EMPTY!", "^SE\\d{6}$");
        product = findProductByCode(code);

        if (product == null) {
            System.out.println("CANNOT DELETE BECAUSE THE PRODUCT DOES NOT EXIST!");
            return;
        }

        if (isProductInOrderList(code)) {
            System.out.println("CANNOT DELETE THIS PRODUCT BECAUSE IT IS IN IMPORT/EXPORT PROCESS!");
            return;
        }

        System.out.println("ARE YOU SURE YOU WANT TO DELETE THIS PRODUCT?");
        System.out.println("THIS IS THE PRODUCT BEFORE REMOVING");
        printHeaderBar();
        System.out.println();
        System.out.print(HEADER);
        System.out.println();
        printHeaderBar();
        product.showProfile();
        System.out.println();

        answer = Tools.confirmYesNo("ENTER YOUR CHOICE Y/N: ", "AN ANSWER CHOICE IS REQUIRED!");

        if (answer) {
            productList.remove(product);
            System.out.println("PRODUCT HAS BEEN DELETED SUCCESSFULLY!");
            System.out.println("THIS IS THE PRODUCT LIST AFTER DELETED!");
            showAllProduct();
            changed = true;
        } else {
            System.out.println("DELETE CANCELLED!");
        }

    }

    public void showAllProduct() {
        if (productList.isEmpty()) {
            System.out.println("NOTHING TO SHOW!");
            return;
        } else {
            printHeaderBar();
            System.out.println();
            System.out.print(HEADER);
            System.out.println();
            printHeaderBar();
            for (Product product : productList) {
                product.showProfile();
            }
            System.out.println();
        }
    }

    public void addDailyProduct(String code, String name) {
        String unit, size;
        unit = Tools.getAString("INPUT A PRODUCT UNIT: ", "A PRODUCT UNIT IS REQUIRED!");
        size = Tools.getAString("INPUT A UNIT SIZE: ", "A PRODUCT SIZE IS REQUIRED!");
        productList.add(new DailyProduct(unit, size, code, name));
        showAllProduct();
        System.out.println("ADDING THE PRODUCT #" + productList.size() + " SUCCESSFULLY!");
        checkIfContinue();
        changed = true;
    }

    public void checkIfContinue() {
        boolean isContinue = Tools.confirmYesNo("DO YOU WANT TO ADD MORE PRODUCTS? (Y/N)? ", "AN INPUT MUST BE EITHER Y OR N!");
        if (isContinue) {
            addAProduct();
        }
    }

    public void showAllProduct(String message) {
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    public void addLongShelfProduct(String code, String name) {
        String supplier;
        Date manufacturingDate, expiredDate;
        manufacturingDate = Tools.getDate("INPUT MANUFACTURING DATE IN FORMAT (dd-MM-yyyy): ", "dd-MM-yyyy");
        do {
            expiredDate = Tools.getDate("INPUT EXPIRED DATE IN FORMAT (dd-MM-yyyy): ", "dd-MM-yyyy");
            if (!checkValidDate(manufacturingDate, expiredDate)) {
                System.out.println("INVALID DATE! EXPIRED DATE MUST BE GREATER THAN MANUFACTURING DATE!");
            } else {
                break;
            }
        } while (true);
        supplier = Tools.getAString("INPUT A PRODUCT SUPPLIER: ", "A PRODUCT SUPPLIER IS REQUIRED!");
        productList.add(new LongShelfProduct(manufacturingDate, expiredDate, code, name, supplier));
        showAllProduct();
        System.out.println("ADDING THE PRODUCT # " + productList.size() + " SUCCESSFULLY!");
        checkIfContinue();
        changed = true;
    }

    public void saveProductsToFile(String fileName) {
        if (productList.isEmpty()) {
            System.out.println("NOTHING TO SAVE IN PRODUCTS!");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(fileName); ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Product product : productList) {
                oos.writeObject(product);
            }

            oos.writeObject(null);
            System.out.println("PRODUCTS SAVE SUCCESSFULLY!");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveOrdersToFile(String fileName) {
        if (orderList.isEmpty()) {
            System.out.println("NOTHING TO SAVE IN ORDERS!");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(fileName); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(orderNumberCounter);

            for (Order order : orderList) {
                oos.writeObject(order);
            }


            oos.writeObject(null);
            System.out.println("ORDERS SAVE SUCCESSFULLY!");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadProductsFromFile(File f) {
        if (!productList.isEmpty()) {
            productList.clear();
        }

        try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {

            Product p;
            while ((p = (Product) ois.readObject()) != null) {
                productList.add(p);
            }

            System.out.println("LOAD PRODUCTS SUCCESSFULLY!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void loadOrdersFromFile(File f) {
        if (!orderList.isEmpty()) {
            orderList.clear();
        }

        try (FileInputStream fis = new FileInputStream(f); ObjectInputStream ois = new ObjectInputStream(fis)) {
            orderNumberCounter = ois.readInt();
            Order o;
            while ((o = (Order) ois.readObject()) != null) {
                orderList.add(o);
            }

            System.out.println("LOAD ORDERS SUCCESSFULLY!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public boolean checkValidDate(Date manufacturingDate, Date expiredDate) {
        return expiredDate.after(manufacturingDate);
    }

    public void updateDailyProduct(Product product) {
        String unit, size, name;
        name = Tools.getUpdatedString("INPUT A NEW PRODUCT NAME OR YOU CAN LEAVE IT EMPTY: ");

        if (!name.isEmpty()) {
            ((DailyProduct) product).setName(name);
        }

        unit = Tools.getUpdatedString("INPUT A NEW PRODUCT UNIT OR YOU CAN LEAVE IT EMPTY:  ");

        if (!unit.isEmpty()) {
            ((DailyProduct) product).setUnit(unit);
        }

        size = Tools.getUpdatedString("INPUT A NEW PRODUCT SIZE OR YOU CAN LEAVE IT EMPTY: ");

        if (!size.isEmpty()) {
            ((DailyProduct) product).setSize(size);
        }


        System.out.println("UPDATED SUCCESSFULLY! HERE IS THE PRODUCT AFTER UPDATED");
        printHeaderBar();
        System.out.println();
        System.out.print(HEADER);
        System.out.println();
        printHeaderBar();
        product.showProfile();
        System.out.println();
        changed = true;
    }

    public void updateLongShelfProduct(Product product) {
        Date manufacturingDate, expiredDate;
        String supplier, name;
        boolean isValid = false;

        name = Tools.getUpdatedString("INPUT A NEW PRODUCT NAME OR YOU CAN LEAVE IT EMPTY: ");
        if (!name.isEmpty()) {
            ((LongShelfProduct) product).setName(name);
        }

        manufacturingDate = Tools.getUpdatedDate("INPUT A NEW PRODUCT MANUFACTURING DATE OR YOU CAN LEAVE IT EMPTY:  ", "dd-MM-yyyy");
        if (manufacturingDate != null) {
            ((LongShelfProduct) product).setManufacturingDate(manufacturingDate);
        }

        while (true) {
            expiredDate = Tools.getUpdatedDate("INPUT A NEW PRODUCT EXPIRED DATE OR YOU CAN LEAVE IT EMPTY: ", "dd-MM-yyyy");
            if (expiredDate != null) {
                ((LongShelfProduct) product).setExpiredDate(expiredDate);
            }
            if (checkValidDate(((LongShelfProduct) product).getManufacturingDate(), ((LongShelfProduct) product).getExpiredDate())) {
                break;
            } else {
                System.out.println("EXPIRED DATE MUST BE GREATER THAN MANUFACTURING DATE!");
            }
        }


        supplier = Tools.getUpdatedString("INPUT A NEW PRODUCT SUPPLIER OR YOU CAN LEAVE IT EMPTY: ");
        if (!supplier.isEmpty()) {
            ((LongShelfProduct) product).setSupplier(supplier);
        }


        System.out.println("UPDATE SUCCESSFULLY! HERE IS THE PRODUCT AFTER UPDATED!");
        printHeaderBar();
        System.out.println();
        System.out.print(HEADER);
        System.out.println();
        printHeaderBar();
        product.showProfile();
        System.out.println();
        changed = true;
    }

    public void createImportReceipt() {
        String code;
        Product product;
        boolean addMore;
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateStr = dateFormat.format(currentDate);

        System.out.println("CREATING ORDER #: " + (orderList.size() + 1));
        Map<String, Integer> productsToAdd = new HashMap<>();
        do {
            showAllProduct();
            code = Tools.getProductCode("INPUT A PRODUCT CODE THAT YOU WANT TO IMPORT IN FORMAT SEXXXXXX (X STANDS FOR DIGIT): ", "A VALID PRODUCT CODE IS REQUIRED SEXXXXXX (X STANDS FOR DIGIT) AND CANNOT BE LEAVE EMPTY!", "^SE\\d{6}$");
            product = findProductByCode(code);
            if (product == null) {
                System.out.println("NOT FOUND!");
                break;
            } else {
                int quantity = Tools.getAnInteger("INPUT THE QUANTITY TO IMPORT: ", "A QUANTITY IS REQUIRED!");
                if (productsToAdd.containsKey(code)) {
                    int currentQuantity = productsToAdd.get(code);
                    productsToAdd.put(code, currentQuantity + quantity);
                } else {
                    productsToAdd.put(code, quantity);
                }
                updateInventory(code, quantity, "import");
                System.out.println("PRODUCT ADDED TO IMPORT RECEIPT!");
            }

            addMore = Tools.confirmYesNo("DO YOU WANT TO ADD MORE PRODUCT IN THE IMPORT RECEIPT (Y/N): ", "AN ANSWER IS REQUIRED!");
        } while (addMore);

        if (product == null) {
            System.out.println("A PRODUCT YOU WANT TO CREATE IMPORT RECEIPT IS NOT IN THE LIST! PLEASE TRY AGAIN!");
        } else {
            printImportOrderReceipt(orderNumberCounter, currentDateStr, productsToAdd);
            orderList.add(new Order(Integer.toString(orderNumberCounter), currentDateStr, productInventory, "IMPORT"));
            orderNumberCounter++;
            System.out.println("IMPORT RECEIPT CREATED SUCCESSFULLY!");
            changed = true;
        }
    }

    public void createExportReceipt() {
        String code;
        Product product;
        boolean addMore;
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateStr = dateFormat.format(currentDate);
        Map<String, Integer> productsToAdd = new HashMap<>();
        System.out.println("CREATING ORDER #: " + (orderList.size() + 1));
        do {
            showAllProduct();
            code = Tools.getProductCode("INPUT A PRODUCT CODE THAT YOU WANT TO EXPORT IN FORMAT SEXXXXXX: ", "A VALID PRODUCT CODE IS REQUIRED SEXXXXXX (X STANDS FOR DIGIT) AND CANNOT BE LEAVE EMPTY!", "^SE\\d{6}$");
            product = findProductByCode(code);
            if (product == null) {
                System.out.println("THE PRODUCT YOU WANT TO EXPORT IS NOT IN THE LIST! PLEASE TRY AGAIN!");
                break;
            } else {
                int quantity = Tools.getAnInteger("INPUT THE QUANTITY TO EXPORT: ", "A QUANTITY IS REQUIRED!");
                if (quantity > product.getInventoryNumber()) {
                    System.out.println("EXPORT QUANTITY CANNOT BE GREATER THAN INVENTORY!");
                    continue;
                }
                if (productsToAdd.containsKey(code)) {
                    int currentQuantity = productsToAdd.get(code);
                    productsToAdd.put(code, currentQuantity + quantity);
                } else {
                    productsToAdd.put(code, quantity);
                }
                updateInventory(code, quantity, "export");
                System.out.println("PRODUCT ADDED TO EXPORT RECEIPT!");
            }

            addMore = Tools.confirmYesNo("DO YOU WANT TO ADD MORE PRODUCT IN THE EXPORT RECEIPT (Y/N): ", "AN ANSWER IS REQUIRED!");
            if (!addMore) {
                break;
            }
        } while (true);

        if (product == null) {
            System.out.println("NOT FOUND!");
        } else {
            printExportOrderReceipt(orderNumberCounter, currentDateStr, productsToAdd);
            orderList.add(new Order(Integer.toString(orderNumberCounter), currentDateStr, productInventory, "EXPORT"));
            orderNumberCounter++;
            System.out.println("EXPORT RECEIPT CREATED SUCCESSFULLY!");
            changed = true;
        }
    }

    public void printImportOrderReceipt(int orderNumber, String date, Map<String, Integer> productsToAdd) {
        System.out.println("----------------------------------------------");
        System.out.println("|              CONVENIENCE STORE             |");
        System.out.println("|                IMPORT ORDER                |");
        System.out.println("| Number: " + String.format("%07d", orderNumber) + "           Date: " + date + " |");
        System.out.println("----------------------------------------------");


        for (Map.Entry<String, Integer> entry : productsToAdd.entrySet()) {
            String code = entry.getKey();
            int quantity = entry.getValue();
            Product product = findProductByCode(code);

            if (product != null && quantity > 0) {
                System.out.printf("| %-20s                  %5d|\n", product.getName(), quantity);
            }
        }

        System.out.println("----------------------------------------------");
    }

    public void printExportOrderReceipt(int orderNumber, String date, Map<String, Integer> productsToAdd) {
        System.out.println("----------------------------------------------");
        System.out.println("|              CONVENIENCE STORE             |");
        System.out.println("|                EXPORT ORDER                |");
        System.out.println("| Number: " + String.format("%07d", orderNumber) + "           Date: " + date + " |");
        System.out.println("----------------------------------------------");


        for (Map.Entry<String, Integer> entry : productsToAdd.entrySet()) {
            String code = entry.getKey();
            int quantity = entry.getValue();
            Product product = findProductByCode(code);

            if (product != null && quantity > 0) {
                System.out.printf("| %-20s                  %5d|\n", product.getName(), quantity);
            }
        }
        System.out.println("----------------------------------------------");
    }

    public void updateInventory(String code, int quantity, String orderType) {
        Product product;
        product = findProductByCode(code);
        if (product == null) {
            System.out.println("NOT FOUND A PRODUCT TO UPDATE!");
            return;
        }

        int currentQuantity = product.getInventoryNumber();
        int newQuantity;

        if (orderType.equalsIgnoreCase("import")) {
            newQuantity = currentQuantity + quantity;
        } else {
            newQuantity = currentQuantity - quantity;
        }

        product.updateInventoryNumber(newQuantity);
        productInventory.put(code, newQuantity);
    }

    public void expiredProduct() {
        Date currentDate = new Date();
        System.out.println("EXPIRED PRODUCT: ");
        printHeaderBar();
        System.out.println();
        System.out.print(HEADER);
        System.out.println();
        printHeaderBar();
        for (Product product : productList) {
            if (product instanceof LongShelfProduct longShelfProduct) {
                Date expiredDate = longShelfProduct.getExpiredDate();
                if (expiredDate != null && expiredDate.compareTo(currentDate) < 0) {
                    System.out.print(product);
                }
            }
        }
        System.out.println();
    }

    public void sellingProduct() {
        Date currentDate = new Date();
        System.out.println("SELLING PRODUCT: ");
        printHeaderBar();
        System.out.println();
        System.out.print(HEADER);
        System.out.println();
        printHeaderBar();
        for (Product product : productList) {
            if (product instanceof LongShelfProduct longShelfProduct) {
                Date expiredDate = longShelfProduct.getExpiredDate();
                if (expiredDate != null && expiredDate.compareTo(currentDate) > 0 && product.getInventoryNumber() >= 3) {
                    System.out.print(product);
                }
            } else if (product instanceof DailyProduct && product.getInventoryNumber() >= 3) {
                System.out.print(product);
            }

        }
        System.out.println();
    }

    public void outOfStockProduct() {
        List<Product> outOfStockProduct;
        outOfStockProduct = productList.stream().filter(product -> product.getInventoryNumber() < 3).collect(Collectors.toList());
        outOfStockProduct.sort(Comparator.comparingInt(Product::getInventoryNumber));
        System.out.println("OUT OF STOCK PRODUCTS (SORTED IN ASCENDING ORDER QUANTITY)");
        printHeaderBar();
        System.out.println();
        System.out.print(HEADER);
        System.out.println();
        printHeaderBar();
        for (Product product : outOfStockProduct) {
            System.out.print(product);
        }
        System.out.println();
    }

    public void importExportProduct() {
        String code, orderNumber, orderType, orderDate;
        Product p;
        showAllProduct();
        code = Tools.getProductCode("INPUT A PRODUCT CODE TO DISPLAY IMPORT/EXPORT RECEIPT IN FORMAT SEXXXXXX (X STANDS FOR DIGIT): ", "A VALID PRODUCT CODE IN FORMAT SEXXXXXX (X STANDS FOR DIGIT) IS REQUIRED! AND CANNOT LEAVE EMPTY", "^SE\\d{6}$");
        p = findProductByCode(code);
        if (p == null) {
            System.out.println("NOT FOUND!");
            return;
        }
        System.out.println("ORDER PRODUCT");
        printHeaderOrder();
        System.out.println();
        System.out.println("|ORDER NUMBER     |ORDER TYPE        |ORDER DATE    |");
        printHeaderOrder();
        for (Order order : orderList) {
            if (order.getListItem().containsKey(code)) {
                orderNumber = String.format("%07d", Integer.parseInt(order.getOrderCode()));
                orderType = order.getOrderType();
                orderDate = order.getOrderDate();
                System.out.printf("\n|%-17s|%-18s|%-14s|", orderNumber, orderType, orderDate);
                System.out.println();
                printHeaderOrder();
            }
        }

        System.out.println();
        printHeaderBar();
        System.out.println();
        System.out.print(HEADER);
        System.out.println();
        printHeaderBar();
        System.out.println(p);
    }

    public boolean isProductInOrderList(String code) {
        for (Order order : orderList) {
            if (order.getListItem().containsKey(code)) {
                return true;
            }
        }
        return false;
    }

    public void printHeaderBar() {
        for (int i = 1; i <= 126; i++) {
            System.out.print("-");
        }
    }

    public void printHeaderOrder() {
        for (int i = 1; i <= 53; i++) {
            System.out.print("-");
        }
    }
}
