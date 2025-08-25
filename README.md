# Bank Transfer Processing System

A Java Core application for processing bank transfer transactions from text files with comprehensive validation and reporting.

## Features

- **File Processing**: Parses `.txt` files from the `input` directory
- **Transaction Validation**: Validates account numbers (XXXXX-XXXXX format) and positive amounts  
- **Balance Management**: Updates account balances after successful transfers
- **Comprehensive Reporting**: Logs all operations (both successful and failed) with detailed error messages
- **Automatic Archiving**: Moves processed files to `archive` directory
- **User-Friendly Interface**: Console-based menu system

## Project Structure
  src/
-├── model/
-│ ├── BankAccount.java # Account data model
-│ └── Transaction.java # Transaction data model
-├── repository/
-│ ├── FileRepository.java # File operations
-│ └── AccountRepository.java # Account data persistence
-├── service/
-│ ├── TransactionService.java # Business logic for transactions
-│ └── FileParserService.java # File parsing logic
-├── exception/
-│ └── TransactionException.java # Custom exceptions
-├── util/
-│ └── ValidationUtil.java # Validation utilities
-└── Main.java # Application entry point
