package ePortfolio;

import javax.swing.*;
import java.awt.*;

/**
 * The SellInterface class provides a graphical user interface for selling
 * investments in the portfolio.
 * Users can input details such as symbol, quantity, and price to sell an
 * existing investment.
 */
public class SellInterface extends JFrame {
    private final Portfolio portfolio;
    private final JTextField symbolField, quantityField, priceField;
    private final JTextArea messageArea;

    /**
     * Constructs the SellInterface with input fields, buttons, and a message area.
     *
     * @param parent    the parent JFrame that invoked this interface
     * @param portfolio the portfolio from which investments will be sold
     */
    public SellInterface(JFrame parent, Portfolio portfolio) {
        this.portfolio = portfolio;
        setTitle("Sell Investment");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Components
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel symbolLabel = new JLabel("Symbol:");
        symbolField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();

        inputPanel.add(symbolLabel);
        inputPanel.add(symbolField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton sellButton = new JButton("Sell");
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(sellButton);
        buttonPanel.add(resetButton);

        messageArea = new JTextArea(10, 50);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Action listeners
        sellButton.addActionListener(e -> handleSell());
        resetButton.addActionListener(e -> resetFields());

        setVisible(true);
    }

    /**
     * Handles the "Sell" button click event.
     * Validates input and attempts to sell the specified investment using the
     * portfolio object.
     * Displays a success or error message in the message area.
     */
    private void handleSell() {
        try {
            String symbol = symbolField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            double proceeds = portfolio.sellInvestment(symbol, quantity, price);
            messageArea.setText("Successfully sold investment. Proceeds: $" + proceeds);
        } catch (Exception ex) {
            messageArea.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Resets all input fields in the form.
     */
    private void resetFields() {
        symbolField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }
}
