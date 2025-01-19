package ePortfolio;

import javax.swing.*;
import java.awt.*;

/**
 * The UpdateInterface class provides a graphical user interface for updating
 * the prices of investments in the portfolio.
 * Users can navigate through investments, view their details, and update their
 * prices.
 */
public class UpdateInterface extends JFrame {
    private final Portfolio portfolio;
    private final JTextField symbolField, nameField, priceField;
    private final JTextArea messageArea;
    private int currentIndex = 0;

    /**
     * Constructs the UpdateInterface with fields for viewing and updating
     * investments.
     *
     * @param parent    the parent JFrame that invoked this interface
     * @param portfolio the portfolio containing the investments to be updated
     */
    public UpdateInterface(JFrame parent, Portfolio portfolio) {
        this.portfolio = portfolio;
        setTitle("Update Investment");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Components
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel symbolLabel = new JLabel("Symbol:");
        symbolField = new JTextField();
        symbolField.setEditable(false);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        nameField.setEditable(false);

        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();

        inputPanel.add(symbolLabel);
        inputPanel.add(symbolField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton prevButton = new JButton("Prev");
        JButton saveButton = new JButton("Save");
        JButton nextButton = new JButton("Next");
        buttonPanel.add(prevButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(nextButton);

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
        prevButton.addActionListener(e -> navigateInvestments(-1));
        nextButton.addActionListener(e -> navigateInvestments(1));
        saveButton.addActionListener(e -> updatePrice());

        // Load first investment
        if (portfolio.getInvestments().isEmpty()) {
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            messageArea.setText("No investments to update.");
        } else {
            loadInvestment(0);
        }

        setVisible(true);
    }

    /**
     * Navigates through the investments in the portfolio based on the given
     * direction.
     *
     * @param direction -1 to move to the previous investment, 1 to move to the next
     *                  investment
     */
    private void navigateInvestments(int direction) {
        currentIndex += direction;
        loadInvestment(currentIndex);
    }

    /**
     * Loads the details of the investment at the specified index into the fields.
     *
     * @param index the index of the investment to load
     */
    private void loadInvestment(int index) {
        Investment investment = portfolio.getInvestments().get(index);
        symbolField.setText(investment.getSymbol());
        nameField.setText(investment.getName());
        priceField.setText(String.valueOf(investment.getPrice()));
    }

    /**
     * Updates the price of the currently displayed investment.
     * Displays a success or error message in the message area.
     */
    private void updatePrice() {
        try {
            double newPrice = Double.parseDouble(priceField.getText().trim());
            Investment investment = portfolio.getInvestments().get(currentIndex);
            investment.updatePrice(newPrice);
            messageArea.setText("Updated investment:\n" + investment);
        } catch (Exception ex) {
            messageArea.setText("Error: " + ex.getMessage());
        }
    }
}
