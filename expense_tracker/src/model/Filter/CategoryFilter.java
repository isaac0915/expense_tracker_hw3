package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

/**
 * The CategoryFilter class only shows the Transactions with categories equal to the specified category.
 *
 * NOTE) This is applying the Strategy design pattern. This is a concrete strategy class.
 */
public class CategoryFilter implements TransactionFilter {
    private String categoryFilter;

    /**
     * Constructs a new CategoryFilter with the specified category.
     * It validates the category to ensure it is a valid filter criterion.
     * @param categoryFilter The category to filter transactions by.
     * @throws IllegalArgumentException if the category is invalid.
     */
    public CategoryFilter(String categoryFilter) {
        // Since the CategoryFilter constructor is public, 
        // the input validation needs to be performed again.
        if(!InputValidation.isValidCategory(categoryFilter)){
            throw new IllegalArgumentException("Invalid category filter");
        }else{
            this.categoryFilter = categoryFilter;
        }
    }

    /**
     * Filters a list of transactions, returning only those that match the filter's category (case-insensitive).
     * @param transactions The list of transactions to filter.
     * @return A new list containing only the transactions that match the category.
     * @throws IllegalArgumentException if the transactions list is null.
     */
    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
	// Perform input validation
        if (transactions == null) {
            throw new IllegalArgumentException("The transactions list must be non-null.");
	}
	
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equalsIgnoreCase(categoryFilter)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
}
