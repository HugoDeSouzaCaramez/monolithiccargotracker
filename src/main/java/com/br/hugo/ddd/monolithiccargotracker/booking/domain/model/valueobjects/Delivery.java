package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

// DOMAIN (Modelo de Domínio)
/**
 * Classe de Domínio que acompanha o progresso da Carga em relação à
 * Especificação
 * da Rota / Itinerário e Eventos de Manuseio.
 * 
 * Value Object com Domain Rules
 */

@Embeddable
public class Delivery {
    public static final Date ETA_UNKOWN = null;

    // Tipos Enumerados - Status de Roteamento / Status de Transporte da Carga
    @Enumerated(EnumType.STRING)
    @Column(name = "routing_status")
    private final RoutingStatus routingStatus; // Campo final

    @Enumerated(EnumType.STRING)
    @Column(name = "transport_status")
    private final TransportStatus transportStatus; // Campo final

    @Column(name = "last_known_location_id")
    @AttributeOverride(name = "unLocCode", column = @Column(name = "last_known_location_id"))
    private final Location lastKnownLocation; // Campo final

    @Column(name = "current_voyage_number")
    @AttributeOverride(name = "voyageNumber", column = @Column(name = "current_voyage_number"))
    private final Voyage currentVoyage; // Campo final

    @Embedded
    private final LastCargoHandledEvent lastEvent; // Campo final

    // Previsões para a atividade da Carga
    public static final CargoHandlingActivity NO_ACTIVITY = new CargoHandlingActivity();

    @Embedded
    private final CargoHandlingActivity nextExpectedActivity; // Campo final

    public Delivery() {
        this.routingStatus = null;
        this.transportStatus = null;
        this.lastKnownLocation = null;
        this.currentVoyage = null;
        this.lastEvent = null;
        this.nextExpectedActivity = null;
    }

    public Delivery(LastCargoHandledEvent lastEvent, CargoItinerary itinerary,
            RouteSpecification routeSpecification) {
        this.lastEvent = lastEvent;
        this.routingStatus = calculateRoutingStatus(itinerary, routeSpecification);
        this.transportStatus = calculateTransportStatus();
        this.lastKnownLocation = calculateLastKnownLocation();
        this.currentVoyage = calculateCurrentVoyage();
        this.nextExpectedActivity = NO_ACTIVITY;
    }

    /**
     * Cria uma nova captura de estado da entrega para refletir alterações no
     * roteamento
     */
    public Delivery updateOnRouting(RouteSpecification routeSpecification, CargoItinerary itinerary) {
        return new Delivery(this.lastEvent, itinerary, routeSpecification);
    }

    /**
     * Factory method para criar Delivery
     */
    public static Delivery derivedFrom(RouteSpecification routeSpecification,
            CargoItinerary itinerary, LastCargoHandledEvent lastCargoHandledEvent) {
        return new Delivery(lastCargoHandledEvent, itinerary, routeSpecification);
    }

    // Apenas getters
    public RoutingStatus getRoutingStatus() {
        return this.routingStatus;
    }

    public TransportStatus getTransportStatus() {
        return this.transportStatus;
    }

    public Location getLastKnownLocation() {
        return this.lastKnownLocation;
    }

    public Voyage getCurrentVoyage() {
        return this.currentVoyage;
    }

    // Métodos privados de cálculo permanecem iguais...
    private RoutingStatus calculateRoutingStatus(CargoItinerary itinerary, RouteSpecification routeSpecification) {
        if (itinerary == null || itinerary == CargoItinerary.EMPTY_ITINERARY) {
            return RoutingStatus.NOT_ROUTED;
        } else {
            return RoutingStatus.ROUTED;
        }
    }

    private TransportStatus calculateTransportStatus() {
        if (lastEvent.getHandlingEventType() == null) {
            return TransportStatus.NOT_RECEIVED;
        }

        switch (lastEvent.getHandlingEventType()) {
            case "LOAD":
                return TransportStatus.ONBOARD_CARRIER;
            case "UNLOAD":
            case "RECEIVE":
            case "CUSTOMS":
                return TransportStatus.IN_PORT;
            case "CLAIM":
                return TransportStatus.CLAIMED;
            default:
                return TransportStatus.UNKNOWN;
        }
    }

    private Location calculateLastKnownLocation() {
        if (lastEvent != null) {
            return new Location(lastEvent.getHandlingEventLocation());
        } else {
            return null;
        }
    }

    private Voyage calculateCurrentVoyage() {
        if (getTransportStatus().equals(TransportStatus.ONBOARD_CARRIER) && lastEvent != null) {
            return new Voyage(lastEvent.getHandlingEventVoyage());
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Delivery))
            return false;
        Delivery delivery = (Delivery) o;
        return routingStatus == delivery.routingStatus &&
                transportStatus == delivery.transportStatus &&
                Objects.equals(lastKnownLocation, delivery.lastKnownLocation) &&
                Objects.equals(currentVoyage, delivery.currentVoyage) &&
                Objects.equals(lastEvent, delivery.lastEvent) &&
                Objects.equals(nextExpectedActivity, delivery.nextExpectedActivity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routingStatus, transportStatus, lastKnownLocation,
                currentVoyage, lastEvent, nextExpectedActivity);
    }
}
