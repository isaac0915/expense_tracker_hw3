package model.Filter;

import java.util.List;

import model.Transaction;

/**
 * The TransactionFilter supports filtering the transaction list.
 *
 * NOTE) The Strategy design pattern is being applied. This is the Strategy interface.
 */
public interface TransactionFilter {

  /**
   * Filters a given list of transactions based on a specific criterion.
   * @param transactions The list of transactions to be filtered.
   * @return A new list containing only the transactions that meet the filter's criteria.
   */
  public List<Transaction> filter(List<Transaction> transactions);

}
