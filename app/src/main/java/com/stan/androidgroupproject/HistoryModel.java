package com.stan.androidgroupproject;

/**
 * Created by stan on 30/12/2017.
 * POJO class for all the history items
 */

public class HistoryModel {

    private int id, price, distance, liter;
    private String date;

    public HistoryModel() {

    }

    public HistoryModel(String date, int price, int distance, int liter) {
        this.date = date;
        this.price = price;
        this.distance = distance;
        this.liter = liter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    int getDistance() {
        return distance;
    }

    void setDistance(int distance) {
        this.distance = distance;
    }

    int getLiter() {
        return liter;
    }

    void setLiter(int liter) {
        this.liter = liter;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }


}
