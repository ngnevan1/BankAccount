package entity;

import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void setUp() {
        this.account = new BankAccount();
    }

    @Test
    void givenDepositAmountLessThanZero_whenDeposit_throwInvalidAmountException() {
        BigDecimal lessThanZero = new BigDecimal(-1);
        Exception exception = assertThrows(InvalidAmountException.class, () -> account.deposit(lessThanZero));

        String expectedMsg = "Please enter a valid deposit amount!";
        String actualMsg = exception.getMessage();

        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    void givenDepositAmountZero_whenDeposit_throwInvalidAmountException() {
        Exception exception = assertThrows(InvalidAmountException.class, () -> account.deposit(BigDecimal.ZERO));

        String expectedMsg = "Please enter a valid deposit amount!";
        String actualMsg = exception.getMessage();

        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    void givenDepositAmountGreaterThanZero_whenDeposit_updateBalance() throws InvalidAmountException {
        BigDecimal validAmount = BigDecimal.ONE;
        account.deposit(validAmount);
        assertEquals(account.getBalance(), validAmount);

        Transaction transaction = account.getTransactions().get(0);
        assertEquals(transaction.getAmount(), validAmount);
        assertEquals(transaction.getBalance(), validAmount);
    }

    @Test
    void givenWithdrawalAmountLessThanZero_whenWithdraw_throwInvalidAmountException() {
        BigDecimal lessThanZero = new BigDecimal(-1);
        Exception exception = assertThrows(InvalidAmountException.class, () -> account.withdraw(lessThanZero));

        String expectedMsg = "Please enter a valid withdrawal amount!";
        String actualMsg = exception.getMessage();

        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    void givenWithdrawalAmountZero_whenWithdraw_throwInvalidAmountException() {
        Exception exception = assertThrows(InvalidAmountException.class, () -> account.withdraw(BigDecimal.ZERO));

        String expectedMsg = "Please enter a valid withdrawal amount!";
        String actualMsg = exception.getMessage();

        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    void givenWithdrawalAmountLessThanBalance_whenWithdraw_throwInsufficientBalanceException() throws InvalidAmountException {
        BigDecimal validAmount = BigDecimal.ONE;
        account.deposit(validAmount);

        Exception exception = assertThrows(InsufficientBalanceException.class, () -> account.withdraw(BigDecimal.TEN));

        String expectedMsg = "You have insufficient funds to withdraw $10.00 from your account. Your account balance is $1.00";
        String actualMsg = exception.getMessage();

        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    void givenWithdrawalAmountGreaterThanZero_whenWithdraw_updateBalance() throws InvalidAmountException, InsufficientBalanceException {
        BigDecimal validAmount = BigDecimal.ONE;
        account.deposit(validAmount);
        account.withdraw(validAmount);
        assertEquals(account.getBalance(), BigDecimal.ZERO);

        Transaction transaction = account.getTransactions().get(1);
        assertEquals(transaction.getAmount(), validAmount.negate());
        assertEquals(transaction.getBalance(), BigDecimal.ZERO);
    }
}
