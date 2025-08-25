# Bank Transfer Processing System

A Java Core application for processing bank transfer transactions from text files with comprehensive validation and reporting.

## Features

- **File Processing**: Parses `.txt` files from the `input/` directory
- **Transaction Validation**: Validates account numbers (XXXXX-XXXXX format) and positive amounts  
- **Balance Management**: Updates account balances after successful transfers
- **Comprehensive Reporting**: Logs all operations with detailed error messages
- **Automatic Archiving**: Moves processed files to `archive/` directory
- **User-Friendly Interface**: Console-based menu system

## Project Structure

```
src/
├── model/
│   ├── BankAccount.java
│   └── Transaction.java
├── repository/
│   ├── FileRepository.java
│   └── AccountRepository.java
├── service/
│   ├── TransactionService.java
│   └── FileParserService.java
├── exception/
│   └── TransactionException.java
├── util/
│   └── ValidationUtil.java
└── Main.java
```

## Input File Format

Files must be `.txt` format with required fields:
```
XXXXX-XXXXX                      # Source account
XXXXX-XXXXX                      # Destination account  
100                              # Transfer amount
2024-01-15                       # Transaction date
```

## Usage

1. Place `.txt` files in `input/` directory
2. Run the application
3. Choose operation:
   - `1` - Process files from input
   - `2` - View transaction report

## Example Output

```
2024-01-15 14:30:25 | file.txt | перевод с 12345-67890 на 98765-43210 100 | успешно обработан
2024-01-15 14:30:25 | file.txt | перевод с 12345-67890 на 98765-43210 -50 | ошибка: неверная сумма
```

## Error Handling

- ❌ Invalid account numbers
- ❌ Negative amounts  
- ❌ Insufficient balance
- ❌ Missing fields
- ❌ Non-existent accounts

## Technical Requirements

- Java 8+
- Proper file system permissions
- Text files in specified format
