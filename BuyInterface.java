package ePortfolio;

import javax.swing.*;
import java.awt.*;

/**
 * The BuyInterface class provides a graphical user interface for buying
 * investments in the portfolio.
 * Users can input details such as type, symbol, name, quantity, and price to
 * purchase
 * a new investment or add to an existing one.
 */
public class BuyInterface extends JFrame {
    private final Portfolio portfolio;
    private final JTextField symbolField, nameField, quantityField, priceField;
    private final JComboBox<String> typeComboBox;
    private final JTextArea messageArea;

    /**
     * Constructs the BuyInterface with input fields, buttons, and message display.
     * 
     * @param parent    the parent JFrame that invoked this interface
     * @param portfolio the portfolio where the investment will be added
     */
    public BuyInterface(JFrame parent, Portfolio portfolio) {
        this.portfolio = portfolio;
        setTitle("Buy Investment");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create components
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel typeLabel = new JLabel("Type:");
        typeComboBox = new JComboBox<>(new String[] { "Stock", "MutualFund" });
        JLabel symbolLabel = new JLabel("Symbol:");
        symbolField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();

        inputPanel.add(typeLabel);
        inputPanel.add(typeComboBox);
        inputPanel.add(symbolLabel);
        inputPanel.add(symbolField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton buyButton = new JButton("Buy");
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(buyButton);
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
        buyButton.addActionListener(e -> handleBuy());
        resetButton.addActionListener(e -> resetFields());

        setVisible(true);
    }

    /**
     * Handles the "Buy" button click event.
     * Validates input and attempts to buy the investment using the portfolio
     * object.
     * Displays a success or error message in the message area.
     */
    private void handleBuy() {
        try {
            String type = (String) typeComboBox.getSelectedItem();
            String symbol = symbolField.getText().trim();
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            portfolio.buyInvestment(type, symbol, name, quantity, price);
            messageArea.setText("Successfully purchased investment: " + symbol);
        } catch (Exception ex) {
            messageArea.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Resets all input fields in the form, except the investment type ComboBox.
     */
    private void resetFields() {
        symbolField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }
}
