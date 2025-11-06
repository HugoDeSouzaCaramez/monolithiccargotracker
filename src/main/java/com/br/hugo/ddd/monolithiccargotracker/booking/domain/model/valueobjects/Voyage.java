package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// DOMAIN (Modelo de Domínio)
@Embeddable
public class Voyage {
    @Column(name = "voyage_number")
    private String voyageNumber;

    public Voyage() {
    }

    public Voyage(String voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    public String getVoyageNumber() {
        return this.voyageNumber;
    }

    public void setVoyageNumber(String voyageNumber) {
        this.voyageNumber = voyageNumber;
    }
}
