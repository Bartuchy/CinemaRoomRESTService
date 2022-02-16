package com.example.cinemaAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class RoomController {
    private final int REFUND = 0;
    private final int RESERVE = 1;

    private final int TOTAL_ROWS = 9;
    private final int TOTAL_COLUMNS = 9;

    private final Room room = new Room(TOTAL_ROWS, TOTAL_COLUMNS);
    private final Statistic statistic = new Statistic(TOTAL_COLUMNS * TOTAL_COLUMNS);
    private final List<Reservation> reservations = new LinkedList<>();

    @GetMapping("/seats")
    public Room getRoom() {
        return room;
    }

    @PostMapping("/stats")
    public Object statistics(@RequestParam(required = false) String password) {
        if (password != null) {
            if (password.equals("super_secret")) {
                return statistic;
            }
        }
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/purchase")
    public Object bookSeat(@RequestBody Seat seat_to_book) {
        boolean isPurchased = reservations
                .stream()
                .anyMatch(reservation -> reservation.getTicket().getRow() == seat_to_book.getRow()
                        && reservation.getTicket().getColumn() == seat_to_book.getColumn());

        if (isPurchased) {
            return new ResponseEntity<>(Map.of("error", "The ticket has been already booked!"), HttpStatus.BAD_REQUEST);
        } else {
            return bookSeat(room.findSeat(seat_to_book).getFirst(), room.findSeat(seat_to_book).getSecond());
        }
    }

    private Object bookSeat(Seat seat_to_book, int index) {
        if (index != -1) {
            room.getAvailable_seats().remove(index);
            Reservation reservation = new Reservation(seat_to_book);
            reservations.add(reservation);

            statistic.updateStatistics(RESERVE, reservation);
            return reservation;
        }
        return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refund")
    public Object returnReservation(@RequestBody Token token) {
        for (Reservation reservation: reservations) {
            if (reservation.getToken().toString().equals(token.getToken())) {
                reservations.remove(reservation);
                room.getAvailable_seats().add(reservation.getTicket());

                statistic.updateStatistics(REFUND, reservation);
                return Map.of("returned_ticket", reservation.getTicket());
            }
        }
        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }
}
