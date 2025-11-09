package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe TrackingLocation representada por um código UN de TrackingLocation
 * único de 5 dígitos
 */
@Embeddable
public class TrackingLocation {
    @Column(name = "location_id")
    private final String unLocCode; // Campo final

    public TrackingLocation() {
        this.unLocCode = null;
    }

    public TrackingLocation(String unLocCode) {
        this.unLocCode = unLocCode;
    }

    public String getUnLocCode() {
        return this.unLocCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TrackingLocation))
            return false;
        TrackingLocation that = (TrackingLocation) o;
        return Objects.equals(unLocCode, that.unLocCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unLocCode);
    }
}
