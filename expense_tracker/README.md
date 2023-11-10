# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTrackerApp
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## Functionality

#### Undo
The undo button allows the user to remove transactions in a stack-like manner: last in, first out, undoing the most recent addition to the expense tracker.

#### Exception Handling

In order to provide better logging and testability, there is an additional exception in regard to input handling and validation the new exception is the InvalidTransactionException. This is thrown by the addTransaction method of the controller class when input validation fails either check: amount and/or category.
