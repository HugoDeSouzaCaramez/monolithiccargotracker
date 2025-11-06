package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Identificador de Agregado (Aggregate Identifier) para o Agregado Carga
 */
@Embeddable
public class BookingId implements Serializable {
    // CONCEITO: Business Key - Identificador de negócio do Aggregate
    // CONCEITO: Embedded Value - Sem identidade própria

    @Column(name = "booking_id")
    private String bookingId;

    public BookingId() {
    }

    // CONCEITO: Value Object com validação e comportamento
    public BookingId(String bookingId) {
        // Poderia ter validações de formato do ID
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return this.bookingId;
    }
}
