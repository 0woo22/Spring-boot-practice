package com.github.springprac.respository.reservations;

public interface ReservationRepository {
    Boolean saveReservation(Reservation reservation);

    Reservation findReservationWithPassengerIdAndAirLineTicketId(Integer userId, Integer airlineTicketId);

    void updateReservationStatus(Integer reservationId, String status);
}