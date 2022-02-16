package com.example.cinemaAPI;

import java.util.UUID;

public class Reservation {
    private final UUID token;
    private final Seat ticket;

    public Reservation(Seat ticket) {
        this.token = UUID.randomUUID();
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public Seat getTicket() {
        return ticket;
    }
}
