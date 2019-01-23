package com.example.wb.my.Model;

public class product {
    public String product_name;
    public String product_image;
    public String product_weight;
    public long product_ID;
    public int product_quantity;
    public int product_price;
    public int id;
    public product( String product_name, String product_image, String product_weight, int product_quantity, int product_price, int id) {

        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_weight = product_weight;
        this.product_price = product_price;
        this.product_image = product_image;
        this.id=id;
    }
}