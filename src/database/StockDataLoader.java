package database;

import model.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDataLoader {

    private DBConnection dbConnection;

    public StockDataLoader() {
        dbConnection = new DBConnection();
    }

    // Method to load stock data from the database
    public Stock loadStockData(String stockName) {
        Stock stock = null;
        String query = "SELECT * FROM Stock_Data WHERE name = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stockName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                stock = new Stock();
                stock.setName(resultSet.getString("name"));
                stock.setCurrentPrice(resultSet.getDouble("current_price")); // Update based on your schema
                stock.setDate(resultSet.getString("date")); // Adjust according to your date format
                // Add additional fields as necessary
                CSVReader(stockName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    // Method to load all stocks (if needed)


    public String getCsvLocation(String stockName) {
        String csvLocation = null;
        String query = "SELECT Data FROM Stock_Data WHERE name = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, stockName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                csvLocation = rs.getString("Data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return csvLocation;
    }

    public List<Stock> CSVReader(String stockName) {
            String csvFile = "D:/StockPrediction/src/"+getCsvLocation(stockName)+".csv"; // Replace with your CSV file path
            String line;
            String csvSplitBy = ","; // Assuming the CSV is comma-separated
            Stock stock = null;
            List<Stock> stocks = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

                // Read the file line by line
                while ((line = br.readLine()) != null) {

                    // Split each line by commas into an array of values
                    String[] values = line.split(csvSplitBy);

                    // Example: Access each value (assuming columns are: Date, Price, Volume, etc.)
                    String date = values[0];
                    String price = values[7];
                    String volume = values[11];

                    stock = new Stock();
                    stock.setName(values[0]);
                    stock.setCurrentPrice(Double.parseDouble(values[7])); // Update based on your schema
                    stock.setDate(values[11]); // Adjust according to your date format

                    // Display the values
                    System.out.println("Date: " + date + ", Price: " + price + ", Volume: " + volume);

                    stocks.add(stock);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return stocks;
    }
}
