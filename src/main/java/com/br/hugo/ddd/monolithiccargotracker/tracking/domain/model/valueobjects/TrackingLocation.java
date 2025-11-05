package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe TrackingLocation representada por um código UN de TrackingLocation
 * único de 5 dígitos
 */
@Embeddable
public class TrackingLocation {

    @Column(name = "location_id")
    private String unLocCode;

    public TrackingLocation() {
    }

    public TrackingLocation(String unLocCode) {
        this.unLocCode = unLocCode;
    }

    public void setUnLocCode(String unLocCode) {
        this.unLocCode = unLocCode;
    }

    public String getUnLocCode() {
        return this.unLocCode;
    }
}
