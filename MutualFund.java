package ePortfolio;

public class MutualFund extends Investment {
    private static final double REDEMPTION_FEE = 45.00;

    /**
     * Constructs a MutualFund object with the specified symbol, name, quantity, and
     * price.
     * 
     * @param symbol   the symbol of the mutual fund
     * @param name     the name of the mutual fund
     * @param quantity the quantity of units
     * @param price    the price per unit
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
    }

    /**
     * Copy constructor for creating a duplicate MutualFund object.
     * 
     * @param other the MutualFund object to copy
     */
    public MutualFund(MutualFund other) {
        super(other);
    }

    /**
     * Buys additional units of the mutual fund.
     * The book value is increased based on the quantity and price of the new
     * purchase.
     * 
     * @param additionalQuantity the quantity to buy
     * @param price              the price per unit
     */
    @Override
    public void buy(int additionalQuantity, double price) {
        this.quantity += validateQuantity(additionalQuantity);
        this.bookValue += additionalQuantity * validatePrice(price);
    }

    /**
     * Sells a specified quantity of units of the mutual fund.
     * A redemption fee is subtracted from the proceeds of the sale.
     * 
     * @param quantityToSell the quantity to sell
     * @param price          the price per unit
     * @return the proceeds from the sale after deducting the redemption fee
     * @throws IllegalArgumentException if the quantity to sell is greater than the
     *                                  available quantity
     */
    @Override
    public double sell(int quantityToSell, double price) {
        if (quantityToSell > this.quantity) {
            throw new IllegalArgumentException("Insufficient quantity to sell.");
        }
        double proceeds = quantityToSell * validatePrice(price) - REDEMPTION_FEE;
        double bookValuePerUnit = this.bookValue / this.quantity;
        this.bookValue -= bookValuePerUnit * quantityToSell;
        this.quantity -= quantityToSell;
        return proceeds;
    }

    /**
     * Returns a string representation of the mutual fund, including its symbol,
     * name, quantity, price, and book value.
     * 
     * @return a string representation of the mutual fund
     */
    @Override
    public String toString() {
        return String.format("Mutual Fund [Symbol: %s, Name: %s, Quantity: %d, Price: %.2f, Book Value: %.2f]",
                symbol, name, quantity, price, bookValue);
    }

    /**
     * Compares two MutualFund objects for equality.
     * 
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MutualFund))
            return false;
        return super.equals(obj);
    }

    /**
     * Returns a hash code for the MutualFund object.
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
