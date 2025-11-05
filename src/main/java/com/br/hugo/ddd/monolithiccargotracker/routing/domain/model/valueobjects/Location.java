package com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe Localização representada por um código de localização UN único de 5
 * dígitos
 */
@Embeddable
public class Location {

    @Column(name = "arrival_location_id")
    private String unLocCode;

    public Location() {
    }

    public Location(String unLocCode) {
        this.unLocCode = unLocCode;
    }

    public void setUnLocCode(String unLocCode) {
        this.unLocCode = unLocCode;
    }

    public String getUnLocCode() {
        return this.unLocCode;
    }
}
