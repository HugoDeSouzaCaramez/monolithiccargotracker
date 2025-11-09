package com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe Localização representada por um código UN de localização único de 5
 * dígitos
 */
@Embeddable
public class Location {
    @Column(name = "location")
    private final String unLocCode; // Campo final

    public Location() {
        this.unLocCode = null;
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
