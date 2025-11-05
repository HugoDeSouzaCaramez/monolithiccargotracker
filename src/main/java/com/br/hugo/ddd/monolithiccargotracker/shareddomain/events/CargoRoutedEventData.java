package com.br.hugo.ddd.monolithiccargotracker.shareddomain.events;

/**
 * Dados do Evento para o Evento de Carga Roteada
 */
public class CargoRoutedEventData {

    private String bookingId;

    public CargoRoutedEventData() {
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return this.bookingId;
    }
}
