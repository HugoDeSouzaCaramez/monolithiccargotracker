package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.entities;

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
    // CONCEITO: Entity como Embedded (depende do Aggregate Root)
    // CONCEITO: Business Identity - UN Location Code
    @Column(name = "origin_id")
    private String unLocCode;

    public Location() {
    }

    // CONCEITO: Constructor com business identity
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
