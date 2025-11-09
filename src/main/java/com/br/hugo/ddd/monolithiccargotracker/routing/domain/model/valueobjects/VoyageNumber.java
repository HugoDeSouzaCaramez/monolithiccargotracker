package com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VoyageNumber {
    @Column(name = "voyage_number")
    private final String voyageNumber; // Campo final

    public VoyageNumber() {
        this.voyageNumber = null;
    }

    public VoyageNumber(String voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    public String getVoyageNumber() {
        return this.voyageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof VoyageNumber))
            return false;
        VoyageNumber that = (VoyageNumber) o;
        return Objects.equals(voyageNumber, that.voyageNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyageNumber);
    }
}
