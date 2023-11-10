// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

import javax.swing.*;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }


    public void checkTransaction(double amount, String category, Transaction transaction) {
	assertEquals(amount, transaction.getAmount(), 0.01);
        assertEquals(category, transaction.getCategory());
        String transactionDateString = transaction.getTimestamp();
        Date transactionDate = null;
        try {
            transactionDate = Transaction.dateFormatter.parse(transactionDateString);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
            transactionDate = null;
        }
        Date nowDate = new Date();
        assertNotNull(transactionDate);
        assertNotNull(nowDate);
        // They may differ by 60 ms
        assertTrue(nowDate.getTime() - transactionDate.getTime() < 60000);
    }


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
	double amount = 50.0;
	String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Post-condition: List of transactions contains only
	//                 the added transaction	
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
	Transaction firstTransaction = model.getTransactions().get(0);
	checkTransaction(amount, category, firstTransaction);
	
	// Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);
    }


    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
	double amount = 50.0;
	String category = "food";
        Transaction addedTransaction = new Transaction(amount, category);
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains only
	//                the added transaction
        assertEquals(1, model.getTransactions().size());
	Transaction firstTransaction = model.getTransactions().get(0);
	checkTransaction(amount, category, firstTransaction);

	assertEquals(amount, getTotalCost(), 0.01);
	
	// Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }


    @Test
    public void testAddTransactionView() {
        // - Steps: Add a transaction with amount 50.00 and category ”food”
        // - Expected Output: Transaction is added to the table, Total Cost is updated
        assertEquals(0, model.getTransactions().size());
    
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Post-condition: View table contains only the added transaction
        //                 Table has two rows: transaction and total
        //                 Transaction row has amount and category that match the model
        JTable table = view.getTransactionsTable();
        assertEquals(2, table.getRowCount());
    
        // Check the contents of the list
        Object viewCell = table.getValueAt(0, 1);
        float cellAmount = Float.parseFloat(viewCell.toString());
        viewCell = table.getValueAt(0,2);
        String cellCategory = viewCell.toString();

        // Check that amount and category in view is the same as the model
        Transaction firstTransaction = model.getTransactions().get(0);
        checkTransaction(cellAmount, cellCategory, firstTransaction);

        // check total cost in view is same as getTotalCost method
        viewCell = table.getValueAt(1,3);
        cellAmount = Float.parseFloat(viewCell.toString());
        assertEquals(cellAmount, getTotalCost(), 0.01);
    }


    @Test
    public void invalidInputHandling() { // TODO
        // - Steps: Attempt to add a transaction with an invalid amount or category
        // - Expected Output: Error messages are displayed, transactions and Total Cost remain unchanged
        assertEquals(0, model.getTransactions().size());
    
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
        int initial_size = model.getTransactions().size();

        String invalid_in = "mcdonald's";
        assertTrue(!controller.addTransaction(amount, invalid_in));
    
        // Post-condition: List of transactions is the same size as before the invalid input
        assertEquals(initial_size, model.getTransactions().size());
    	
        // Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);

        // TODO: check that pop-up dialogue shows up
    }


    @Test
    public void filterByAmount() { // TODO
        // - Steps: Add multiple transactions with different amounts, apply amount filter
        // - Expected Output: Only transactions matching the amount are returned (and will be highlighted)
        assertEquals(0, model.getTransactions().size());
    
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // ...
    }


    @Test
    public void filterByCategory() { // TODO
        // - Steps: Add multiple transactions with different categories, apply category filter
        // - Expected Output: Only transactions matching the category are returned (and will be highlighted)
        assertEquals(0, model.getTransactions().size());
    
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // ...
    }


    @Test
    public void undoDisallowed() { // TODO
        // - Steps: Attempt to undo when the transactions list is empty
        // - Expected Output: Either UI widget is disabled or an error code (exception) is returned (thrown).
        assertEquals(0, model.getTransactions().size());
    
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // ...
    }


    @Test
    public void undoAllowed() { // TODO
        // - Steps: Add a transaction, undo the addition
        // - Expected Output: Transaction is removed from the table, Total Cost is updated
        assertEquals(0, model.getTransactions().size());
    
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // ...
    }

}
