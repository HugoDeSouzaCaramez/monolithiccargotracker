package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// DOMAIN (Modelo de Domínio)
@Embeddable
public class LastCargoHandledEvent {
    @Column(name = "last_handling_event_id")
    private final Integer handlingEventId; // Campo final

    @Column(name = "last_handling_event_type")
    private final String handlingEventType; // Campo final

    @Column(name = "last_handling_event_voyage")
    private final String handlingEventVoyage; // Campo final

    @Column(name = "last_handling_event_location")
    private final String handlingEventLocation; // Campo final

    public static final LastCargoHandledEvent EMPTY = new LastCargoHandledEvent();

    public LastCargoHandledEvent() {
        this.handlingEventId = null;
        this.handlingEventType = null;
        this.handlingEventVoyage = null;
        this.handlingEventLocation = null;
    }

    public LastCargoHandledEvent(Integer handlingEventId, String handlingEventType,
            String handlingEventVoyage, String handlingEventLocation) {
        this.handlingEventId = handlingEventId;
        this.handlingEventType = handlingEventType;
        this.handlingEventVoyage = handlingEventVoyage;
        this.handlingEventLocation = handlingEventLocation;
    }

    public String getHandlingEventType() {
        return this.handlingEventType;
    }

    public String getHandlingEventVoyage() {
        return this.handlingEventVoyage;
    }

    public Integer getHandlingEventId() {
        return this.handlingEventId;
    }

    public String getHandlingEventLocation() {
        return this.handlingEventLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LastCargoHandledEvent))
            return false;
        LastCargoHandledEvent that = (LastCargoHandledEvent) o;
        return Objects.equals(handlingEventId, that.handlingEventId) &&
                Objects.equals(handlingEventType, that.handlingEventType) &&
                Objects.equals(handlingEventVoyage, that.handlingEventVoyage) &&
                Objects.equals(handlingEventLocation, that.handlingEventLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handlingEventId, handlingEventType, handlingEventVoyage, handlingEventLocation);
    }
}
