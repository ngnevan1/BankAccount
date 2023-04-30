package entity;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BankAccount {
    private BigDecimal balance;
    private final List<Transaction> transactions;

    public BankAccount() {
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }
    /**
     * This method is used to deposit amounts into a BankAccount
     * @param amount A BigDecimal representing the deposit amount
     * @exception InvalidAmountException if deposit amount <= 0
     */
    public void deposit(BigDecimal amount) throws InvalidAmountException {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidAmountException("Please enter a valid deposit amount!");
        }
        BigDecimal newBal = this.balance.add(amount);
        Transaction dep = new Transaction(LocalDateTime.now(), amount, newBal);
        this.transactions.add(dep);
        this.balance = newBal;
    }

    /**
     * This method is used to withdraw amounts into a BankAccount
     * @param amount A BigDecimal representing the withdrawal amount
     * @exception InvalidAmountException if withdraw amount <= 0
     * @exception InsufficientBalanceException if BankAccount.balance <= withdraw amount
     */
    public void withdraw(BigDecimal amount) throws InsufficientBalanceException, InvalidAmountException {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new InvalidAmountException("Please enter a valid withdrawal amount!");
        }
        BigDecimal newBal = this.balance.subtract(amount);
        if (newBal.compareTo(BigDecimal.ZERO) == -1) {
            String formattedAmount = NumberFormat.getCurrencyInstance(Locale.US).format(amount);
            String formattedBal = NumberFormat.getCurrencyInstance(Locale.US).format(this.balance);
            throw new InsufficientBalanceException("You have insufficient funds to withdraw " + formattedAmount + " from your account. Your account balance is " + formattedBal + ".");
        }
        Transaction dep = new Transaction(LocalDateTime.now(), amount.negate(), newBal);
        this.transactions.add(dep);
        this.balance = newBal;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
