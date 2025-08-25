package service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BankAccountManager {
    private static final Path ACCOUNTS_FILE = Paths.get(System.getProperty("user.dir"), "src", "data", "bankAccounts.txt");
    private static final Map<String, Double> accounts = new HashMap<>();

    static {
        loadAccounts();
    }

    public static void loadAccounts() {
        accounts.clear();
        try {
            if (Files.exists(ACCOUNTS_FILE)) {
                List<String> lines = Files.readAllLines(ACCOUNTS_FILE);
                for (String line : lines) {
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        try {
                            String account = parts[0];
                            String balanceStr = parts[1];

                            NumberFormat format = NumberFormat.getInstance(Locale.FRENCH); //(европейский формат с запятой)
                            Number number = format.parse(balanceStr);
                            double balance = number.doubleValue();

                            accounts.put(account, balance);
                            System.out.println("Загружен счет: " + account + " с балансом: " + balance);

                        } catch (ParseException e) {
                            System.out.println("Ошибка парсинга баланса в строке: " + line);
                        } catch (NumberFormatException e) {
                            System.out.println("Неверный формат числа в строке: " + line);
                        }
                    }
                }
                System.out.println("Всего загружено счетов: " + accounts.size());
            } else {
                System.out.println("Файл bankAccounts.txt не найден!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка загрузки счетов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveAccounts() {
        try {
            Files.createDirectories(ACCOUNTS_FILE.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(ACCOUNTS_FILE)) {
                for (Map.Entry<String, Double> entry : accounts.entrySet()) {
                    // Сохраняем с запятыми (европейский формат)
                    String balanceStr = String.format(Locale.FRENCH, "%.2f", entry.getValue());
                    writer.write(entry.getKey() + " " + balanceStr);
                    writer.newLine();
                }
            }
            System.out.println("Файл bankAccounts.txt обновлен");

        } catch (Exception e) {
            System.out.println("Ошибка сохранения счетов: " + e.getMessage());
        }
    }

    public static boolean accountExists(String accountNumber) {
        boolean exists = accounts.containsKey(accountNumber);
        if (!exists) {
            System.out.println("ВНИМАНИЕ: Счет не найден в системе: " + accountNumber);
        }
        return exists;
    }

    public static double getBalance(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.println("ОШИБКА: Попытка получить баланс несуществующего счета: " + accountNumber);
            return 0.0;
        }
        return accounts.get(accountNumber);
    }

    public static void updateBalance(String accountNumber, double amount) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.println("ОШИБКА: Попытка обновить несуществующий счет: " + accountNumber);
            return;
        }

        double currentBalance = accounts.get(accountNumber);
        double newBalance = currentBalance + amount;
        accounts.put(accountNumber, newBalance);

        System.out.printf("Обновление счета %s: %.2f -> %.2f (изменение: %+.2f)%n",
                accountNumber, currentBalance, newBalance, amount);

        saveAccounts();
    }

    public static void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("Нет данных о счетах");
            return;
        }

        System.out.println("=== ТЕКУЩИЕ СЧЕТА ===");
        System.out.println("Счет\t\tБаланс");
        System.out.println("========================");
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            System.out.printf("%s\t%.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.println("========================");
    }

    public static Map<String, Double> getAccounts() {
        return new HashMap<>(accounts);
    }

    // Вспомогательный метод для отладки
    public static void printAllAccounts() {
        System.out.println("=== ВСЕ СЧЕТА В ПАМЯТИ ===");
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}