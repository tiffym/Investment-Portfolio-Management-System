package ePortfolio;

import javax.swing.*;
import java.awt.*;

/**
 * The SearchInterface class provides a graphical user interface to search
 * for investments in the portfolio based on various criteria.
 */
public class SearchInterface extends JFrame {
    private final Portfolio portfolio;
    private final JTextField symbolField, nameField, lowPriceField, highPriceField;
    private final JTextArea messageArea;

    /**
     * Constructs the SearchInterface for searching investments.
     *
     * @param parent    the parent JFrame that invoked this interface
     * @param portfolio the portfolio containing investments to search
     */
    public SearchInterface(JFrame parent, Portfolio portfolio) {
        this.portfolio = portfolio;
        setTitle("Search Investments");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Components
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel symbolLabel = new JLabel("Symbol:");
        symbolField = new JTextField();

        JLabel nameLabel = new JLabel("Name Keywords:");
        nameField = new JTextField();

        JLabel lowPriceLabel = new JLabel("Low Price:");
        lowPriceField = new JTextField();

        JLabel highPriceLabel = new JLabel("High Price:");
        highPriceField = new JTextField();

        inputPanel.add(symbolLabel);
        inputPanel.add(symbolField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(lowPriceLabel);
        inputPanel.add(lowPriceField);
        inputPanel.add(highPriceLabel);
        inputPanel.add(highPriceField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(searchButton);
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
        searchButton.addActionListener(e -> handleSearch());
        resetButton.addActionListener(e -> resetFields());

        setVisible(true);
    }

    /**
     * Handles the search action. Retrieves the input values, builds a price range,
     * and performs a search on the portfolio.
     * Displays the results in the message area or an error message if applicable.
     */
    private void handleSearch() {
        String symbol = symbolField.getText().trim();
        String keywords = nameField.getText().trim();
        String lowPrice = lowPriceField.getText().trim();
        String highPrice = highPriceField.getText().trim();

        try {
            String priceRange = buildPriceRange(lowPrice, highPrice);
            java.util.List<String> results = portfolio.searchInvestments(symbol, keywords, priceRange);
            if (results.isEmpty()) {
                messageArea.setText("No investments found matching the criteria.");
            } else {
                messageArea.setText(String.join("\n", results));
            }
        } catch (Exception ex) {
            messageArea.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Resets all input fields to their default (empty) state.
     */
    private void resetFields() {
        symbolField.setText("");
        nameField.setText("");
        lowPriceField.setText("");
        highPriceField.setText("");
    }

    /**
     * Builds a price range string from the provided low and high price values.
     *
     * @param low  the low price value as a string
     * @param high the high price value as a string
     * @return a formatted price range string
     */
    private String buildPriceRange(String low, String high) {
        if (low.isEmpty() && high.isEmpty()) {
            return "";
        }
        if (low.isEmpty()) {
            return "-" + high;
        }
        if (high.isEmpty()) {
            return low + "-";
        }
        return low + "-" + high;
    }
}
