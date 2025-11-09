package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrackingNumber {
    @Column(name = "tracking_number")
    private final String trackingNumber; // Campo final

    public TrackingNumber() {
        this.trackingNumber = null;
    }

    public TrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackingNumber)) return false;
        TrackingNumber that = (TrackingNumber) o;
        return Objects.equals(trackingNumber, that.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber);
    }
}