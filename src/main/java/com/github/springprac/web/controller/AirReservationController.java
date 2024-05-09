package com.github.springprac.web.controller;


import com.github.springprac.service.AirReservationService;
import com.github.springprac.service.exceptions.InvalidValueException;
import com.github.springprac.service.exceptions.NotAcceptException;
import com.github.springprac.service.exceptions.NotFoundException;
import com.github.springprac.web.dto.airline.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
@RequiredArgsConstructor
@Slf4j
public class AirReservationController {

    private final AirReservationService airReservationService;

    @GetMapping("/tickets")
    public TicketResponse findAirlineTickets(@RequestParam("user-Id") Integer userId,
                                             @RequestParam("airline-ticket-type") String ticketType ){
        List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId, ticketType);
        return new TicketResponse(tickets);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResult makeReservation(@RequestBody ReservationRequest reservationRequest){
        return airReservationService.makeReservation(reservationRequest);

    }

    @GetMapping("/users-sum-price")
    public Double findUserFlightSumPrice(
            @RequestParam("user-id")Integer userId
    ){
        Double sum = airReservationService.findUserFlightSumPrice(userId);
        return sum;
    }
}