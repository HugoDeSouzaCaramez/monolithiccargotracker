package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe Localização representada por um código único de localização UN de 5
 * dígitos
 */
@Embeddable
public class Location {

    @Column(name = "origin_id")
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
