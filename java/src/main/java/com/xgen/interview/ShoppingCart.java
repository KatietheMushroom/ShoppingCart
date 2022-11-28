package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public class ShoppingCart implements IShoppingCart {
    HashMap<String, Integer> contents = new HashMap<>();
    Pricer pricer;
    ArrayList<String> orderAdded = new ArrayList<>();
    String receiptType;

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
        this.receiptType = "";
    }

    public ShoppingCart(Pricer pricer, String receiptType) {
        this.pricer = pricer;
        this.receiptType = receiptType;
    }

    public void addItem(String itemType, int number) {
        if (!contents.containsKey(itemType)) {
            contents.put(itemType, number);
        } else {
            int existing = contents.get(itemType);
            contents.put(itemType, existing + number);
        }
        if(!orderAdded.contains(itemType)){
            orderAdded.add(itemType);
        }
    }

    public void printReceipt() {

        int total = 0;
        
        for(String itemType : orderAdded){
            Integer price = pricer.getPrice(itemType) * contents.get(itemType);
            Float priceFloat = new Float(new Float(price) / 100);
            String priceString = String.format("€%.2f", priceFloat);

            total += price;

            // A bit inefficient as it checks for each item
            switch(receiptType){
                case "Price First":
                    System.out.println(priceString + " - " + itemType + " - " + contents.get(itemType));    
                    break;
                default:
                    System.out.println(itemType + " - " + contents.get(itemType) + " - " + priceString);
            }      
        }

        Float totalFloat = (float) (total / 100);
        String totalString = String.format("€%.2f", totalFloat);
        System.out.println("Total: " + totalString);
    }
}
