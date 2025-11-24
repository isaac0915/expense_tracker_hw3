package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The `ExpenseTrackerModel` class represents the data and business logic of the application.
 * It manages the list of transactions, providing methods to add, remove, and retrieve them.
 * The model is designed to be independent of the user interface, ensuring a clear separation
 * of concerns as per the MVC pattern. It returns an unmodifiable list of transactions to
 * prevent direct external modification, enhancing data integrity.
 */
public class ExpenseTrackerModel {

  private List<Transaction> transactions;

  /**
   * Constructs a new ExpenseTrackerModel, initializing an empty list of transactions.
   */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  /**
   * Adds a transaction to the model.
   * @param t The transaction to be added.
   */
  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  /**
   * Removes a transaction from the model.
   * @param t The transaction to be removed.
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  /**
   * Returns an unmodifiable view of the list of transactions.
   * This prevents the list from being modified directly by external code,
   * ensuring that all changes go through the model's methods.
   * @return An unmodifiable list of transactions.
   */
  public List<Transaction> getTransactions() {
    // Alternative 1: Apply the decorator design pattern (see below)
    // Alternative 2: Return a copy of the list
    return Collections.unmodifiableList(transactions);
  }

}