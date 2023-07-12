package com.techlabs.bankapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DBConnection {
	private static DBConnection Dbconnection;
    private static Connection connection;
    
    private DBConnection() {
    }
    
    public static synchronized DBConnection getDBConnection() {
        if ( Dbconnection == null) {
        	 Dbconnection = new DBConnection();
        }
        return  Dbconnection;
    }
    
    public Connection connect() throws SQLException {
        try {
            // Registering the drivers
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "root", "vismita2000");
            System.out.println("Connection established successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
    
    public void createbankDb() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS bankdb");
            preparedStatement.executeUpdate();
            System.out.println("bankdb created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }
    
    public void createAdminTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Admin (" +
                    "AdminId INT PRIMARY KEY," +
                    "Name VARCHAR(15)," +
                    "Password VARCHAR(10)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Admin Table created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }

    public void createCustomerTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Customer (" +
                    "CustomerId INT  AUTO_INCREMENT PRIMARY KEY," +
                    "FirstName VARCHAR(15)," +
                    "LastName VARCHAR(15)," +
                    "Password VARCHAR(10)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Customer Table created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }

      public void createAccountTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Account (" +
                    "AccountNumber INT PRIMARY KEY," +
                    "Balance DECIMAL(10,2)," +
                    "CustomerId INT AUTO_INCREMENT,"+
                    "FOREIGN KEY(CustomerId ) REFERENCES Customer(CustomerId )" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Account Table created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }
    public void createTransactionTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Transaction (" +
                    "TransactionId INT  AUTO_INCREMENT PRIMARY KEY," +
                    "TransactionType VARCHAR(10)," +
                    "Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "Amount INT," +
                    "AccountNumber INT," +
                    "FOREIGN KEY (AccountNumber) REFERENCES Account(AccountNumber)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Transaction Table created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }

    public void createCustomerTransactionTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS CustomerTransaction (" +
                   "CustomerId INT  AUTO_iNCREMENT," +
                    "TransactionId INT  ," +
                    "PRIMARY KEY (CustomerId, TransactionId),"+
                    "FOREIGN KEY (CustomerId ) REFERENCES Customer(CustomerId )," +
                    "FOREIGN KEY (TransactionId) REFERENCES Transaction(TransactionId)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("CustomerTransaction Table created successfully");
            preparedStatement.close();
        }  finally {
            connection.close();
        }
    }
    public void addAdminDetails(int adminId, String name, String password) throws SQLException {
        connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Admin (AdminId, Name, Password) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, adminId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
            System.out.println("Admin details added successfully");
            preparedStatement.close();
        } 
    
    public void addCustomerDetails(String firstName, String lastName, String password) throws SQLException {
        connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer ( FirstName, LastName, Password) VALUES ( ?, ?, ?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
            System.out.println("Customer details added successfully");
            preparedStatement.close();
        } 

    public void addAccountDetails(double balance) throws SQLException {
        connect();
            int accountNumber = generateRandomAccountNumber(); // Generate a random 8-digit account number
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Account (AccountNumber, Balance) VALUES (?, ?)");
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setDouble(2, balance);
            preparedStatement.executeUpdate();
            System.out.println("Account details added successfully. Account Number: " + accountNumber);
            preparedStatement.close();
        } 

    private int generateRandomAccountNumber() {
        Random random = new Random();
        return 10000000 + random.nextInt(90000000); // Generate an 8-digit account number
    }

    public void addTransactionDetails( String transactionType, Date date, int amount, int accountNumber) throws SQLException {
        connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Transaction ( TransactionType, Date, Amount, AccountNumber) VALUES ( ?, ?, ?, ?)");
            preparedStatement.setString(1, transactionType);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, amount);
            preparedStatement.setInt(4, accountNumber);
            preparedStatement.executeUpdate();
            System.out.println("Transaction details added successfully");
            preparedStatement.close();
        } 
   
    public void addCustomerTransactionDetails( int transactionId) throws SQLException {
      connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CustomerTransaction ( TransactionId) VALUES ( ?)");
            preparedStatement.setInt(1, transactionId);
            preparedStatement.executeUpdate();
            System.out.println("CustomerTransaction details added successfully");
            preparedStatement.close();
        } 
    }






