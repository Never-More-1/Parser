import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*Описание задания
Создать программу для выполнения денежного перевода с одного счета на другой.
Предварительно в программе должен находится файл с номерами счетов и суммами
на них. При запуске программа должна ожидать ввода информации из консоли.
При выборе функции парсинга программа должна парсить все подходящие файлы из
каталога ‘input’ и перенести распаршеные файлы в другой каталог ‘archive’. В
результате парсинга файлов программа должна сформировать/обновить файл-отчет и
обновить информацию в файле с номерами счетов и суммами до актуальных.*/
        String bankAccountFrom = "";
        String bankAccountTo = "";
        String error = "";
        String date = "";
        int moneyTransfer = 0;
        int moneyOnBankAccount = 0;
        int flag = 0;
        boolean success = true;
        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();
        List<String> informationLines = new ArrayList<>();
        List<String> archiveLines = new ArrayList<>();

        try (BufferedReader readerInput = new BufferedReader(new FileReader("D:\\input.txt"));//файл с данными откуда куда сколько
             BufferedReader readerInformation = new BufferedReader(new FileReader("D:\\information.txt"));//файл с данными о счетах и суммами на них
             BufferedWriter writerArchive = new BufferedWriter(new FileWriter("D:\\archive.txt"));
             BufferedWriter writerOutput = new BufferedWriter(new FileWriter("D:\\output.txt"))) {//файл для записи распаршенной инфы

            String element;

            while ((element = readerInput.readLine()) != null) {//чтение из input.txt и занесение в коллекцию inputLines
                String[] words = element.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        inputLines.add(word);
                    }
                }
            }

            while ((element = readerInformation.readLine()) != null) {//чтение из information.txt и занесение в коллекцию informationLines
                String[] words = element.split("[|]");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        informationLines.add(word);
                    }
                }
            }

            int index = 0;//индекс для записи счетов(если чеиный - счет откуда, если нечетный - счет куда)
            System.out.println("1 - вызов операции парсинга файлов перевода из input" + "\n" + "2 - вызов операции вывода списка всех переводов из файла-отчета");
            int choose = scanner.nextInt();
            if (choose == 1) {
                for (String line : inputLines) {
                    success = true;
                    if (line.matches("\\d{5}-\\d{5}")) {//номер счета
                        if (index % 2 == 0) {
                            bankAccountFrom = line;
                            index++;
                            System.out.println(line);
                            writerArchive.write(line + "|");
                            flag++;
                            continue;//вместо else, т.к. else это дурной тон
                        }
                        bankAccountTo = line;
                        System.out.println(line);
                        writerArchive.write(line + "|");
                        index++;
                        flag++;
                    }
                    if (line.matches("\\d{4}-\\d{2}-\\d{2}")) {//дата перевода
                        date = line;
                        System.out.println(line);
                        System.out.println();
                        writerArchive.write(line + "\n");
                        flag++;
                    }
                    if (line.matches("-?\\d+")) {//деньги для перевода
                        moneyTransfer = Integer.parseInt(line);
                        System.out.println(line);
                        writerArchive.write(line + "|");
                        flag++;
                    }
                }
                System.out.println("Парсинг произведен");
            }
            if (choose == 2) {
                for (String line : inputLines) {
                    success = true;
                    if (line.matches("\\d{5}-\\d{5}")) {//номер счета
                        if (index % 2 == 0) {
                            bankAccountFrom = line;
                            index++;
                            System.out.println(line);
                            writerArchive.write(line + "|");
                            flag++;
                            continue;//вместо else, т.к. else это дурной тон
                        }
                        bankAccountTo = line;
                        System.out.println(line);
                        writerArchive.write(line + "|");
                        index++;
                        flag++;
                    }
                    if (line.matches("\\d{4}-\\d{2}-\\d{2}")) {//дата перевода
                        date = line;
                        System.out.println(line);
                        System.out.println();
                        writerArchive.write(line + "\n");
                        flag++;
                    }
                    if (line.matches("-?\\d+")) {//деньги для перевода
                        moneyTransfer = Integer.parseInt(line);
                        System.out.println(line);
                        writerArchive.write(line + "|");
                        flag++;
                    }
                    if (flag == 4) {
                        for (int i = 0; i <= informationLines.toArray().length - 1; i++) {//логика вычитывания номеров договоров и сумм переводов !!!нужно доделать!!!
                            if (informationLines.get(i).matches(bankAccountFrom)) {
                                success = true;
                                moneyOnBankAccount = Integer.parseInt(informationLines.get(++i));
                                if (moneyOnBankAccount <= 0 || moneyTransfer <= 0) {//ошибка, если сумма перевода равна ноль или меньше
                                    error = "ошибка во время обработки, неверная сумма перевода";
                                    success = false;
                                }
                                if (moneyOnBankAccount - moneyTransfer < 0) {//ошибка, если денег на перевод больше, чем денег на счете
                                    error = "ошибка во время обработки, неверная сумма перевода";
                                    success = false;
                                }
                                moneyOnBankAccount = moneyOnBankAccount - moneyTransfer;
                                String money = Integer.toString(moneyOnBankAccount);
                                informationLines.set(i, money);
                                for (int j = 0; j < informationLines.toArray().length; j++) {
                                    if (informationLines.get(j).matches(bankAccountTo)) {
                                        moneyOnBankAccount = Integer.parseInt(informationLines.get(++j));
                                        moneyOnBankAccount = moneyOnBankAccount + moneyTransfer;
                                        money = Integer.toString(moneyOnBankAccount);
                                        informationLines.set(j, money);
                                    }
                                }
                                flag = 0;
                                writerOutput.write(date + " | " + "input" + " | " + "перевод с " + bankAccountFrom + " на " + bankAccountTo + " " + moneyTransfer + " | " + (success ? "успешно обработан" : error) + "\n");
                                System.out.println(date + " | " + "input" + " | " + "перевод с " + bankAccountFrom + " на " + bankAccountTo + " " + moneyTransfer + " | " + (success ? "успешно обработан" : error));
                                break;
                            }
                            if (i + 1 == informationLines.toArray().length) {
                                error = "счёт не найден";
                                success = false;
                                writerOutput.write(date + " | " + "input" + " | " + "перевод с " + bankAccountFrom + " на " + bankAccountTo + " " + moneyTransfer + " | " + error + "\n");
                                System.out.println(date + " | " + "input" + " | " + "перевод с " + bankAccountFrom + " на " + bankAccountTo + " " + moneyTransfer + " | " + error);
                            }
                        }
                }


                }
            }

            System.out.println(informationLines);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
