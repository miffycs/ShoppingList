package com.miffycs.shoppinglist;

public class Item {

    public String name;
    public String description;
    public double price;
    public int imageID;


    public Item (String name, double price) {
        this.name = formatName(name);
        this.price = formatPrice(price);
        this.description = "dummy description";
        this.imageID = R.drawable.ic_action_item_icon;
    }

    public Item (String name, double price, String description) {
        this.name = formatName(name);
        this.price = formatPrice(price);
        this.description = description;
        this.imageID = R.drawable.ic_action_item_icon;
    }

    private String formatName(String original) {
        if (original.isEmpty())
            return original;
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }

    private double formatPrice(double original) {
        return Math.round(original * 100.0) / 100.0;
    }

}