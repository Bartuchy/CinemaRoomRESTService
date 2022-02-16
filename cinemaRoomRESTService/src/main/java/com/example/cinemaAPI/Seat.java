package com.example.cinemaAPI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {
    private final int row;
    private final int column;
    private int price;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    @JsonCreator
    public Seat(
            @JsonProperty("row") int row,
            @JsonProperty("column") int column) {
        this.row = row;
        this.column = column;
    }



    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }
}
