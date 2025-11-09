package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import java.util.Objects;

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
    private final Integer bookingAmount; // Campo final

    public BookingAmount() {
        this.bookingAmount = null; // Para JPA
    }

    public BookingAmount(Integer bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public Integer getBookingAmount() {
        return this.bookingAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BookingAmount))
            return false;
        BookingAmount that = (BookingAmount) o;
        return Objects.equals(bookingAmount, that.bookingAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingAmount);
    }
}
