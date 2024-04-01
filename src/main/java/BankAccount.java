public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
            return false;
        }
    }

    public boolean transfer(BankAccount targetAccount, double amount) {
        if (this == targetAccount) {
            System.out.println("Cannot transfer to the same account.");
            return false;
        }

        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    public void calculateInterest(double annualRate) {
        if (balance > 0) {
            double interest = balance * (annualRate / 100);
            balance += interest;
        } else {
            System.out.println("No interest is applied to non-positive balances.");
        }
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Setter for balance if needed, though typically not recommended to expose directly
    // public void setBalance(double balance) {
    //     this.balance = balance;
    // }
}