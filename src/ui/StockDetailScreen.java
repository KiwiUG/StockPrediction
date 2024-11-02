package ui;

import database.StockDataLoader;
import model.PredictedStock;
import model.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StockDetailScreen extends JFrame {

    private String stockName;
    private StockDataLoader stockDataLoader;

    public StockDetailScreen(String stockName) {
        this.stockName = stockName;
        stockDataLoader = new StockDataLoader();

        // Set up the JFrame
        setTitle(stockName + " Details");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fetch stock data and create UI components
        displayStockDetails();

        setVisible(true);
    }

    private void displayStockDetails() {
        // Fetch the current stock data from the CSV or database
        List<Stock> stockPrices = stockDataLoader.CSVReader(stockName); // Ensure this returns List<Stock>

        // Create and display the chart
        displayStockChart(stockPrices);

        JButton predictButton = new JButton("Predict");
        predictButton.addActionListener(e -> new PredictedStock(stockName));
        add(predictButton, BorderLayout.SOUTH);
    }

    private void displayStockChart(List<Stock> stockPrices) {
        // Create a dataset from stock prices
        TimeSeries series = new TimeSeries("Stock Price");
        for (Stock price : stockPrices) {
            // Assume Stock has getDate() returning a String and getCurrentPrice() returning a double
            series.add(new Day(price.getDate()), price.getCurrentPrice());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);

        // Create the chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                stockName + " Price Chart",
                "Date",
                "Price",
                dataset,
                false,
                true,
                false
        );

        // Add the chart to a ChartPanel and set it on the JFrame
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> new StockDetailScreen("YourStockName"));
    }
}
