package com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.dto;

/**
 * Classe de Recurso para a API do Comando de Reserva de Carga
 */
public class RouteCargoResource {

    private String bookingId;

    public RouteCargoResource() {
    }

    public RouteCargoResource(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
