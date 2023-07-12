package com.techlabs.bankapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;


public class DBConnectiontest {

	public static void main(String[] args) throws SQLException {
		try {
            DBConnection dbConnection = DBConnection.getDBConnection();
            dbConnection.createbankDb();
            dbConnection.createAdminTable();
            dbConnection.createCustomerTable();
            dbConnection.createAccountTable();
            dbConnection.createTransactionTable();
            dbConnection.createCustomerTransactionTable();

           
            int choice;
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Add Admin Details");
                System.out.println("2. Add Customer Details");
                System.out.println("3. Add Account Details");
                System.out.println("4. Add Transaction Details");
                System.out.println("5. Add Customer-Transaction Details");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addAdminDetails(dbConnection, scanner);
                        break;
                    case 2:
                        addCustomerDetails(dbConnection, scanner);
                        break;
                    case 3:
                        addAccountDetails(dbConnection, scanner);
                        break;
                    case 4:
                        addTransactionDetails(dbConnection, scanner);
                        break;
                    case 5:
                        addCustomerTransactionDetails(dbConnection, scanner);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addAdminDetails(DBConnection dbConnection, Scanner scanner) throws SQLException {
        System.out.println("Enter Admin details:");
        System.out.print("Admin ID: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Admin Name: ");
        String adminName = scanner.nextLine();
        System.out.print("Admin Password: ");
        String adminPassword = scanner.nextLine();
        dbConnection.addAdminDetails(adminId, adminName, adminPassword);
        System.out.println("Admin details added successfully.");
    }

    private static void addCustomerDetails(DBConnection dbConnection, Scanner scanner) throws SQLException {
        System.out.println("Enter Customer details:");    
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        dbConnection.addCustomerDetails( firstName, lastName, password);
        System.out.println("Customer details added successfully.");
    }
    private static void addAccountDetails(DBConnection dbConnection, Scanner scanner) throws SQLException {
        System.out.println("Enter Account details:");
        System.out.print("Balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        dbConnection.addAccountDetails(balance);
        System.out.println("Account details added successfully.");
    }

       private static void addTransactionDetails(DBConnection dbConnection, Scanner scanner) throws SQLException {
        System.out.println("Enter Transaction details:");
        System.out.print("Transaction Type: ");
        String transactionType = scanner.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        System.out.print("Amount: ");
        int amount = scanner.nextInt();
        System.out.print("Account Number: ");
        int accountNumber = scanner.nextInt();
        Date date = Date.valueOf(dateString);
        dbConnection.addTransactionDetails( transactionType, date, amount, accountNumber);
        System.out.println("Transaction details added successfully.");
    }

    private static void addCustomerTransactionDetails(DBConnection dbConnection, Scanner scanner) throws SQLException {
        System.out.println("Enter Customer-Transaction details:");
        System.out.print("Transaction ID: ");
        int transactionId = scanner.nextInt();
        dbConnection.addCustomerTransactionDetails (transactionId);
        System.out.println("Customer-Transaction details added successfully.");
    }

}
