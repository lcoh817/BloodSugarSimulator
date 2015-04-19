package com.lancecohen.bloodsugarsimulator;

/**
 * Created by lancecohen on 16/04/15.
 */
public class Item {

    private String item;

    private int itemID;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Item(String item, int itemID) {
        super();
        this.item = item;
        this.itemID = itemID;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


}
