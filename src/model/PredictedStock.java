package model;

import algorithm.StockManipulation;

import javax.swing.*;
import java.awt.*;

public class PredictedStock extends Component {
    private String stockName;

    public PredictedStock(String stockName) {
        this.stockName = stockName;
    }

    private void predictStockPrice() {
        // Implement the algorithm
        // Display the prediction result and graph
        JOptionPane.showMessageDialog(this, "(place holder)Predicted price for next week, month and year: ................");
    }

    public double predictPrice() {
        // Assuming we have a method in StockManipulation to predict the price based on the CSV data
        StockManipulation stockManipulation = new StockManipulation();
        return stockManipulation.predictPrice(stockName);
    }

    public double[] predictGraphData() {
        // Assuming we have a method in StockManipulation to get the predicted graph data
        StockManipulation stockManipulation = new StockManipulation();
        return stockManipulation.predictGraphData(stockName);
    }
}
