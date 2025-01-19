package ePortfolio;

import java.io.*;
import java.util.*;

/**
 * The Portfolio class manages a collection of investments, including stocks and
 * mutual funds.
 * It supports adding, removing, updating, searching, and calculating gains for
 * investments.
 * It also maintains a HashMap-based index for optimizing keyword searches.
 */
public class Portfolio {
    private List<Investment> investments = new ArrayList<>();
    private Map<String, List<Integer>> keywordIndex = new HashMap<>();

    /**
     * Default constructor for Portfolio.
     */
    public Portfolio() {
    }

    /**
     * Loads investments from a specified file into the portfolio.
     * 
     * @param filename the name of the file to load investments from
     */
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename + ". Starting with an empty portfolio.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String type = null, symbol = null, name = null;
            int quantity = 0;
            double price = 0.0, bookValue = 0.0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("type")) {
                    type = line.split("=")[1].trim().replace("\"", "");
                } else if (line.startsWith("symbol")) {
                    symbol = line.split("=")[1].trim().replace("\"", "");
                } else if (line.startsWith("name")) {
                    name = line.split("=")[1].trim().replace("\"", "");
                } else if (line.startsWith("quantity")) {
                    quantity = Integer.parseInt(line.split("=")[1].trim().replace("\"", ""));
                } else if (line.startsWith("price")) {
                    price = Double.parseDouble(line.split("=")[1].trim().replace("\"", ""));
                } else if (line.startsWith("bookValue")) {
                    bookValue = Double.parseDouble(line.split("=")[1].trim().replace("\"", ""));

                    if (type != null && symbol != null && name != null) {
                        Investment newInvestment = type.equalsIgnoreCase("stock")
                                ? new Stock(symbol, name, quantity, price)
                                : new MutualFund(symbol, name, quantity, price);
                        investments.add(newInvestment);
                        indexKeywords(newInvestment.getName(), investments.size() - 1);
                    }

                    // Reset fields
                    type = symbol = name = null;
                    quantity = 0;
                    price = bookValue = 0.0;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    /**
     * Saves the current state of the investments to a specified file.
     * 
     * @param filename the name of the file to save investments to
     */
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Investment investment : investments) {
                writer.write("type = \"" + (investment instanceof Stock ? "stock" : "mutualfund") + "\"\n");
                writer.write("symbol = \"" + investment.getSymbol() + "\"\n");
                writer.write("name = \"" + investment.getName() + "\"\n");
                writer.write("quantity = \"" + investment.getQuantity() + "\"\n");
                writer.write("price = \"" + investment.getPrice() + "\"\n");
                writer.write("bookValue = \"" + investment.getBookValue() + "\"\n\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Calculates the total gain of all investments in the portfolio.
     * 
     * @return the total gain
     */
    public double getTotalGain() {
        double totalGain = 0;
        for (Investment investment : investments) {
            totalGain += investment.calculateGain();
        }
        return totalGain;
    }

    /**
     * Searches for investments that match specified criteria.
     * 
     * @param symbol      the symbol to match (or empty for any symbol)
     * @param nameKeyword the keyword to match in the name (or empty for any)
     * @param priceRange  the price range to match (or empty for any range)
     * @return a list of matching investments as strings
     */
    public List<String> searchInvestments(String symbol, String nameKeyword, String priceRange) {
        List<Integer> matchedPositions = null;
        if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
            String[] keywords = splitWords(nameKeyword);
            for (String keyword : keywords) {
                List<Integer> positions = keywordIndex.getOrDefault(keyword.toLowerCase(), new ArrayList<>());
                if (matchedPositions == null) {
                    matchedPositions = new ArrayList<>(positions);
                } else {
                    matchedPositions.retainAll(positions); // Intersection for multiple keywords
                }
                if (matchedPositions.isEmpty()) {
                    break;
                }
            }
        }

        List<String> results = new ArrayList<>();
        List<Integer> searchPositions = (matchedPositions == null || matchedPositions.isEmpty())
                ? getAllPositions()
                : matchedPositions;

        for (int index : searchPositions) {
            Investment investment = investments.get(index);
            if (matchesSymbol(investment.getSymbol(), symbol) &&
                    matchesPriceRange(investment.getPrice(), priceRange)) {
                results.add(investment.toString());
            }
        }

        return results;
    }

    /**
     * Adds or updates an investment in the portfolio.
     * 
     * @param type     the type of investment (stock or mutual fund)
     * @param symbol   the symbol of the investment
     * @param name     the name of the investment
     * @param quantity the quantity to buy
     * @param price    the price of the investment
     */
    public void buyInvestment(String type, String symbol, String name, int quantity, double price) {
        symbol = validateSymbol(symbol);
        name = validateName(name);
        quantity = validateQuantity(quantity);
        price = validatePrice(price);

        Investment existingInvestment = findInvestment(symbol);
        if (existingInvestment != null) {
            existingInvestment.buy(quantity, price);
        } else {
            Investment newInvestment = type.equalsIgnoreCase("stock")
                    ? new Stock(symbol, name, quantity, price)
                    : new MutualFund(symbol, name, quantity, price);
            investments.add(newInvestment);
            indexKeywords(newInvestment.getName(), investments.size() - 1);
        }
    }

    /**
     * Sells a specified quantity of an investment in the portfolio.
     * 
     * @param symbol   the symbol of the investment
     * @param quantity the quantity to sell
     * @param price    the price of the investment
     * @return the proceeds from the sale
     */
    public double sellInvestment(String symbol, int quantity, double price) {
        symbol = validateSymbol(symbol);
        quantity = validateQuantity(quantity);
        price = validatePrice(price);

        Investment investment = findInvestment(symbol);
        if (investment != null) {
            double proceeds = investment.sell(quantity, price);
            if (investment.getQuantity() == 0) {
                int index = investments.indexOf(investment);
                investments.remove(index);
                updateIndexOnRemoval(index);
            }
            return proceeds;
        }

        throw new IllegalArgumentException("Investment with symbol " + symbol + " not found.");
    }

    /**
     * Updates the prices of all investments in the portfolio.
     */
    public void updatePrices() {
        for (Investment investment : investments) {
            double newPrice = promptForPrice(investment.getClass().getSimpleName(), investment.getSymbol());
            investment.updatePrice(newPrice);
        }
    }

    /**
     * Finds an investment in the portfolio by its symbol.
     * 
     * @param symbol the symbol of the investment
     * @return the investment if found, or null if not found
     */
    private Investment findInvestment(String symbol) {
        for (Investment investment : investments) {
            if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                return createCopy(investment);
            }
        }
        return null;
    }

    /**
     * Creates a copy of an investment to avoid privacy leaks.
     * 
     * @param investment the investment to copy
     * @return a copy of the investment
     */
    private Investment createCopy(Investment investment) {
        if (investment instanceof Stock) {
            return new Stock((Stock) investment);
        } else if (investment instanceof MutualFund) {
            return new MutualFund((MutualFund) investment);
        }
        return null;
    }

    /**
     * Indexes the keywords in the investment's name for efficient searching.
     * 
     * @param name     the name of the investment
     * @param position the position of the investment in the list
     */
    private void indexKeywords(String name, int position) {
        String[] keywords = splitWords(name);
        for (String keyword : keywords) {
            keywordIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(position);
        }
    }

    /**
     * Updates the keyword index when an investment is removed.
     * 
     * @param removedPosition the position of the removed investment
     */
    private void updateIndexOnRemoval(int removedPosition) {
        for (Map.Entry<String, List<Integer>> entry : keywordIndex.entrySet()) {
            List<Integer> positions = entry.getValue();
            positions.removeIf(pos -> pos == removedPosition);
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i) > removedPosition) {
                    positions.set(i, positions.get(i) - 1);
                }
            }
            if (positions.isEmpty()) {
                keywordIndex.remove(entry.getKey());
            }
        }
    }

    /**
     * Gets a list of all positions of investments in the portfolio.
     * 
     * @return a list of all positions
     */
    private List<Integer> getAllPositions() {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < investments.size(); i++) {
            positions.add(i);
        }
        return positions;
    }

    /**
     * Splits a string into lowercase words for keyword indexing.
     * 
     * @param input the input string
     * @return an array of lowercase words
     */
    private String[] splitWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new String[0];
        }
        return input.toLowerCase().split("\\s+");
    }

    /**
     * Checks if an investment symbol matches a search symbol.
     * 
     * @param investmentSymbol the symbol of the investment
     * @param searchSymbol     the symbol to match
     * @return true if the symbols match, false otherwise
     */
    private boolean matchesSymbol(String investmentSymbol, String searchSymbol) {
        return searchSymbol.isEmpty() || investmentSymbol.equalsIgnoreCase(searchSymbol);
    }

    /**
     * Checks if an investment price falls within a specified price range.
     * 
     * @param price      the price of the investment
     * @param priceRange the price range to match
     * @return true if the price matches the range, false otherwise
     */
    private boolean matchesPriceRange(double price, String priceRange) {
        if (priceRange == null || priceRange.trim().isEmpty()) {
            return true;
        }
        priceRange = priceRange.trim();
        try {
            if (priceRange.matches("^\\d+(\\.\\d{1,2})?$")) {
                double targetPrice = Double.parseDouble(priceRange);
                return price == targetPrice;
            }
            if (priceRange.matches("^\\d+(\\.\\d{1,2})?-$")) {
                double minPrice = Double.parseDouble(priceRange.substring(0, priceRange.length() - 1));
                return price >= minPrice;
            }
            if (priceRange.matches("^-(\\d+(\\.\\d{1,2})?)$")) {
                double maxPrice = Double.parseDouble(priceRange.substring(1));
                return price <= maxPrice;
            }
            if (priceRange.matches("^\\d+(\\.\\d{1,2})?-\\d+(\\.\\d{1,2})?$")) {
                String[] parts = priceRange.split("-");
                double minPrice = Double.parseDouble(parts[0]);
                double maxPrice = Double.parseDouble(parts[1]);
                return price >= minPrice && price <= maxPrice;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates an investment symbol.
     * 
     * @param symbol the symbol to validate
     * @return the validated symbol in uppercase
     * @throws IllegalArgumentException if the symbol is invalid
     */
    private String validateSymbol(String symbol) {
        if (symbol == null || !symbol.matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException("Invalid symbol format. Symbols must be alphanumeric and uppercase.");
        }
        return symbol.toUpperCase();
    }

    /**
     * Validates an investment name.
     * 
     * @param name the name to validate
     * @return the validated name
     * @throws IllegalArgumentException if the name is invalid
     */
    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        return name.trim();
    }

    /**
     * Validates an investment quantity.
     * 
     * @param quantity the quantity to validate
     * @return the validated quantity
     * @throws IllegalArgumentException if the quantity is invalid
     */
    private int validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        return quantity;
    }

    /**
     * Validates an investment price.
     * 
     * @param price the price to validate
     * @return the validated price
     * @throws IllegalArgumentException if the price is invalid
     */
    private double validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        return price;
    }

    /**
     * Prompts the user for a new price of an investment.
     * 
     * @param type   the type of investment
     * @param symbol the symbol of the investment
     * @return the new price entered by the user
     */
    private double promptForPrice(String type, String symbol) {
        System.out.println("Enter new price for " + type + " " + symbol + ":");
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                double price = Double.parseDouble(scanner.nextLine().trim());
                if (price > 0) {
                    return price;
                } else {
                    System.out.println("Price must be greater than 0. Please enter a valid price:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a valid number.");
            }
        }
    }
}
