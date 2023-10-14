/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * Provide methods which check the validation of the user's input, including
 * string, date, integer,...
 *
 * @author nklua
 */
public class Tools {

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Normalize a date string input, remove any empty spaces and replace all
     * the '/' or '-' and '.' with just a '-'.
     *
     * @param dateString
     * @return new string
     */
    public static String normalizeDateString(String dateString) {
        String result = dateString.replaceAll("[\\s]+", "");
        result = result.replaceAll("[./-]+", "-");
        return result;
    }

    /**
     * Parsing input string and return the formatted date.
     *
     * @param inputString
     * @param dateFormat
     * @return formatted date string.
     */
    public static Date parseDateFormatted(String inputString, String dateFormat, String errorMessage) {
        inputString = normalizeDateString(inputString);
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(inputString);
        } catch (ParseException e) {
            System.out.println(errorMessage);
        }
        return null;
    }

    /**
     * Convert a given date object to a string input.
     *
     * @param dateInput
     * @param dateFormat
     * @return a new string date after converted.
     */
    public static String convertDateToString(Date dateInput, String dateFormat) {
        if (dateInput == null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        // Return the result after formatted.
        return formatter.format(dateInput);
    }

    /**
     * @param message
     * @param errorMessage
     * @param lowerBound
     * @param UpperBound
     * @return
     */
    public static int getAnInteger(String message, String errorMessage, int lowerBound, int UpperBound) {
        int input, temp;
        if (lowerBound > UpperBound) {
            temp = lowerBound;
            lowerBound = UpperBound;
            UpperBound = temp;
        }

        while (true) {
            try {
                System.out.print(message);
                input = Integer.parseInt(SCANNER.nextLine());
                if (input < lowerBound || input > UpperBound) {
                    throw new Exception();
                }
                return input;
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * Validate product code and check if it is empty.
     *
     * @param inputMessage
     * @param errorMessage
     * @param regexCode
     * @return
     */
    public static String getProductCode(String inputMessage, String errorMessage, String regexCode) {
        String input;
        boolean match;
        while (true) {
            System.out.print(inputMessage);
            input = SCANNER.nextLine().trim().toUpperCase();
            if (!input.matches(regexCode) || input.isEmpty()) {
                System.out.println(errorMessage);
            } else {
                return input;
            }
        }
    }

    public static String getAString(String inputMessage, String errorMessage) {
        String input;
        while (true) {
            try {
                System.out.print(inputMessage);
                input = SCANNER.nextLine().trim().toUpperCase();
                if (input.isEmpty()) {
                    throw new Exception();
                } else {
                    return input;
                }
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    public static boolean confirmYesNo(String message, String errorMessage) {
        String input = "";
        boolean isValidChoice = false;

        do {
            try {
                System.out.print(message);
                input = SCANNER.nextLine().trim().toUpperCase();

                if (input.equals("Y") || input.equals("N")) {
                    isValidChoice = true;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        } while (!isValidChoice);

        return input.equals("Y");
    }


    /**
     * Reading a date using pre-defined format
     */
    public static Date getDate(String inputMessage, String dateFormat) {
        String input;
        Date d;
        do {
            System.out.print(inputMessage);
            input = SCANNER.nextLine().trim();
            d = parseDateFormatted(input, dateFormat, "INVALID DATE FORMATTED! PLEASE INPUT THE DATE IN FORMAT (dd-MM-yyyy)!");
        } while (d == null);
        return d;
    }

    public static int getAnInteger(String message, String errorMessage) {
        int input;
        while (true) {
            try {
                System.out.print(message);
                input = Integer.parseInt(SCANNER.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public static String getUpdatedString(String message) {
        String input;
        System.out.print(message);
        input = SCANNER.nextLine().trim().toUpperCase();
        return input;
    }

    public static Date getUpdatedDate(String inputMessage, String dateFormat) {
        String input;
        Date d = null;
        boolean validInput = false;

        do {
            System.out.print(inputMessage);
            input = SCANNER.nextLine().trim();

            if (input.isEmpty()) {
                validInput = true;
            } else {
                d = parseDateFormatted(input, dateFormat, "INVALID DATE FORMATTED! PLEASE INPUT THE DATE IN FORMAT (dd-MM-yyyy)!");
                validInput = (d != null);
            }
        } while (!validInput);
        return d;
    }

}
