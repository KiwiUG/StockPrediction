package model;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;

public class Stock {
    private String name;
    private String csvFileLocation;
    private String date; // Add a date field
    private double currentPrice; // Add current price field

    // Constructor
    public Stock(String name, String csvFileLocation,String date, double currentPrice) {
        this.name = name;
        this.csvFileLocation = csvFileLocation;
        this.date=date;
        this.currentPrice=currentPrice;
    }

    public Stock(String name, String location) {
        this.name = name;
    }

    public Stock() {

    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDate() {
        return date;
    }


    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        String lastLine = readLastLineFromCSV(csvFileLocation);
        if (lastLine != null) {
            String[] data = lastLine.split(",");
            return Double.parseDouble(data[6]); // Assuming 'ltp' is in the 7th column (index 6)
        }
        return 0.0;
    }

    public String getLastTradingDate() {
        String lastLine = readLastLineFromCSV(csvFileLocation);
        if (lastLine != null) {
            String[] data = lastLine.split(",");
            return data[0]; // Assuming the date is in the 1st column (index 0)
        }
        return null;
    }

    private String readLastLineFromCSV(String filePath) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String lastLine = null;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lastLine = currentLine;
            }
            return lastLine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Stock fetchStockFromDatabase(int stockId) {
        DBConnection dbConnection = new DBConnection();
        String query = "SELECT Name, Data FROM Stock_Data WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, stockId);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                String data = resultSet.getString("Data");
                return new Stock(name, data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
