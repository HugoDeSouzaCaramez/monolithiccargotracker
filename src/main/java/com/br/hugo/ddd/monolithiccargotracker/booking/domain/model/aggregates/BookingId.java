package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Identificador de Agregado para o Agregado Carga
 */
@Embeddable
public class BookingId implements Serializable {

    @Column(name = "booking_id")
    private String bookingId;

    public BookingId() {
    }

    public BookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return this.bookingId;
    }
}
