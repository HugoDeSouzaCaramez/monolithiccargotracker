package com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.aggregates;

import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.commands.AddTrackingEventCommand;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.commands.AssignTrackingNumberCommand;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.entities.TrackingActivityEvent;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.entities.TrackingBookingId;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects.TrackingEvent;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects.TrackingEventType;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects.TrackingLocation;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.valueobjects.TrackingVoyageNumber;

import javax.persistence.*;

//Aggregate Root
@Entity
@NamedQueries({
        @NamedQuery(name = "TrackingActivity.findAll", query = "Select t from TrackingActivity t"),
        @NamedQuery(name = "TrackingActivity.findByTrackingNumber", query = "Select t from TrackingActivity t where t.trackingNumber = :trackingNumber"),
        @NamedQuery(name = "TrackingActivity.getAllTrackingNos", query = "Select t.trackingNumber from TrackingActivity t"),
        @NamedQuery(name = "TrackingActivity.findByBookingNumber", query = "Select t from TrackingActivity t where t.bookingId = :bookingId") })
@Table(name = "tracking_activity")
public class TrackingActivity {
    // CONCEITO: Aggregate Root com Event Sourcing style
    // CONCEITO: Command Handler no aggregate

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private TrackingNumber trackingNumber; // Identificador do Agregado
    @Embedded
    private TrackingBookingId bookingId;
    @Embedded
    private TrackingActivityEvent trackingActivityEvent; // Lista de eventos

    public TrackingActivity() {
    }

    /**
     * Cria um novo Número de Rastreamento
     * 
     * CONCEITO: Construction from Command
     * 
     * @param assignTrackingNumberCommand
     */
    public TrackingActivity(AssignTrackingNumberCommand assignTrackingNumberCommand) {
        this.trackingNumber = new TrackingNumber(assignTrackingNumberCommand.getTrackingNumber());
        this.bookingId = new TrackingBookingId((assignTrackingNumberCommand.getBookingId()));
        this.trackingActivityEvent = TrackingActivityEvent.EMPTY_ACTIVITY;
        /*
         * this.trackingActivityEvent.getTrackingEvents().add(
         * new TrackingEvent(
         * new TrackingVoyageNumber(""),
         * new TrackingLocation(""),
         * new TrackingEventType("ROUTED",new Date())));
         */

    }

    /**
     * Adiciona um evento de rastreamento aos Detalhes de Rastreamento
     * 
     * CONCEITO: Behavior method que modifica estado
     * 
     * @param addTrackingEventCommand
     */
    public void addTrackingEvent(AddTrackingEventCommand addTrackingEventCommand) {
        TrackingEvent trackingEvent = new TrackingEvent(
                new TrackingVoyageNumber(addTrackingEventCommand.getVoyageNumber()),
                new TrackingLocation(addTrackingEventCommand.getLocation()),
                new TrackingEventType(addTrackingEventCommand.getEventType(), addTrackingEventCommand.getEventTime()));
        this.trackingActivityEvent.getTrackingEvents().add(trackingEvent);
    }

    public TrackingNumber getTrackingNumber() {
        return this.trackingNumber;
    }

    public TrackingBookingId getBookingId() {
        return this.bookingId;
    }

    public TrackingActivityEvent getTrackingActivityEvents() {
        return this.trackingActivityEvent;
    }

}
