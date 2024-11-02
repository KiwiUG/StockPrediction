package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import database.DBConnection;
import database.StockDataLoader;
import model.Stock;

public class StockHomeScreen extends JFrame {

    private JList<String> stockList; // To display the list of stocks
    private DefaultListModel<String> stockListModel; // Model for the JList
    private StockDataLoader stockDataLoader;

    public StockHomeScreen() {
        // Initialize components
        stockDataLoader = new StockDataLoader();
        stockListModel = new DefaultListModel<>();
        stockList = new JList<>(stockListModel);

        // Set up the JFrame
        setTitle("Stock Prediction App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load stocks from the database
        loadStocks();

        // Add components
        add(new JScrollPane(stockList), BorderLayout.CENTER);
        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStockDetails();
            }
        });
        add(viewButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadStocks() {
        // Load stock names from the database and add them to the stockListModel
        DBConnection dbConnection = new DBConnection();
        List<Stock> stocks = dbConnection.getAllStocks(); // Implement this method to fetch all stocks
        for (Stock stock : stocks) {
            stockListModel.addElement(stock.getName());
        }
    }

    private void viewStockDetails() {
        // Get selected stock
        String selectedStock = stockList.getSelectedValue();
        if (selectedStock != null) {
            // Open stock details screen
            StockDetailScreen stockDetailScreen = new StockDetailScreen(selectedStock);
            stockDetailScreen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a stock.");
        }
    }



    public static void main(String[] args) {
        // Start the application
        SwingUtilities.invokeLater(() -> new StockHomeScreen());
    }
}
