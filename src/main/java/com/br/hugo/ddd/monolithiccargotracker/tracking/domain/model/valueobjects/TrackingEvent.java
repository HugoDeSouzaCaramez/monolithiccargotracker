package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.*;

/**
 * Detalhes do Evento de Rastreamento
 */
@Entity
@Table(name = "tracking_handling_events")
public class TrackingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private final TrackingVoyageNumber trackingVoyageNumber; // Campo final
    
    @Embedded
    private final TrackingLocation trackingLocation; // Campo final
    
    @Embedded
    private final TrackingEventType trackingEventType; // Campo final

    public TrackingEvent() {
        this.trackingVoyageNumber = null;
        this.trackingLocation = null;
        this.trackingEventType = null;
    }

    public TrackingEvent(
            TrackingVoyageNumber trackingVoyageNumber,
            TrackingLocation trackingLocation,
            TrackingEventType trackingEventType) {
        this.trackingVoyageNumber = trackingVoyageNumber;
        this.trackingLocation = trackingLocation;
        this.trackingEventType = trackingEventType;
    }

    // Apenas getters
    public TrackingVoyageNumber getTrackingVoyageNumber() {
        return this.trackingVoyageNumber;
    }

    public TrackingLocation getTrackingLocation() {
        return this.trackingLocation;
    }

    public TrackingEventType getTrackingEventType() {
        return this.trackingEventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackingEvent)) return false;
        TrackingEvent that = (TrackingEvent) o;
        return Objects.equals(trackingVoyageNumber, that.trackingVoyageNumber) &&
               Objects.equals(trackingLocation, that.trackingLocation) &&
               Objects.equals(trackingEventType, that.trackingEventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingVoyageNumber, trackingLocation, trackingEventType);
    }
}