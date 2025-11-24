package model;

import controller.InputValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The `Transaction` class is a model class that represents a single financial transaction.
 * It encapsulates the core attributes of a transaction, including the amount, category,
 * and a timestamp. The class ensures the validity of its data by performing input
 * validation in its constructor. Its fields are immutable to ensure that a transaction's
 * state cannot be altered after creation.
 */
public class Transaction {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
  // final means that the variable cannot be changed
  private final double amount;
  private final String category;
  private final String timestamp;

  /**
   * Constructs a new Transaction with the specified amount and category.
   * It validates the input to ensure that the amount and category are valid.
   * A timestamp is automatically generated upon creation.
   * @param amount The amount of the transaction.
   * @param category The category of the transaction.
   * @throws IllegalArgumentException if the amount or category is invalid.
   */
  public Transaction(double amount, String category) {
    // Since this is a public constructor, perform input validation
    // to guarantee that the amount and category are both valid
    if (!InputValidation.isValidAmount(amount)) {
      throw new IllegalArgumentException("The amount is not valid.");
    }
    if (!InputValidation.isValidCategory(category)) {
      throw new IllegalArgumentException("The category is not valid.");
    }
      
    this.amount = amount;
    this.category = category;
    this.timestamp = generateTimestamp();
  }

  /**
   * Returns the amount of the transaction.
   * @return The transaction amount.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Returns the category of the transaction.
   * @return The transaction category.
   */
  public String getCategory() {
    return category;
  }
  
  /**
   * Returns the timestamp of when the transaction was created.
   * @return The timestamp as a formatted string ("dd-MM-yyyy HH:mm").
   */
  public String getTimestamp() {
    return timestamp;
  }

  // private helper method to generate timestamp
  private String generateTimestamp() {
     return LocalDateTime.now().format(DATE_FORMATTER);
  }

}
