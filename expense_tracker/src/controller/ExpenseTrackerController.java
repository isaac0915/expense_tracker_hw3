package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;
import java.io.File;
import java.io.IOException;

/**
 * The `ExpenseTrackerController` class acts as the intermediary between the
 * model and the view
 * in the MVC architecture. It handles user input, processes it, and updates the
 * model and view
 * accordingly. It is responsible for actions like adding transactions and
 * applying filters.
 * This class also implements the Strategy design pattern for filtering,
 * allowing different
 * filtering strategies (e.g., by amount or category) to be used
 * interchangeably.
 */
public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  /**
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;
  private Exporter exporter;

  /**
   * Constructs a new ExpenseTrackerController.
   * 
   * @param model The model for the expense tracker.
   * @param view  The view for the expense tracker.
   */
  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    this.exporter = new CsvExporter();
  }

  /**
   * Sets the Strategy class being used in the applyFilter method.
   *
   * @param filter The concrete strategy class to be used for filtering
   */
  public void setFilter(TransactionFilter filter) {
    this.filter = filter;
  }

  /**
   * Sets the Exporter strategy to be used for exporting transactions.
   */
  public void setExporter(Exporter exporter) {
    this.exporter = exporter;
  }

  /**
   * Export the provided transactions using the configured exporter.
   */
  public void exportTransactions(List<Transaction> transactions, File file) throws IOException {
    if (exporter == null) {
      throw new IllegalStateException("No exporter configured");
    }
    exporter.export(transactions, file);
  }

  /**
   * Refreshes the view with the current list of transactions from the model.
   * This method is called to update the UI whenever the data changes.
   */
  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  /**
   * Adds a new transaction to the model after validating the input.
   * 
   * @param amount   The amount of the transaction.
   * @param category The category of the transaction.
   * @return true if the transaction was added successfully, false otherwise.
   */
  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }

    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[] { t.getAmount(), t.getCategory(), t.getTimestamp() });
    refresh();
    return true;
  }

  /**
   * Adds a transaction but returns a user-friendly error message on failure.
   * Returns null when the add was successful.
   */
  public String addTransactionWithMessage(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return "Amount must be > 0 and <= 1000";
    }
    if (!InputValidation.isValidCategory(category)) {
      return "Category must be one of: food, travel, bills, entertainment, other";
    }

    try {
      Transaction t = new Transaction(amount, category);
      model.addTransaction(t);
      view.getTableModel().addRow(new Object[] { t.getAmount(), t.getCategory(), t.getTimestamp() });
      refresh();
      return null;
    } catch (IllegalArgumentException ex) {
      // Return the constructor's message if validation inside Transaction fails
      return ex.getMessage();
    }
  }

  /**
   * Applies the filter specified by the user.
   *
   * NOTE) This is applying the Strategy design pattern. This is the core method
   * using the strategy helper method.
   */
  public void applyFilter() {
    List<Transaction> filteredTransactions;
    // If no filter is specified, show all transactions.
    if (filter == null) {
      filteredTransactions = model.getTransactions();
    }
    // If a filter is specified, show only the transactions accepted by that filter.
    else {
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      filteredTransactions = filter.filter(transactions);
    }
    view.displayFilteredTransactions(filteredTransactions);
  }

}
