package service;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileProcessor {
    private static final List<String> reportLines = new ArrayList<>();

    public static void processFiles() {
        try {
            Path inputPath = Paths.get(System.getProperty("user.dir"), "src", "input");
            Path archivePath = Paths.get(System.getProperty("user.dir"), "src", "archive");

            Files.createDirectories(inputPath);
            Files.createDirectories(archivePath);

            reportLines.clear();

            BankAccountManager.loadAccounts();

            int processedFiles = 0;
            try (var files = Files.list(inputPath)) {
                processedFiles = (int) files
                        .filter(file -> Files.isRegularFile(file) &&
                                file.toString().toLowerCase().endsWith(".txt"))
                        .count();
            }

            saveReport();
            System.out.println("Обработка завершена. Обработано файлов: " + processedFiles);

        } catch (Exception e) {
            System.out.println("Ошибка обработки файлов: " + e.getMessage());
        }
    }

    private static void processSingleFile(Path file) {
        try {
            System.out.println("\n--- Обработка файла: " + file.getFileName() + " ---");

            List<String> lines = Files.readAllLines(file);
            String bankAccountFrom = null;
            String bankAccountTo = null;
            String amountStr = null;

            // Ищем данные в файле
            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (word.matches("\\d{5}-\\d{5}")) {
                        if (bankAccountFrom == null) {
                            bankAccountFrom = word;
                        } else if (bankAccountTo == null) {
                            bankAccountTo = word;
                        }
                    } else if (word.matches("-?\\d+(\\.\\d+)?") && amountStr == null) {
                        amountStr = word;
                    }
                }
            }

            // Обработка транзакции
            if (bankAccountFrom != null && bankAccountTo != null && amountStr != null) {
                processTransaction(file.getFileName().toString(), bankAccountFrom, bankAccountTo, amountStr);
            } else {
                String errorMsg = "Файл не содержит полных данных (нужны: счет_от, счет_на, сумма)";
                addToReport(file.getFileName().toString(), "", "", 0, false, errorMsg);
                System.out.println(errorMsg);
            }

            // Перемещаем в archive
            Path archiveFile = Paths.get(System.getProperty("user.dir"), "src", "archive", file.getFileName().toString());
            Files.move(file, archiveFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл перемещен в archive");

        } catch (Exception e) {
            System.out.println("Ошибка обработки файла " + file.getFileName() + ": " + e.getMessage());
        }
    }

    private static void processTransaction(String fileName, String fromAccount, String toAccount, String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            boolean success = true;
            String error = "";

            // Проверка суммы
            if (amount <= 0) {
                success = false;
                error = "неверная сумма перевода: " + amount;
            }

            // Проверка достаточности средств
            if (success) {
                double fromBalance = BankAccountManager.getBalance(fromAccount);
                if (fromBalance < amount) {
                    success = false;
                    error = "недостаточно средств на счете " + fromAccount +
                            " (текущий баланс: " + fromBalance + ", требуется: " + amount + ")";
                }
            }

            // Выполнение перевода
            if (success) {
                BankAccountManager.updateBalance(fromAccount, -amount);
                BankAccountManager.updateBalance(toAccount, amount);
                System.out.printf("Перевод выполнен: %.2f с %s на %s%n", amount, fromAccount, toAccount);
            }

            addToReport(fileName, fromAccount, toAccount, amount, success, error);

        } catch (NumberFormatException e) {
            addToReport(fileName, fromAccount, toAccount, 0, false, "неверный формат суммы: " + amountStr);
        } catch (Exception e) {
            addToReport(fileName, fromAccount, toAccount, 0, false, "ошибка обработки: " + e.getMessage());
        }
    }

    private static void addToReport(String fileName, String fromAccount, String toAccount,
                                    double amount, boolean success, String error) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        String status = success ? "успешно обработан" : "ошибка: " + error;
        String operation = String.format("перевод с %s на %s %.2f", fromAccount, toAccount, amount);

        String reportLine = timestamp + " | " + fileName + " | " + operation + " | " + status;
        reportLines.add(reportLine);

        System.out.println((success ? "✓ " : "✗ ") + reportLine);
    }

    private static void saveReport() {
        if (reportLines.isEmpty()) {
            System.out.println("Нет данных для отчета");
            return;
        }

        try {
            Path reportPath = Paths.get(System.getProperty("user.dir"), "src", "output", "report.txt");
            Files.createDirectories(reportPath.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(reportPath,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                writer.write("=== ОТЧЕТ ОБ ОБРАБОТКЕ ТРАНЗАКЦИЙ ===");

                for (String line : reportLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            System.out.println("Отчет сохранен: " + reportPath);

        } catch (Exception e) {
            System.out.println("Ошибка сохранения отчета: " + e.getMessage());
        }
    }
}