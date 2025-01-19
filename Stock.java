package ePortfolio;

public class Stock extends Investment {
    private static final double COMMISSION = 9.99;

    /**
     * Constructs a Stock object with the specified symbol, name, quantity, and
     * price.
     * 
     * @param symbol   the symbol of the stock
     * @param name     the name of the stock
     * @param quantity the quantity of shares
     * @param price    the price per share
     */
    public Stock(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        this.bookValue = calculateInitialBookValue(quantity, price);
    }

    /**
     * Copy constructor for creating a duplicate Stock object.
     * 
     * @param other the Stock object to copy
     */
    public Stock(Stock other) {
        super(other);
    }

    /**
     * Calculates the initial book value of the stock, including a commission fee.
     * 
     * @param quantity the quantity of shares
     * @param price    the price per share
     * @return the initial book value including the commission fee
     */
    @Override
    protected double calculateInitialBookValue(int quantity, double price) {
        return quantity * price + COMMISSION;
    }

    /**
     * Buys additional shares of the stock.
     * The book value is increased based on the quantity and price of the new
     * purchase, plus a commission fee.
     * 
     * @param additionalQuantity the quantity to buy
     * @param price              the price per share
     */
    @Override
    public void buy(int additionalQuantity, double price) {
        this.quantity += validateQuantity(additionalQuantity);
        this.bookValue += additionalQuantity * validatePrice(price) + COMMISSION;
    }

    /**
     * Sells a specified quantity of shares of the stock.
     * A commission fee is subtracted from the proceeds of the sale.
     * 
     * @param quantityToSell the quantity to sell
     * @param price          the price per share
     * @return the proceeds from the sale after deducting the commission fee
     * @throws IllegalArgumentException if the quantity to sell is greater than the
     *                                  available quantity
     */
    @Override
    public double sell(int quantityToSell, double price) {
        if (quantityToSell > this.quantity) {
            throw new IllegalArgumentException("Insufficient quantity to sell.");
        }
        double proceeds = quantityToSell * validatePrice(price) - COMMISSION;
        double bookValuePerShare = this.bookValue / this.quantity;
        this.bookValue -= bookValuePerShare * quantityToSell;
        this.quantity -= quantityToSell;
        return proceeds;
    }

    /**
     * Returns a string representation of the stock, including its symbol, name,
     * quantity, price, and book value.
     * 
     * @return a string representation of the stock
     */
    @Override
    public String toString() {
        return String.format("Stock [Symbol: %s, Name: %s, Quantity: %d, Price: %.2f, Book Value: %.2f]",
                symbol, name, quantity, price, bookValue);
    }

    /**
     * Compares two Stock objects for equality.
     * 
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Stock))
            return false;
        return super.equals(obj);
    }

    /**
     * Returns a hash code for the Stock object.
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
