import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;
    private BankAccount targetAccount;

    @BeforeEach
    void setUp() {
        account = new BankAccount("12345", 1000.00);
        targetAccount = new BankAccount("67890", 500.00);
    }

    @Test
    @DisplayName("Depositing a positive amount updates the balance correctly")
    void deposit_PositiveAmount_UpdatesBalanceCorrectly() {
        double initialBalance = account.getBalance();
        double amountToDeposit = 500.00;

        account.deposit(amountToDeposit);
        double updatedBalance = account.getBalance();

        assertEquals(initialBalance + amountToDeposit, updatedBalance,
                "Balance should be correctly updated after depositing a positive amount.");
    }
    @Test
    @DisplayName("Attempting to deposit a negative amount does not alter the balance")
    void deposit_NegativeAmount_DoesNotAlterBalance() {
        double initialBalance = account.getBalance();
        double amountToDeposit = -500.00;

        account.deposit(amountToDeposit);
        double unchangedBalance = account.getBalance();

        assertEquals(initialBalance, unchangedBalance,
                "Balance should remain unchanged when attempting to deposit a negative amount.");
    }

    @Test
    @DisplayName("Withdrawing an amount less than the balance decreases the balance correctly")
    void withdraw_AmountLessThanBalance_DecreasesBalanceCorrectly() {
        double initialBalance = account.getBalance();
        double amountToWithdraw = 200.00;

        boolean result = account.withdraw(amountToWithdraw);
        double updatedBalance = account.getBalance();

        assertTrue(result, "Withdrawal should be successful for amounts less than the current balance.");
        assertEquals(initialBalance - amountToWithdraw, updatedBalance,
                "Balance should be correctly decreased after a successful withdrawal.");
    }

    @Test
    @DisplayName("Attempting to withdraw more than the balance should fail")
    void withdraw_AmountMoreThanBalance_ShouldFail() {
        double initialBalance = account.getBalance();
        double amountToWithdraw = 1200.00;

        boolean result = account.withdraw(amountToWithdraw);
        double unchangedBalance = account.getBalance();

        assertFalse(result, "Withdrawal should fail for amounts more than the current balance.");
        assertEquals(initialBalance, unchangedBalance,
                "Balance should remain unchanged when attempting to withdraw more than the current balance.");
    }

    @Test
    @DisplayName("Attempting to withdraw a negative amount should fail")
    void withdraw_NegativeAmount_ShouldFail() {
        double initialBalance = account.getBalance();
        double amountToWithdraw = -500.00;

        boolean result = account.withdraw(amountToWithdraw);
        double unchangedBalance = account.getBalance();

        assertFalse(result, "Withdrawal should fail for negative amounts.");
        assertEquals(initialBalance, unchangedBalance,
                "Balance should remain unchanged when attempting to withdraw a negative amount.");
    }

    @Test
    @DisplayName("Transferring to another account decreases balance and increases target balance")
    void transfer_ToAnotherAccount_DecreasesBalanceAndIncreasesTargetBalance() {
        double initialSourceBalance = account.getBalance();
        double initialTargetBalance = targetAccount.getBalance();
        double transferAmount = 200.00;

        boolean result = account.transfer(targetAccount, transferAmount);

        assertTrue(result, "Transfer should be successful.");
        assertEquals(initialSourceBalance - transferAmount, account.getBalance(),
                "Source account balance should decrease by the transfer amount.");
        assertEquals(initialTargetBalance + transferAmount, targetAccount.getBalance(),
                "Target account balance should increase by the transfer amount.");
    }

    @Test
    @DisplayName("Attempting to transfer more than the account balance should fail")
    void transfer_MoreThanBalance_ShouldFail() {
        double initialSourceBalance = account.getBalance();
        double transferAmount = 1200.00;

        boolean result = account.transfer(targetAccount, transferAmount);

        assertFalse(result, "Transfer should fail due to insufficient funds.");
        assertEquals(initialSourceBalance, account.getBalance(),
                "Source account balance should remain unchanged.");
    }

    @Test
    @DisplayName("Attempting to transfer a negative amount should fail")
    void transfer_NegativeAmount_ShouldFail() {
        double initialSourceBalance = account.getBalance();
        double transferAmount = -100.00;

        boolean result = account.transfer(targetAccount, transferAmount);

        assertFalse(result, "Transfer should fail due to negative transfer amount.");
        assertEquals(initialSourceBalance, account.getBalance(),
                "Source account balance should remain unchanged.");
    }

    @Test
    @DisplayName("Calculating interest adds correct amount to balance")
    void calculateInterest_AddsCorrectAmountToBalance() {
        double annualRate = 5.0;
        double initialBalance = account.getBalance();
        double expectedInterest = initialBalance * (annualRate / 100);

        account.calculateInterest(annualRate);

        assertEquals(initialBalance + expectedInterest, account.getBalance(),
                "Interest should be correctly calculated and added to the balance.");
    }

    @Test
    @DisplayName("Calculating interest with zero balance does not alter balance")
    void calculateInterest_WithZeroBalance_DoesNotAlterBalance() {
        BankAccount newAccount = new BankAccount("11111", 0);
        double annualRate = 5.0;

        newAccount.calculateInterest(annualRate);

        assertEquals(0, newAccount.getBalance(),
                "Interest calculation should not alter balance if it is zero.");
    }
}