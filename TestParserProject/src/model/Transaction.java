package model;

import java.time.LocalDateTime;

public class Transaction {
    private final LocalDateTime timestamp;
    private final String fileName;
    private final String fromAccount;
    private final String toAccount;
    private final double amount;
    private final boolean success;
    private final String errorMessage;

    public Transaction(String fileName, String fromAccount, String toAccount,
                       double amount, boolean success, String errorMessage) {
        this.timestamp = LocalDateTime.now();
        this.fileName = fileName;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public String toReportString() {
        String status = success ? "успешно обработан" : errorMessage;
        return String.format("%s | %s | перевод с %s на %s %.2f | %s",
                timestamp, fileName, fromAccount, toAccount, amount, status);
    }

    // Геттеры
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}