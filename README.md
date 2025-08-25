Bank Transfer Processing System
A Java Core application for processing bank transfer transactions from text files with comprehensive validation and reporting.

Features
File Processing: Parses .txt files from the input/ directory

Transaction Validation: Validates account numbers (XXXXX-XXXXX format) and positive amounts

Balance Management: Updates account balances after successful transfers

Comprehensive Reporting: Logs all operations (both successful and failed) with detailed error messages

Automatic Archiving: Moves processed files to archive/ directory

User-Friendly Interface: Console-based menu system

Project Structure
src/
├── model/
│   ├── BankAccount.java          # Account data model
│   └── Transaction.java          # Transaction data model
├── repository/
│   ├── FileRepository.java       # File operations
│   └── AccountRepository.java    # Account data persistence
├── service/
│   ├── TransactionService.java   # Business logic for transactions
│   └── FileParserService.java    # File parsing logic
├── exception/
│   └── TransactionException.java # Custom exceptions
├── util/
│   └── ValidationUtil.java       # Validation utilities
└── Main.java                     # Application entry point
Input File Format
Processed files must be .txt format and contain the following required fields:

XXXXX-XXXXX                      # Source account number (10 digits)
XXXXX-XXXXX                      # Destination account number (10 digits) 
100                              # Transfer amount (positive integer)
2024-01-15                       # Transaction date (YYYY-MM-DD)

Files may contain additional fields, but only the above data will be processed.

Output Files
Report File (output.txt)

Contains all processed operations with status and timestamps:

2024-01-15 14:30:25 | input.txt | перевод с 12345-67890 на 98765-43210 100 | успешно обработан
2024-01-15 14:30:25 | input.txt | перевод с 12345-67890 на 98765-43210 -50 | ошибка во время обработки, неверная сумма перевода
Account Data File (information.txt)

Stores current account balances:

12345-67890|1500
98765-43210|2500
Archive Files
Processed input files are moved to archive/ directory with timestamps.

Usage
Prepare input files: Place .txt files in the input directory

Run the application: Execute the Main class

Choose operation:

Press 1 - Process files from input directory

Press 2 - View transaction report

Press 3 - View report by dates

Press 4 - View current accounts

Press 5 - Exit

Example Session

1 - Обработка файлов переводов
2 - Просмотр отчета о транзакциях
3 - Просмотр отчета по датам
4 - Просмотр текущих счетов
5 - Выход

> 1

Обработка файла: transfer1.txt
12345-67890
98765-43210
100
2024-01-15

Парсинг произведен!
Файл успешно обработан: transfer1.txt

> 2

=== Список всех переводов ===
==========================================
2024-01-15 14:30:25 | transfer1.txt | перевод с 12345-67890 на 98765-43210 100 | успешно обработан
==========================================

Error Handling

The application handles various error scenarios:

❌ Invalid account number format

❌ Negative or zero transfer amounts

❌ Insufficient account balance

❌ Missing required fields in input files

❌ Non-existent accounts

❌ Invalid file formats

All errors are logged in the report file with descriptive messages.

Technical Requirements

Java 8 or higher

Text files in specified format

Proper file system permissions for read/write operations

Support
For issues related to file processing or transaction errors, check the output.txt report file for detailed error messages and timestamps.
