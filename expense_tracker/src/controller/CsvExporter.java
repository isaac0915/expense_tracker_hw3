package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Transaction;

/** CSV exporter — writes transactions to a CSV file (header + rows). */
public class CsvExporter implements Exporter {

    private static final String CSV_HEADER = "Amount,Category,Timestamp";
    public static final String FILE_EXTENSION = ".csv";
    public static final String FILE_EXTENSION_DESCRIPTION = "CSV Files (*.csv)";

    /**
     * Export transactions to the given file; throws IOException on write errors.
     */
    @Override
    public void export(List<Transaction> transactions, File file) throws IOException {
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println(CSV_HEADER);
            if (transactions != null) {
                for (Transaction t : transactions) {
                    pw.println(t.getAmount() + "," + t.getCategory() + "," + t.getTimestamp());
                }
            }
        }
    }
}
