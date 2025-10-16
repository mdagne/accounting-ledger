package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileManager {
    private static final String FILE_PATH = "transactions.csv";

    public static List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true; // skip header if it exists

            while ((line = reader.readLine()) != null) {
                // Skip header row
                if (firstLine && line.startsWith("date")) {
                    firstLine = false;
                    continue;
                }
                firstLine = false;

                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String date = parts[0];
                    String time = parts[1];
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No existing transactions file found. A new one will be created when saving.");
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        return transactions;
    }

    // Add transaction
    public static void addTransaction(Transaction transaction) {
        File file = new File(FILE_PATH);
        boolean writeHeader = !file.exists(); // write header if file is new

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Write header if file is new
            if (writeHeader) {
                writer.write("date|time|description|vendor|amount");
                writer.newLine();
            }

            // Write the transaction
            writer.write(transaction.getDate() + "|" + transaction.getTime() + "|" +
                    transaction.getDescription() + "|" + transaction.getVendor() + "|" +
                    transaction.getAmount());
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
}
