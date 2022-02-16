package com.example.cinemaAPI;

public class Statistic {
    public int current_income;
    public int number_of_available_seats;
    public int number_of_purchased_tickets;

    public Statistic(int number_of_available_seats) {
        this.current_income = 0;
        this.number_of_available_seats = number_of_available_seats;
        this.number_of_purchased_tickets = 0;
    }

    public void updateStatistics(int action, Reservation reservation){
        switch (action) {
            case 0:
                decreaseStatistic(reservation);
                break;
            case 1:
                increaseStatistic(reservation);
                break;
        }
    }

    private void increaseStatistic(Reservation reservation) {
        current_income += reservation.getTicket().getPrice();
        number_of_purchased_tickets++;
        number_of_available_seats--;
    }

    private void decreaseStatistic(Reservation reservation) {
        current_income -= reservation.getTicket().getPrice();
        number_of_purchased_tickets--;
        number_of_available_seats++;

    }
}
