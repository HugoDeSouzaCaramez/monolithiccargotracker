package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// DOMAIN (Modelo de Domínio)
/**
 * Representação do modelo de domínio da Quantidade Reservada para uma nova
 * Carga.
 * Contém a Quantidade Reservada da Carga
 */
@Embeddable
public class BookingAmount {

    @Column(name = "booking_amount", unique = true, updatable = false)
    private Integer bookingAmount;

    public BookingAmount() {
    }

    public BookingAmount(Integer bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public void setBookingAmount(Integer bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public Integer getBookingAmount() {
        return this.bookingAmount;
    }
}
