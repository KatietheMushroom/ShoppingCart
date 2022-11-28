package com.xgen.interview;

import com.xgen.interview.Pricer;
import com.xgen.interview.ShoppingCart;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ShoppingCartTest {

    // Change these helper functions when making changes to ShoppingCart
    // But need to change the testTotal functions if item price changes.
    // Given more time/larger system I would have a CSV file to keep all the test data, and these tests can just pull data from it.

    public String addOneItem(ShoppingCart sc) {
        sc.addItem("apple", 1);
        return String.format("apple - 1 - €1.00%n");
    }

    public String addTwoItems(ShoppingCart sc) {
        sc.addItem("banana", 2);
        return String.format("banana - 2 - €4.00%n");
    }

    public String addMysteryItem(ShoppingCart sc) {
        sc.addItem("crisps", 2);
        return String.format("crisps - 2 - €0.00%n");
    }

    public String addOneItemPriceFirst(ShoppingCart sc) {
        sc.addItem("apple", 1);
        return String.format("€1.00 - apple - 1%n");
    }

    @Test
    public void canAddAnItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        String expected = addOneItem(sc);
 
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();
        assertTrue("Doesn't contain item after addition", myOut.toString().contains(expected));
    }

    @Test
    public void canAddMoreThanOneItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        String expected = addTwoItems(sc);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();
        assertTrue("Doesn't contain 2 items after addition", myOut.toString().contains(expected));
    }

    @Test
    public void canAddDifferentItems() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        String expected1 = addOneItem(sc);
        String expected2 = addTwoItems(sc);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();

        String result = myOut.toString();
        assertTrue("Doesn't contain item 1", result.contains(expected1));
        assertTrue("Doesn't contain item 2", result.contains(expected2));
    }

    @Test
    public void itemsAreInOrder() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        String expected1 = addOneItem(sc);
        String expected2 = addTwoItems(sc);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();

        String result = myOut.toString();

        // Checks string contains the first item.
        assertTrue("Doesn't contain item 1", result.contains(expected1 + expected2));
    }

    @Test
    public void priceFirstCartWorks(){
        ShoppingCart sc = new ShoppingCart(new Pricer(), "Price First");

        String expected = addOneItemPriceFirst(sc);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();
        assertTrue("Doesn't contain item after addition", myOut.toString().contains(expected));
    }

    @Test
    public void doesntExplodeOnMysteryItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        String expected = addMysteryItem(sc);
        
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();
        assertTrue("Does explode?", myOut.toString().contains(expected));
    }

    @Test
    public void testTotalWhenEmpty(){
        ShoppingCart sc = new ShoppingCart(new Pricer());
 
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        String expectedTotal = "Total: " + "€0.00";
        sc.printReceipt();

        assertTrue("Doesn't contain total", myOut.toString().contains("Total:"));
        assertTrue("Total is inncorrect", myOut.toString().contains(expectedTotal));
    }


    @Test
    public void testTotalWhenOneItem(){
        ShoppingCart sc = new ShoppingCart(new Pricer());

        addOneItem(sc);
 
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        String expectedTotal = "Total: " + "€1.00";
        sc.printReceipt();
        assertTrue("Doesn't contain total", myOut.toString().contains("Total:"));
        assertTrue("Total is inncorrect", myOut.toString().contains(expectedTotal));
    }

    @Test
    public void testTotalWhenTwoSame(){
        ShoppingCart sc = new ShoppingCart(new Pricer());

        addTwoItems(sc);
 
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        String expectedTotal = "Total: " + "€4.00";
        sc.printReceipt();
        assertTrue("Doesn't contain total", myOut.toString().contains("Total:"));
        assertTrue("Total is inncorrect" + myOut.toString(), myOut.toString().contains(expectedTotal));
    }

}


