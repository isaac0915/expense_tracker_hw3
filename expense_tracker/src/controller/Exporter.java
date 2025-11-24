package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import model.Transaction;

public interface Exporter {
    void export(List<Transaction> transactions, File file) throws IOException;
}
