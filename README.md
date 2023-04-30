# BankAccount
BankAccount is a simple Java command line interface application that handles operations on bank accounts and currently supports three features:
- depositing an amount
- withdrawing an amount
- printing account statement


## Design
The application consists of 3 packages.
### Client
- Consists of the "front-end" of the application that the user interacts with
### Entity
- Contains 2 Java classes modelling the objects that are present in the application
- #### BankAccount
    - BankAccount is created at the start of every session 
    - It stores the Balance and List of Transactions performed
- #### Transaction
  - Transaction is an immutable class that is created for every deposit and withdrawal
  - It stores the Date, Amount and Balance after every transaction is done
### Exception
- Consists of 2 exceptions
  - InsufficientBalanceException - Thrown when user wants to withdraw an amount greater than their account balance
  - InvalidAmountException - Thrown when user wants to deposit/withdraw an amount that is less than or equal to 0

## Assumptions
- The system does not persist user data beyond the user's session. Thus, each time the app
    is started, a new bank account is created for the user with balance 0.
- A user is not able to withdraw an amount strictly greater than their current account
    balance
- A user is not able to deposit an amount less than or equal to 0

## Instructions
Requires
- Java (JDK 11)
- Gradle
### Running the app
```bash
gradle --console plain run
```

### Testing the app
```bash
gradle test
```

