package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class TrackingEventType {
    @Column(name = "event_type")
    private final String eventType; // Campo final

    @Column(name = "event_time")
    @Temporal(TemporalType.DATE)
    private final Date eventTime; // Campo final

    public TrackingEventType() {
        this.eventType = null;
        this.eventTime = null;
    }

    public TrackingEventType(String eventType, Date eventTime) {
        this.eventType = eventType;
        this.eventTime = eventTime != null ? new Date(eventTime.getTime()) : null;
    }

    public String getEventType() {
        return this.eventType;
    }

    public Date getEventTime() {
        return eventTime != null ? new Date(eventTime.getTime()) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TrackingEventType))
            return false;
        TrackingEventType that = (TrackingEventType) o;
        return Objects.equals(eventType, that.eventType) &&
                Objects.equals(eventTime, that.eventTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, eventTime);
    }
}
