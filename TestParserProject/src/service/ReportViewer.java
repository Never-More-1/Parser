package service;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

public class ReportViewer {
    public static void viewReport() {
        try {
            Path reportPath = Paths.get(System.getProperty("user.dir"), "src", "output", "report.txt");

            if (!Files.exists(reportPath)) {
                System.out.println("Отчет еще не создан. Сначала обработайте файлы.");
                return;
            }

            System.out.println("\n=== ПОЛНЫЙ ОТЧЕТ ===");
            Files.readAllLines(reportPath).forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Ошибка чтения отчета: " + e.getMessage());
        }
    }

    public static void viewReportByDate(String startDateStr, String endDateStr) {
        try {
            Path reportPath = Paths.get(System.getProperty("user.dir"), "src", "output", "report.txt");

            if (!Files.exists(reportPath)) {
                System.out.println("Отчет еще не создан.");
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            System.out.println("\n=== ОТЧЕТ С " + startDate + " ПО " + endDate + " ===");

            List<String> lines = Files.readAllLines(reportPath);
            boolean found = false;

            for (String line : lines) {
                if (line.contains("|") && !line.startsWith("=")) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 0) {
                        String dateStr = parts[0].trim().substring(0, 10); // Берем только дату
                        LocalDate transactionDate = LocalDate.parse(dateStr);

                        if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                            System.out.println(line);
                            found = true;
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("Нет записей за указанный период.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка фильтрации отчета: " + e.getMessage());
            System.out.println("Убедитесь, что даты в формате гггг-мм-дд");
        }
    }
}