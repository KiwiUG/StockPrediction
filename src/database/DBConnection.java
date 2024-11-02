package database;

import model.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:src/database/Stock_Data.db"; // Path to your SQLite DB
    private Connection connection;

    public DBConnection() {
        connect();
    }

    // Method to connect to the database
    private void connect() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM Stock_Data"; // Adjust the table name if needed

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String location = rs.getString("Data");
                Stock stock = new Stock(name, location);
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

}
