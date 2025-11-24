package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import model.Transaction;

/** Simple exporter interface for writing transactions to a file. */
public interface Exporter {
    /** Export the given transactions to the provided file. */
    void export(List<Transaction> transactions, File file) throws IOException;
}
