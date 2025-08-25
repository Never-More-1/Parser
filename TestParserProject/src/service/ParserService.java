package service;

import model.Transaction;
import exception.*;
import util.ValidationUtil;
import java.nio.file.*;
import java.util.*;

public class ParserService {
    private static final List<Transaction> transactions = new ArrayList<>();

    public static void parseFile(Path file) {
        try {
            List<String> lines = Files.readAllLines(file);
            Map<String, Integer> accounts = new HashMap<>();
            List<String> transactionData = new ArrayList<>();

            // Чтение данных счетов и транзакций
            for (String line : lines) {
                String[] parts = line.split("\\s+");
                for (String part : parts) {
                    if (part.matches("\\d{5}-\\d{5}")) {
                        transactionData.add(part);
                    } else if (part.matches("-?\\d+")) {
                        transactionData.add(part);
                    }
                }
            }

            // Обработка транзакций
            processTransactions(file.getFileName().toString(), transactionData, accounts);

        } catch (Exception e) {
            System.err.println("Ошибка обработки файла: " + file.getFileName());
        }
    }

    private static void processTransactions(String fileName, List<String> data,
                                            Map<String, Integer> accounts) {
        for (int i = 0; i < data.size() - 3; i += 4) {
            try {
                String fromAccount = data.get(i);
                String toAccount = data.get(i + 1);
                int amount = Integer.parseInt(data.get(i + 2));

                if (!ValidationUtil.isValidAccountNumber(fromAccount) ||
                        !ValidationUtil.isValidAccountNumber(toAccount)) {
                    throw new InvalidAccountException("Неверный формат номера счета");
                }

                if (!ValidationUtil.isValidAmount(amount)) {
                    throw new InvalidAmountException("Неверная сумма перевода");
                }

                // Логика обработки транзакции
                boolean success = true;
                String error = "";

                transactions.add(new Transaction(fileName, fromAccount, toAccount,
                        amount, success, error));

            } catch (Exception e) {
                transactions.add(new Transaction(fileName, data.get(i), data.get(i + 1),
                        Integer.parseInt(data.get(i + 2)),
                        false, e.getMessage()));
            }
        }
    }

    public static List<Transaction> getTransactions() {
        return transactions;
    }

    public static void clearTransactions() {
        transactions.clear();
    }
}