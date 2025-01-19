package ePortfolio;

import java.util.Objects;

public abstract class Investment {
    protected String symbol;
    protected String name;
    protected int quantity;
    protected double price;
    protected double bookValue;

    /**
     * Constructor to create a new Investment.
     *
     * @param symbol   the symbol of the investment
     * @param name     the name of the investment
     * @param quantity the quantity of the investment
     * @param price    the price of the investment
     */
    public Investment(String symbol, String name, int quantity, double price) {
        this.symbol = validateSymbol(symbol);
        this.name = validateName(name);
        this.quantity = validateQuantity(quantity);
        this.price = validatePrice(price);
        this.bookValue = calculateInitialBookValue(quantity, price);
    }

    /**
     * Copy constructor to create a duplicate of an existing Investment.
     *
     * @param other the investment to copy
     */
    public Investment(Investment other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from a null investment.");
        }
        this.symbol = other.symbol;
        this.name = other.name;
        this.quantity = other.quantity;
        this.price = other.price;
        this.bookValue = other.bookValue;
    }

    /**
     * Calculates the initial book value of the investment based on the quantity and
     * price. This method can be overridden by subclasses to include additional
     * calculations if needed.
     *
     * @param quantity the quantity of the investment
     * @param price    the price of the investment
     * @return the initial book value
     */
    protected double calculateInitialBookValue(int quantity, double price) {
        return quantity * price;
    }

    /**
     * Updates the price of the investment.
     *
     * @param newPrice the new price of the investment
     */
    public void updatePrice(double newPrice) {
        this.price = validatePrice(newPrice);
    }

    /**
     * Calculates the gain of the investment based on the current market value and
     * book value.
     *
     * @return the calculated gain
     */
    public double calculateGain() {
        double marketValue = this.quantity * this.price;
        return marketValue - this.bookValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getBookValue() {
        return bookValue;
    }

    /**
     * Validates the symbol of the investment.
     *
     * @param symbol the symbol to validate
     * @return the validated symbol in uppercase
     */
    protected String validateSymbol(String symbol) {
        if (symbol == null || !symbol.matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException("Invalid symbol. Symbols must be alphanumeric and uppercase.");
        }
        return symbol.toUpperCase();
    }

    /**
     * Validates the name of the investment.
     *
     * @param name the name to validate
     * @return the validated name
     */
    protected String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name. Name cannot be null or empty.");
        }
        return name.trim();
    }

    /**
     * Validates the quantity of the investment.
     *
     * @param quantity the quantity to validate
     * @return the validated quantity
     */
    protected int validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        return quantity;
    }

    /**
     * Validates the price of the investment.
     *
     * @param price the price to validate
     * @return the validated price
     */
    protected double validatePrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        return price;
    }

    @Override
    public String toString() {
        return String.format("Investment [Symbol: %s, Name: %s, Quantity: %d, Price: %.2f, Book Value: %.2f]",
                symbol, name, quantity, price, bookValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Investment that = (Investment) obj;
        return Objects.equals(symbol, that.symbol) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, name);
    }

    public abstract void buy(int additionalQuantity, double price);

    public abstract double sell(int quantityToSell, double price);
}
