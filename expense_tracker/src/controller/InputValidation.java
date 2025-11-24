package controller;

import java.util.Arrays;

/**
 * The `InputValidation` class is a utility class that provides static methods for
 * validating user input. It centralizes all validation logic, such as checking if a
 * transaction amount or category is valid, ensuring consistency and reusability across
 * the application. This helps to protect the model from invalid data.
 */
public class InputValidation {

  /**
   * Checks if the given amount is valid.
   * A valid amount is greater than 0 and less than or equal to 1000.
   * @param amount The amount to validate.
   * @return true if the amount is valid, false otherwise.
   */
  public static boolean isValidAmount(double amount) {
    
    // Check range
    if(amount >1000) {
      return false;
    }
    if (amount < 0){
      return false;
    }
    if (amount == 0){
      return false;
    }
    return true;
  }

  /**
   * Checks if the given category is valid.
   * A valid category is a non-null, non-empty string containing only letters,
   * and must be one of the predefined valid categories (case-insensitive).
   * The valid categories are: "food", "travel", "bills", "entertainment", "other".
   * @param category The category to validate.
   * @return true if the category is valid, false otherwise.
   */
  public static boolean isValidCategory(String category) {

    if(category == null) {
      return false; 
    }
  
    if(category.trim().isEmpty()) {
      return false;
    }

    if(!category.matches("[a-zA-Z]+")) {
      return false;
    }

    String[] validWords = {"food", "travel", "bills", "entertainment", "other"};

    if(!Arrays.asList(validWords).contains(category.toLowerCase())) {
      // invalid word  
      return false;
    }
  
    return true;
  
  }

}
