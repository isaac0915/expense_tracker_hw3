# Expense Tracker (CS520 HW2)

This project is the solution for **CS520 Homework 2: Design Patterns & Testing**.
It extends the original Expense Tracker app to improve modularity, testability, and usability using the MVC architecture and the Strategy design pattern.


## Overview

The Expense Tracker allows users to add and manage daily transactions while maintaining a running total.
This version adds filtering functionality (by amount or category), reuses input validation logic from Homework 1, and includes a design plan for exporting transactions to a CSV file.



## How to Compile and Run

There are two ways to compile and run the application:

### 1. Manual Compilation (from `src` directory)

From the terminal, navigate to the `src` directory and run:

```bash
cd src
javac ExpenseTrackerApp.java
java ExpenseTrackerApp
```

### 2. Using Apache Ant (from `expense_tracker` directory)

From the `expense_tracker` directory, you can use the following commands:

- **Compile the source code:**
  ```bash
  ant compile
  ```

- **Run the application (after compiling):**
  ```bash
  java -cp bin ExpenseTrackerApp
  ```

- **Run the unit tests:**
  ```bash
  ant test
  ```

If compiled successfully, the Expense Tracker GUI will appear.


## Java Version

This project was compiled and tested with:

```
java version "1.8.0_471"
Java(TM) SE Runtime Environment (build 1.8.0_471-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.471-b09, mixed mode)
```

Please ensure your JDK version is compatible (JDK 8 recommended).

## Features

* **Add Transaction:**
  Enter a valid amount and category, then click **Add Transaction**.
  The transaction appears in the list, and the total cost updates automatically.

* **Filter Transactions (Strategy Pattern):**
  Filter by amount or category.
  Implemented using the `TransactionFilter` interface and concrete classes `AmountFilter` and `CategoryFilter` for reusability and extensibility.

* **Input Validation:**
  Reuses Homework 1 validation logic for both adding transactions and filtering.

## Testing

The test suite includes:

* The two original tests (from Homework 1)
* Five new test cases:

  * Add valid transaction
  * Invalid amount input
  * Invalid category input
  * Filter by amount
  * Filter by category

All tests pass successfully (see `test_screenshot.png`).


### **Usability: Export to CSV file**
This project includes a working "Export to CSV" feature.

- UI: an **Export CSV** button opens a save dialog. The view validates the filename (non-empty and ending with `.csv`) and shows an error dialog when invalid. On success a confirmation dialog shows the exported path.
- MVC: the View collects the file choice and the Controller performs the export by delegating to an `Exporter` strategy. The default implementation is `CsvExporter` which writes a header and one transaction row per line.
- Files & locations:
  - `src/view/ExpenseTrackerView.java` — `chooseExportFile()` and `addExportListener(...)` (UI + filename validation)
  - `src/controller/ExpenseTrackerController.java` — `exportTransactions(...)` and `setExporter(...)`
  - `src/controller/Exporter.java` — exporter interface
  - `src/controller/CsvExporter.java` — CSV implementation (writes header + rows)
- CSV format: first line is `Amount,Category,Timestamp`. Each remaining line is `amount,category,timestamp`.



### **Project Structure**

```
expense_tracker/
│
├── src/              # Source files (MVC components)
├── test/             # JUnit test cases
├── lib/              # JUnit library
├── jdoc/             # Generated Javadoc
├── build.xml         # Ant build file
├── README.md         # Updated README
├── gitlog.txt        # Git commit log
├── test_screenshot.png
└── export.pdf        # Usability design plan