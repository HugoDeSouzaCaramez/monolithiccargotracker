package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class TrackingActivityEvent {

    public static final TrackingActivityEvent EMPTY_ACTIVITY = new TrackingActivityEvent();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tracking_id")
    private List<TrackingEvent> trackingEvents = new ArrayList<TrackingEvent>();

    public TrackingActivityEvent() {
        // Nada para Inicializar
    }

    public TrackingActivityEvent(List<TrackingEvent> trackingEvents) {
        this.trackingEvents = trackingEvents;
    }

    public List<TrackingEvent> getTrackingEvents() {
        return trackingEvents;
    }

}
