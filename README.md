# Investment Portfolio Management System

## 1. Problem Overview
This program provides a graphical interface for managing an investment portfolio of Stocks and Mutual Funds. It allows users to buy, sell, update prices, calculate total gains, and search for investments efficiently. The portfolio supports data persistence by loading investments from a file and saving any changes back to the file. The GUI enhances usability and provides a more interactive experience.

## 2. Features
- Graphical User Interface (GUI):
Supports operations like Buy, Sell, Update, Get Gain, and Search through intuitive forms and buttons.
Displays information and errors in scrollable text areas for easy reading.

- Data Persistence:
Investments are loaded from a file and saved back, ensuring portfolio continuity across sessions.

- HashMap Indexing:
Efficient keyword-based search using a HashMap for name indexing.
- Robust Input Validation:
Ensures symbols are alphanumeric and uppercase.
Quantities and prices must be positive numbers.

- Investment Types:
Supports Stocks (with commission fees) and Mutual Funds (with redemption fees).

- Exception Handling:
Graceful handling of invalid inputs and data processing errors.

## 3. How to Run It
1. Compile the program from the `ePortfolio` directory:
   ```
      javac ePortfolio/*.java
   ```

### Commands:
- `buy` – Buy an investment.
- `sell` – Sell part or all of an investment.
- `update` – Update the prices of all investments.
- `getGain` – Calculate and show your portfolio’s total gain.
- `search` – Search investments by symbol or name.
- `quit` – Exit the program.

### Interfaces:
- Initial Interface
Description: The home screen displays a welcome message and instructions.
Commands: Accessed via the "Commands" menu, providing options to:
Buy
Sell
Update
Get Gain
Search
Quit

- Buy Interface
Fields:
Type: Select between "Stock" or "Mutual Fund."
Symbol, Name, Quantity, and Price.
Buttons:
Buy: Processes the purchase.
Reset: Clears all input fields.
Messages:
Displays success messages or error details.

- Sell Interface
Fields:
Symbol, Quantity, and Price.
Buttons:
Sell: Processes the sale.
Reset: Clears all input fields.
Messages:
Displays success messages, proceeds, or error details.

- Update Interface
Fields:
Symbol (read-only), Name (read-only), and Price (editable).
Buttons:
Prev: Navigates to the previous investment.
Next: Navigates to the next investment.
Save: Updates the current investment's price.
Messages:
Displays the updated investment details or error details.

- Total Gain Interface
Fields:
Total Gain: Displays the portfolio’s total gain (read-only).
Messages:
Lists individual gains for each investment.

- Search Interface
Fields:
Symbol, Name Keywords, Low Price, High Price.
Buttons:
Search: Executes the search with the provided criteria.
Reset: Clears all input fields.
Messages:
Displays search results or error messages.

## 4. How To Test It
1. General Input Handling
Valid Inputs:
Accept various forms of input commands, such as "buy", "Buy", "BUY", "b", "B" for the buy command, and similarly for other commands.
Ensure that symbols are alphanumeric and uppercase.
Validate that quantities are positive integers.
Validate that prices are positive numbers.
Invalid Inputs:
Reject commands like "bye", "exit", or any other input that does not match the recognized commands.
Prompt the user to re-enter input with an appropriate error message for invalid symbols, names, quantities, and prices.
Ensure incorrect data types (e.g., strings instead of numbers for quantity and price) are handled gracefully.
2. Buy Command
Major Conditions to Test:
Buying a new investment:
Valid new investment of type stock.
Valid new investment of type mutual fund.
Adding to an existing investment:
Valid addition to an existing stock.
Valid addition to an existing mutual fund.
Invalid Input Cases:
Invalid quantity (zero or negative).
Invalid price (zero or negative).
Invalid type (not "stock" or "mutualfund").
Edge Cases:
Very large quantities and prices.
Adding a very small quantity or at a low price (validations).
3. Sell Command
Major Conditions to Test:
Selling part of an investment:
Valid sale of a portion of a stock.
Valid sale of a portion of a mutual fund.
Selling all of an investment:
Valid sale of all shares/units, resulting in the removal of the investment.
Invalid Input Cases:
Selling more than the available quantity.
Invalid quantity (zero or negative).
Invalid price (zero or negative).
Symbol not found in the portfolio.
Edge Cases:
Very small and very large quantities or prices.
Selling exactly the remaining quantity.
4. Update Command
Major Conditions to Test:
Valid Price Updates:
Updating the price of all stocks.
Updating the price of all mutual funds.
Invalid Input Cases:
Entering invalid (non-positive) values for prices.
Handling empty input gracefully.
5. Get Gain Command
Major Conditions to Test:
Calculate the total gain of all investments.
Verify that gain calculations are correct based on book values and current prices.
6. Search Command
Major Conditions to Test:
Search by Symbol:
Matching symbol exactly.
Symbol not found.
Search by Name Keywords:
Single keyword match at the start, middle, or end of a name.
Multiple keywords (case-insensitive and regardless of order).
No matching keywords.
Search by Price Range:
Exact price match (e.g., "50").
Minimum price match (e.g., "10.00-").
Maximum price match (e.g., "-100.00").
Price range match (e.g., "10.00-100.00").
Combined Search Conditions:
Symbol and name keyword match.
Symbol, name keyword, and price range match.
Symbol match only, ignoring name and price.
No input provided (should match all investments).
7. File I/O Handling
Loading Data:
Load existing investments from a file.
Handle cases where the file does not exist (start with an empty portfolio).
Invalid file formats (e.g., missing fields, incorrect data types).
Saving Data:
Save investments to a specified file.
Ensure proper format for future loading.
Edge Cases:
Large data sets.
Corrupted file input handling.
8. HashMap-Based Search Optimization
Index Creation:
Correctly create and update index for keywords during loading.
Adding a new investment updates the index.
Deleting an investment updates the index (removal and decrementing positions).
Optimized Searches:
Single keyword search.
Multiple keyword searches with intersection handling.
Combined searches with symbol, keywords, and price range.
9. GUI-Specific Testing
Navigation:
Ensure all menu commands switch to the correct interface.
Verify the Quit button and window close functionality.
Usability:
Validate button actions and input field behavior.
Ensure error messages are clear and displayed in the text area.
10. Command Variations
Case-Insensitive Commands:
Recognize commands such as "BUY", "buy", "b", etc.
Recognize "quit" in various formats (e.g., "q", "Q", "QUIT").
Handling Invalid Commands:
Provide warning messages for unrecognized commands.
11. Edge Cases Across All Commands
Large Quantities and Prices:
Handle very large numbers without overflow or errors.
Empty Portfolio:
Handle commands gracefully when the portfolio is empty.

## 5. Future Improvements
- Support for Additional Investment Types: Expand beyond Stocks and Mutual Funds.
- Better Input Handling: Improve validation to avoid bad data.
- Enhanced GUI: Add charts or visualizations for portfolio data to improve the layout and user experience. 