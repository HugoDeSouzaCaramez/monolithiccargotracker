package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// DOMAIN (Modelo de Domínio)
@Embeddable
public class Voyage {
    @Column(name = "voyage_number")
    private final String voyageNumber; // Campo final

    public Voyage() {
        this.voyageNumber = null; // Para JPA
    }

    public Voyage(String voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    public String getVoyageNumber() {
        return this.voyageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Voyage))
            return false;
        Voyage voyage = (Voyage) o;
        return Objects.equals(voyageNumber, voyage.voyageNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyageNumber);
    }
}
