import service.FileProcessor;
import service.ReportViewer;
import service.BankAccountManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== БАНКОВСКАЯ СИСТЕМА ===");
            System.out.println("1 - Обработка файлов переводов");
            System.out.println("2 - Просмотр отчета о транзакциях");
            System.out.println("3 - Просмотр отчета по датам");
            System.out.println("4 - Просмотр текущих счетов");
            System.out.println("5 - Выход");
            System.out.print("Выберите операцию: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Обработка файлов...");
                    FileProcessor.processFiles();
                    break;
                case "2":
                    System.out.println("Просмотр отчета...");
                    ReportViewer.viewReport();
                    break;
                case "3":
                    //нужно пофиксить!!!
                    System.out.println("Просмотр отчета по датам...");
                    System.out.print("Введите начальную дату (ГГГГ-ММ-ДД): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Введите конечную дату (ГГГГ-ММ-ДД): ");
                    String endDate = scanner.nextLine();
                    ReportViewer.viewReportByDate(startDate, endDate);
                    break;
                case "4":
                    System.out.println("Текущие счета:");
                    BankAccountManager.viewAccounts();
                    break;
                case "5":
                    System.out.println("Выход из системы...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}