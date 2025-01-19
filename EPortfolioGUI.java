package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The EPortfolioGUI class provides a graphical user interface for the
 * Investment
 * Portfolio Management System.
 * Users can manage their investments through commands such as Buy, Sell,
 * Update,
 * Get Gain, Search, or Quit.
 */
public class EPortfolioGUI extends JFrame {
    private final Portfolio portfolio;

    /**
     * Constructs the main GUI application for the Investment Portfolio Management
     * System.
     */
    public EPortfolioGUI() {
        portfolio = new Portfolio();
        initializeGUI();
    }

    /**
     * Initializes the graphical user interface components.
     */
    private void initializeGUI() {
        setTitle("Investment Portfolio Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu commandsMenu = new JMenu("Commands");

        // Menu items
        JMenuItem buyItem = new JMenuItem("Buy");
        JMenuItem sellItem = new JMenuItem("Sell");
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem gainItem = new JMenuItem("Get Gain");
        JMenuItem searchItem = new JMenuItem("Search");
        JMenuItem quitItem = new JMenuItem("Quit");

        // Add menu items to the commands menu
        commandsMenu.add(buyItem);
        commandsMenu.add(sellItem);
        commandsMenu.add(updateItem);
        commandsMenu.add(gainItem);
        commandsMenu.add(searchItem);
        commandsMenu.addSeparator();
        commandsMenu.add(quitItem);

        // Add commands menu to the menu bar
        menuBar.add(commandsMenu);
        setJMenuBar(menuBar);

        // Content pane with a welcome message
        JPanel contentPane = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel(
                "<html>"
                        + "<h1>Welcome to the Investment Portfolio System</h1>"
                        + "<p>Choose a command from the &apos;Commands&apos; menu to buy or sell an investment, "
                        + "update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.</p>"
                        + "</html>",
                SwingConstants.CENTER);
        contentPane.add(welcomeLabel, BorderLayout.CENTER);
        add(contentPane);

        // Action listeners for menu items
        buyItem.addActionListener(e -> showBuyInterface());
        sellItem.addActionListener(e -> showSellInterface());
        updateItem.addActionListener(e -> showUpdateInterface());
        gainItem.addActionListener(e -> showGainInterface());
        searchItem.addActionListener(e -> showSearchInterface());
        quitItem.addActionListener(e -> System.exit(0));
    }

    /**
     * Opens the Buy interface to allow the user to purchase investments.
     */
    private void showBuyInterface() {
        new BuyInterface(this, portfolio);
    }

    /**
     * Opens the Sell interface to allow the user to sell investments.
     */
    private void showSellInterface() {
        new SellInterface(this, portfolio);
    }

    /**
     * Opens the Update interface to allow the user to update prices of investments.
     */
    private void showUpdateInterface() {
        new UpdateInterface(this, portfolio);
    }

    /**
     * Opens the Get Gain interface to calculate and display the total gain of the
     * portfolio.
     */
    private void showGainInterface() {
        new GainInterface(this, portfolio);
    }

    /**
     * Opens the Search interface to allow the user to search for specific
     * investments.
     */
    private void showSearchInterface() {
        new SearchInterface(this, portfolio);
    }

    /**
     * Main method to run the Investment Portfolio Management System GUI
     * application.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EPortfolioGUI app = new EPortfolioGUI();
            app.setVisible(true);
        });
    }
}
