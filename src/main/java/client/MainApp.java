package client;

import entity.BankAccount;
import entity.Transaction;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainApp {
    BankAccount account;

    public MainApp() {
        this.account = new BankAccount();
    }

    public void mainMenu() {
        Scanner sc = new Scanner(System.in);
        String input = "";
        while (!input.equals("q")) {
            System.out.println("[D]eposit");
            System.out.println("[W]ithdraw");
            System.out.println("[P]rint statement");
            System.out.println("[Q]uit");

            input = sc.nextLine();
            input = input.toLowerCase();

            switch (input) {
                case "d":
                    deposit();
                    break;
                case "w":
                    withdraw();
                    break;
                case "p":
                    printStatement();
                    break;
                case "q":
                    System.out.println("Thank you for banking with AwesomeGIC Bank.\n" +
                            "Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid option entered! Please enter a valid option shown in the menu.");
                    break;
            }
        }
    }

    private void deposit() {
        System.out.println("Please enter the amount to deposit:");
        BigDecimal depositAmount = BigDecimal.ZERO;

        do {
            try {
                Scanner sc = new Scanner(System.in);
                depositAmount = sc.nextBigDecimal();
                this.account.deposit(depositAmount);
                String formattedAmount = NumberFormat.getCurrencyInstance(Locale.US).format(depositAmount);
                System.out.println("Thank you. " + formattedAmount + " has been deposited to your account.");
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid amount!");
            } catch (InvalidAmountException ex) {
                System.out.println(ex.getMessage());
            }
        } while (depositAmount.compareTo(BigDecimal.ZERO) < 1);

        System.out.println("Is there anything else you'd like to do?");
    }

    private void withdraw() {
        System.out.println("Please enter the amount to withdraw:");
        BigDecimal withdrawAmount = BigDecimal.ZERO;

        do {
            try {
                Scanner sc = new Scanner(System.in);
                withdrawAmount = sc.nextBigDecimal();
                this.account.withdraw(withdrawAmount);
                String formattedAmount = NumberFormat.getCurrencyInstance(Locale.US).format(withdrawAmount);
                System.out.println("Thank you. " + formattedAmount + " has been withdrawn.");
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid amount!");
            }   catch (InvalidAmountException ex) {
                System.out.println(ex.getMessage());
            }  catch (InsufficientBalanceException ex) {
                System.out.println(ex.getMessage());
                break;
            }
        } while (withdrawAmount.compareTo(BigDecimal.ZERO) < 1);

        System.out.println("Is there anything else you'd like to do?");
    }

    private void printStatement() {
        Scanner sc = new Scanner(System.in);
        List<Transaction> transactionList = account.getTransactions();
        if (transactionList.isEmpty()) {
            System.out.println("There are no transactions for this account");
        } else {
            System.out.printf("%-23s | %-10s | %-10s\n", "Date", "Amount", "Balance");
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMM y hh:mm:ss:a");

            for (Transaction t : transactionList) {
                System.out.printf("%-23s | %-10.2f | %-10.2f\n", t.getDate().format(outputFormat), t.getAmount(), t.getBalance());
            }
            System.out.println("...Enter any key to continue...");
            sc.nextLine();
        }
        System.out.println("Is there anything else you'd like to do?");
    }
}
