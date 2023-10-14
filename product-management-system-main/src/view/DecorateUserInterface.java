/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.ArrayList;
import tools.Tools;

/**
 * Contains some of methods used to decorating the menu and the user interface.
 *
 * @author nklua
 */
public class DecorateUserInterface extends ArrayList<String> {

    public void addItem(String menuItem) {
        add(menuItem);
    }

    /**
     * Print the menu with some options for users to choose and return the input from users.
     * @return a number which is an option for the menu 
     */
    public int getUserChoiceMenu() {
        System.out.println();
        printFullMenu();
        System.out.println();
        return Tools.getAnInteger("CHOOSE YOUR OPTION [1..." + (size() - 1) + "]: ", "INVALID INPUT! YOU MUST ENTER A VALID NUMBER FROM [1..." + (size() - 1) + "]!!!", 1, (size() - 1));
    }

    /**
     * Add menu options (both main menu and sub menu) into the list, which later
     * used to print it.
     *
     * @param menuOptions
     */
    public void addOptionsList(String menuOptions) {
        add(menuOptions);
    }

    /**
     * Print the header bar of the menu.
     *
     * @param heading
     */
    public static void printHeadingBar(String heading) {
        for (int i = 0; i < 25; i++) {
            System.out.print("-");
        }
        System.out.print(heading);

        for (int i = 0; i < 25; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Print a long bar which later used to print the footer bar of the menu.
     */
    public static void printABar() {
        for (int i = 0; i < 75; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    

    public void printFullMenu() {
        printHeadingBar(get(0));
        printABar();
        for (int i = 1; i < size(); i++) {
            System.out.println("| " + i + " |" + get(i));
        }
        printABar();
    }
}
