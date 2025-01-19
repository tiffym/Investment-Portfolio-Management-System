package ePortfolio;

import javax.swing.*;
import java.awt.*;

/**
 * The GainInterface class provides a graphical user interface to display
 * the total gain of the portfolio and the individual gains for each investment.
 */
public class GainInterface extends JFrame {
    private final Portfolio portfolio;
    private final JTextField totalGainField;
    private final JTextArea messageArea;

    /**
     * Constructs the GainInterface for viewing portfolio gains.
     *
     * @param parent    the parent JFrame that invoked this interface
     * @param portfolio the portfolio containing investments to calculate gains for
     */
    public GainInterface(JFrame parent, Portfolio portfolio) {
        this.portfolio = portfolio;
        setTitle("Total Gain");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Components
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JLabel totalGainLabel = new JLabel("Total Gain:");
        totalGainField = new JTextField();
        totalGainField.setEditable(false);

        inputPanel.add(totalGainLabel);
        inputPanel.add(totalGainField);

        messageArea = new JTextArea(10, 50);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Calculate gains
        displayGains();

        setVisible(true);
    }

    /**
     * Calculates and displays the total gain of the portfolio and the individual
     * gains for each investment.
     */
    private void displayGains() {
        double totalGain = portfolio.getTotalGain();
        totalGainField.setText(String.format("%.2f", totalGain));

        StringBuilder gains = new StringBuilder();
        for (Investment investment : portfolio.getInvestments()) {
            gains.append(investment.getName())
                    .append(" (").append(investment.getSymbol()).append("): $")
                    .append(String.format("%.2f", investment.calculateGain()))
                    .append("\n");
        }

        messageArea.setText(gains.toString());
    }
}
