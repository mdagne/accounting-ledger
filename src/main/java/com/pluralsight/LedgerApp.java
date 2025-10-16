package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class LedgerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==================================");
        System.out.println(" Welcome to the Accounting Ledger ");
        System.out.println("==================================");

        while (running) {
            System.out.println("\nHome Menu:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addTransaction(scanner, true);
                    break;
                case "P":
                    addTransaction(scanner, false);
                    break;
                case "L":
                    showLedgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Add deposit or payment
    private static void addTransaction(Scanner scanner, boolean isDeposit) {
        String date = LocalDate.now().toString(); // auto date
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")); // auto time

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        // Use safe input helper
        double amount = readAmount(scanner);

        if (!isDeposit) {
            amount = -Math.abs(amount); // ensure payment is negative
        }

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        TransactionFileManager.addTransaction(transaction);
        System.out.println("Transaction added successfully!");
    }
    private static double readAmount(Scanner scanner) {
        while (true) {
            System.out.print("Enter amount: ");
            String input = scanner.nextLine().trim();

            try {
                double amount = Double.parseDouble(input);
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a number (e.g., 50 or 50.75).");
            }
        }
    }

    // Ledger submenu
    private static void showLedgerMenu(Scanner scanner) {
        boolean inLedgerMenu = true;

        while (inLedgerMenu) {
            System.out.println("\n=== Ledger Menu ===");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine().trim().toUpperCase();
            List<Transaction> transactions = TransactionFileManager.readTransactions();

            switch (option) {
                case "A":
                    System.out.println("\n--- All Transactions ---");
                    for (Transaction t : transactions) {
                        System.out.println(t);
                    }
                    break;

                case "D":
                    System.out.println("\n--- Deposits ---");
                    for (Transaction t : transactions) {
                        if (t.getAmount() > 0) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "P":
                    System.out.println("\n--- Payments ---");
                    for (Transaction t : transactions) {
                        if (t.getAmount() < 0) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "R":
                    showReportsMenu(scanner);
                    break;

                case "H":
                    inLedgerMenu = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Reports menu
    private static void showReportsMenu(Scanner scanner) {
        boolean inReportsMenu = true;

        while (inReportsMenu) {
            System.out.println("\n=== Reports Menu ===");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            List<Transaction> transactions = TransactionFileManager.readTransactions();

            switch (choice) {
                case "1":
                    ReportGenerator.monthToDate(transactions);
                    break;
                case "2":
                    ReportGenerator.previousMonth(transactions);
                    break;
                case "3":
                    ReportGenerator.yearToDate(transactions);
                    break;
                case "4":
                    ReportGenerator.previousYear(transactions);
                    break;
                case "5":
                    System.out.print("Enter vendor name to search: ");
                    String vendor = scanner.nextLine().trim();
                    ReportGenerator.searchByVendor(transactions, vendor);
                    break;
                case "0":
                    inReportsMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
