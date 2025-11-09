package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// DOMAIN (Modelo de Domínio)
/**
 * Classe Localização representada por um código único de localização UN de 5
 * dígitos
 * 
 * (Entity)
 */
@Embeddable
public class Location {
    @Column(name = "origin_id")
    private final String unLocCode; // Campo final

    public Location() {
        this.unLocCode = null; // Para JPA
    }

    public Location(String unLocCode) {
        this.unLocCode = unLocCode;
    }

    public String getUnLocCode() {
        return this.unLocCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Location))
            return false;
        Location location = (Location) o;
        return Objects.equals(unLocCode, location.unLocCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unLocCode);
    }
}
