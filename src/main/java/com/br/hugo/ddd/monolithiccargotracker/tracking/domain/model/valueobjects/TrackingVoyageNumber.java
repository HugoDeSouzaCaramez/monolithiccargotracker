package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrackingVoyageNumber {
    @Column(name = "voyage_number")
    private final String voyageNumber; // Campo final

    public TrackingVoyageNumber() {
        this.voyageNumber = null;
    }

    public TrackingVoyageNumber(String voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    public String getVoyageNumber() {
        return this.voyageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TrackingVoyageNumber))
            return false;
        TrackingVoyageNumber that = (TrackingVoyageNumber) o;
        return Objects.equals(voyageNumber, that.voyageNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyageNumber);
    }
}
