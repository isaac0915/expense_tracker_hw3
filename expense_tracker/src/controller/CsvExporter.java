package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.Transaction;

public class CsvExporter implements Exporter {

    private static final String CSV_HEADER = "Amount,Category,Timestamp";

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
