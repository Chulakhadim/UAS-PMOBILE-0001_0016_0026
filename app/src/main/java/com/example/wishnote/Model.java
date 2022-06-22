package com.example.wishnote;

public class Model {
    String name, price, descr;

    public Model(String name, String price, String descr) {
        this.name = name;
        this.descr = descr;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr){
        this.descr = descr;
    }
}
