package MAIN;

import java.time.LocalDate;

public class Transaction {

        private Item item;
        private User borrower;
        private LocalDate borrowDate;
        private LocalDate returnDate;
        private String transactionStatus; // "Pending", "Active", "Completed"

        public Transaction(Item item, User borrower, LocalDate borrowDate, LocalDate returnDate) {
            this.item = item;
            this.borrower = borrower;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
            this.transactionStatus = "Pending";
        }
    }

