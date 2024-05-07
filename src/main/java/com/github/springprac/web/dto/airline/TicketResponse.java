package com.github.springprac.web.dto.airline;

import java.util.List;

public class TicketResponse {
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public TicketResponse(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public TicketResponse() {
    }
}
