package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    // Print all transactions for the current month
    public static void monthToDate(List<Transaction> transactions) {
        LocalDate now = LocalDate.now();
        System.out.println("\n--- Month To Date ---");
        for (Transaction t : transactions) {
            LocalDate date = parseDate(t.getDate());
            if (date != null && date.getMonthValue() == now.getMonthValue() && date.getYear() == now.getYear()) {
                System.out.println(t);
            }
        }
    }

    // Print all transactions for the previous month
    public static void previousMonth(List<Transaction> transactions) {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        System.out.println("\n--- Previous Month ---");
        for (Transaction t : transactions) {
            LocalDate date = parseDate(t.getDate());
            if (date != null && date.getMonthValue() == lastMonth.getMonthValue() && date.getYear() == lastMonth.getYear()) {
                System.out.println(t);
            }
        }
    }

    // Print all transactions for the current year
    public static void yearToDate(List<Transaction> transactions) {
        int year = LocalDate.now().getYear();
        System.out.println("\n--- Year To Date ---");
        for (Transaction t : transactions) {
            LocalDate date = parseDate(t.getDate());
            if (date != null && date.getYear() == year) {
                System.out.println(t);
            }
        }
    }

    // Print all transactions for the previous year
    public static void previousYear(List<Transaction> transactions) {
        int year = LocalDate.now().getYear() - 1;
        System.out.println("\n--- Previous Year ---");
        for (Transaction t : transactions) {
            LocalDate date = parseDate(t.getDate());
            if (date != null && date.getYear() == year) {
                System.out.println(t);
            }
        }
    }

    // Search transactions by vendor name
    public static void searchByVendor(List<Transaction> transactions, String vendorName) {
        System.out.println("\n--- Transactions for Vendor: " + vendorName + " ---");
        boolean found = false;
        for (Transaction t : transactions) {
            if (t.getVendor().toLowerCase().contains(vendorName.toLowerCase())) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for that vendor.");
        }
    }

    // Helper to safely parse date
    private static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return null;
        }
    }
}

