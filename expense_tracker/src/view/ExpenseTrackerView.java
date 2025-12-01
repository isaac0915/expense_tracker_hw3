package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import controller.CsvExporter;

/**
 * The `ExpenseTrackerView` class is responsible for the user interface (UI) of the application.
 * It displays the transaction data to the user and sends user actions (e.g., button clicks)
 * to the controller. As the view component in the MVC architecture, it is designed to be
 * unaware of the application's business logic and focuses solely on presentation.
 */
public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;

  private JTextField categoryFilterField;
  private JButton categoryFilterBtn;

  private JTextField amountFilterField;
  private JButton amountFilterBtn;

  private JButton clearFilterBtn;
  private JButton exportBtn;

  private List<Transaction> displayedTransactions = new ArrayList<>(); // ✅ Moved here

  /**
   * Constructs the ExpenseTrackerView and initializes all UI components.
   * It sets up the main frame, table, input fields, and buttons.
   */
  public ExpenseTrackerView() {
    setTitle("Expense Tracker");
    setSize(600, 400);

    String[] columnNames = { "serial", "Amount", "Category", "Date" };
    this.model = new DefaultTableModel(columnNames, 0);

    transactionsTable = new JTable(model);
    addTransactionBtn = new JButton("Add Transaction");

    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();
    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    JLabel categoryFilterLabel = new JLabel("Filter by Category:");
    categoryFilterField = new JTextField(10);
    categoryFilterBtn = new JButton("Filter by Category");

    JLabel amountFilterLabel = new JLabel("Filter by Amount:");
    amountFilterField = new JTextField(10);
    amountFilterBtn = new JButton("Filter by Amount");

    clearFilterBtn = new JButton("Clear Filter");
    exportBtn = new JButton("Export CSV");
    exportBtn.setToolTipText("Export currently displayed transactions to a .csv file");

    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel);
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(amountFilterBtn);
    buttonPanel.add(categoryFilterBtn);
    buttonPanel.add(clearFilterBtn);
    buttonPanel.add(exportBtn);

    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Returns the table model used by the transactions table.
   * @return The `DefaultTableModel` for the transactions table.
   */
  public DefaultTableModel getTableModel() {
    return model;
  }

  /**
   * Returns the transactions table component.
   * @return The `JTable` component displaying transactions.
   */
  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  /**
   * Gets the amount value from the amount input field.
   * It parses the text and returns a double value.
   * @return The parsed amount as a double, or `Double.NaN` if parsing fails.
   */
  public double getAmountField() {
    // Prefer the formatted value if available
    Object value = amountField.getValue();
    if (value instanceof Number) {
      return ((Number) value).doubleValue();
    }

    String text = amountField.getText();
    if (text == null || text.trim().isEmpty()) {
      return 0;
    }

    try {
      Number n = NumberFormat.getNumberInstance().parse(text.trim());
      return n.doubleValue();
    } catch (ParseException | NullPointerException e) {
      // Return NaN to indicate an unparsable value so caller can show an error
      return Double.NaN;
    }
  }

  /**
   * Sets the amount field component.
   * @param amountField The `JFormattedTextField` to be used for the amount.
   */
  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  /**
   * Gets the category text from the category input field.
   * @return The text content of the category field.
   */
  public String getCategoryField() {
    return categoryField.getText();
  }

  /**
   * Sets the category field component.
   * @param categoryField The `JTextField` to be used for the category.
   */
  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  /**
   * Adds an `ActionListener` to the "Apply Category Filter" button.
   * @param listener The `ActionListener` to be added.
   */
  public void addApplyCategoryFilterListener(ActionListener listener) {
    categoryFilterBtn.addActionListener(listener);
  }

  /**
   * Shows an input dialog to get the category for filtering.
   * @return The category string entered by the user.
   */
  public String getCategoryFilterInput() {
    return JOptionPane.showInputDialog(this, "Enter Category Filter:");
  }

  /**
   * Adds an `ActionListener` to the "Apply Amount Filter" button.
   * @param listener The `ActionListener` to be added.
   */
  public void addApplyAmountFilterListener(ActionListener listener) {
    amountFilterBtn.addActionListener(listener);
  }

  /**
   * Shows an input dialog to get the amount for filtering.
   * @return The amount entered by the user as a double, or 0.0 if parsing fails.
   */
  public double getAmountFilterInput() {
    String input = JOptionPane.showInputDialog(this, "Enter Amount Filter:");
    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  /**
   * Adds an `ActionListener` to the "Clear Filter" button.
   * @param listener The `ActionListener` to be added.
   */
  public void addClearFilterListener(ActionListener listener) {
    clearFilterBtn.addActionListener(listener);
  }

  /**
   * Opens a file chooser dialog to select a CSV file for exporting.
   * Validates that the selected file has a .csv extension.
   *
   * @return the selected File object, or null if the user cancels or
   *         validation fails
   */
  public File chooseExportFile() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter csvFilter = new FileNameExtensionFilter(CsvExporter.FILE_EXTENSION_DESCRIPTION, CsvExporter.FILE_EXTENSION.substring(1));
    chooser.setFileFilter(csvFilter);
    int retval = chooser.showSaveDialog(this);
    if (retval != JFileChooser.APPROVE_OPTION) {
      return null; // user cancelled
    }

    File file = chooser.getSelectedFile();
    String name = file == null ? null : file.getName();
    if (name == null || name.trim().isEmpty()) {
      JOptionPane.showMessageDialog(this,
          "Please specify a non-empty file name that ends with '" + CsvExporter.FILE_EXTENSION + "'.", "Invalid File Name",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }

    if (!name.toLowerCase().endsWith(CsvExporter.FILE_EXTENSION)) {
      JOptionPane.showMessageDialog(this,
          "File name must end with '" + CsvExporter.FILE_EXTENSION + "'. Please retry and include the extension.",
          "Invalid File Extension", JOptionPane.ERROR_MESSAGE);
      return null;
    }

    return file;
  }

  /**
   * Register an {@link ActionListener} that will be invoked when the Export
   * button is pressed. The listener typically calls the controller to perform
   * the actual export after calling {@link #chooseExportFile()} to obtain the
   * destination {@link java.io.File}.
   *
   * @param listener the action listener to register
   */
  public void addExportListener(ActionListener listener) {
    exportBtn.addActionListener(listener);
  }

    
  /**
   * Refreshes the transactions table with a new list of transactions.
   * It clears the table and repopulates it with the given transactions,
   * and also calculates and displays the total cost.
   * @param transactions The list of `Transaction` objects to display.
   */
  public void refreshTable(List<Transaction> transactions) {
    model.setRowCount(0);
    this.displayedTransactions = transactions; // ✅ Track displayed transactions

    int rowNum = model.getRowCount();
    double totalCost = 0;

    for (Transaction t : transactions) {
      totalCost += t.getAmount();
    }

    for (Transaction t : transactions) {
      model.addRow(new Object[] { ++rowNum, t.getAmount(), t.getCategory(), t.getTimestamp() });
    }

    model.addRow(new Object[] { "Total", null, null, totalCost });
    transactionsTable.updateUI();
  }

  /**
   * Returns the "Add Transaction" button component.
   * @return The `JButton` for adding a transaction.
   */
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  /**
   * Displays a list of filtered transactions in the table.
   * This is a convenience method that calls `refreshTable`.
   * @param filteredTransactions The list of filtered transactions to display.
   */
  public void displayFilteredTransactions(List<Transaction> filteredTransactions) {
    refreshTable(filteredTransactions);
  }

  /**
   * Returns the list of transactions currently displayed in the table.
   * @return A list of `Transaction` objects.
   */
  public List<Transaction> getDisplayedTransactions() {
    return displayedTransactions;
  }

  // Optional: remove if no longer needed
  // public void highlightRows(List<Integer> rowIndexes) { ... }

  // public void highlightRows(List<Integer> rowIndexes) {
  // // The row indices are being used as hashcodes for the transactions.
  // // The row index directly maps to the the transaction index in the list.
  // transactionsTable.setDefaultRenderer(Object.class, new
  // DefaultTableCellRenderer() {
  // @Override
  // public Component getTableCellRendererComponent(JTable table, Object value,
  // boolean isSelected,
  // boolean hasFocus, int row, int column) {
  // Component c = super.getTableCellRendererComponent(table, value, isSelected,
  // hasFocus, row, column);
  // if (rowIndexes.contains(row)) {
  // c.setBackground(new Color(173, 255, 168)); // Light green
  // } else {
  // c.setBackground(table.getBackground());
  // }
  // return c;
  // }
  // });

  // transactionsTable.repaint();
  // }

}
