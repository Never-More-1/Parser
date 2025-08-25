package util;

public class ValidationUtil {
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && accountNumber.matches("\\d{5}-\\d{5}");
    }

    public static boolean isValidAmount(int amount) {
        return amount > 0;
    }

    public static boolean hasSufficientFunds(int currentBalance, int transferAmount) {
        return currentBalance >= transferAmount;
    }
}