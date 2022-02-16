package com.example.cinemaAPI;

import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;

import java.util.LinkedList;
import java.util.List;

public class Room {
    private final int total_rows;
    private final int total_columns;
    private List<Seat> available_seats;

    public Room(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        initializeAvailableSeats(total_rows, total_columns);
    }

    private void initializeAvailableSeats(int rows, int columns) {
        this.available_seats = new LinkedList<>();

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                if (i <= 4) {
                    this.available_seats.add(new Seat(i, j, 10));
                } else {
                    this.available_seats.add(new Seat(i, j, 8));
                }
            }
        }
    }

    public Pair<Seat, Integer> findSeat(Seat seatToPurchase) {
        int index = -1;
        Seat purchased_seat;
        for (Seat seat: getAvailable_seats()) {
            if (seat.getRow() == seatToPurchase.getRow() && seat.getColumn() == seatToPurchase.getColumn()) {
                index = getAvailable_seats().indexOf(seat);
                purchased_seat = seat;
                return Pairs.from(purchased_seat, index);
            }
        }
        return Pairs.from(null, index);
    }



    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }

}

