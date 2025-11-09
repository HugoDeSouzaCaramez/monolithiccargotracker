package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Identificador do Agregado para o Agregado Carga
 */
@Embeddable
public class TrackingBookingId implements Serializable {
    @Column(name = "booking_id")
    private final String bookingId; // Campo final

    public TrackingBookingId() {
        this.bookingId = null;
    }

    public TrackingBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return this.bookingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackingBookingId)) return false;
        TrackingBookingId that = (TrackingBookingId) o;
        return Objects.equals(bookingId, that.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}