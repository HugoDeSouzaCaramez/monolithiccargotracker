package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.entities.Location;

import javax.persistence.*;
import java.util.Date;

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
    // CONCEITO: Value Object complexo com regras de domínio
    // CONCEITO: Calculated State - Estado derivado de outras entidades

    public static final Date ETA_UNKOWN = null;

    // Tipos Enumerados - Status de Roteamento / Status de Transporte da Carga
    @Enumerated(EnumType.STRING)
    @Column(name = "routing_status")
    private RoutingStatus routingStatus; // Status de Roteamento da Carga
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_status")
    private TransportStatus transportStatus; // Status de Transporte da Carga. Informações atuais/anteriores da Carga.
    // Auxilia o operador na verificação do estado atual.
    @Column(name = "last_known_location_id")
    @AttributeOverride(name = "unLocCode", column = @Column(name = "last_known_location_id"))
    private Location lastKnownLocation;
    @Column(name = "current_voyage_number")
    @AttributeOverride(name = "voyageNumber", column = @Column(name = "current_voyage_number"))
    private Voyage currentVoyage;
    @Embedded
    private LastCargoHandledEvent lastEvent;

    // Previsões para a atividade da Carga. Auxilia o operador a determinar se algo
    // precisa ser alterado no futuro
    public static final CargoHandlingActivity NO_ACTIVITY = new CargoHandlingActivity();
    @Embedded
    private CargoHandlingActivity nextExpectedActivity;

    public Delivery() {
        // Nada para Inicializar
    }

    // CONCEITO: Domain Rules como métodos privados
    public Delivery(LastCargoHandledEvent lastEvent, CargoItinerary itinerary,
            RouteSpecification routeSpecification) {
        this.lastEvent = lastEvent;

        this.routingStatus = calculateRoutingStatus(itinerary,
                routeSpecification);
        this.transportStatus = calculateTransportStatus();
        this.lastKnownLocation = calculateLastKnownLocation();
        this.currentVoyage = calculateCurrentVoyage();
        // this.nextExpectedActivity = calculateNextExpectedActivity(
        // routeSpecification, itinerary);
    }

    /**
     * Cria uma nova captura de estado da entrega para refletir alterações no
     * roteamento,
     * isto é, quando a especificação da rota ou itinerário mudaram, sem manuseio
     * adicional da carga.
     * 
     * CONCEITO: Update Method retornando novo Value Object
     * 
     */
    public Delivery updateOnRouting(RouteSpecification routeSpecification,
            CargoItinerary itinerary) {

        return new Delivery(this.lastEvent, itinerary, routeSpecification);
    }

    /**
     *
     * @param routeSpecification
     * @param itinerary
     * @param lastCargoHandledEvent
     * @return
     */
    public static Delivery derivedFrom(RouteSpecification routeSpecification,
            CargoItinerary itinerary, LastCargoHandledEvent lastCargoHandledEvent) {

        return new Delivery(lastCargoHandledEvent, itinerary, routeSpecification);
    }

    /**
     * Método para calcular o status de Roteamento de uma Carga
     *
     * CONCEITO: Business Rule Method
     * 
     * @param itinerary
     * @param routeSpecification
     * @return
     */
    private RoutingStatus calculateRoutingStatus(CargoItinerary itinerary,
            RouteSpecification routeSpecification) {
        if (itinerary == null || itinerary == CargoItinerary.EMPTY_ITINERARY) {
            return RoutingStatus.NOT_ROUTED;
        } else {
            return RoutingStatus.ROUTED;
        }
    }

    /**
     * Método para calcular o Status de Transporte de uma Carga
     * 
     * @return
     */
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

    /**
     * Calcular Última Localização Conhecida
     * 
     * @return
     */
    private Location calculateLastKnownLocation() {
        if (lastEvent != null) {
            return new Location(lastEvent.getHandlingEventLocation());
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    private Voyage calculateCurrentVoyage() {
        if (getTransportStatus().equals(TransportStatus.ONBOARD_CARRIER) && lastEvent != null) {
            return new Voyage(lastEvent.getHandlingEventVoyage());
        } else {
            return null;
        }
    }

    public RoutingStatus getRoutingStatus() {
        return this.routingStatus;
    }

    public TransportStatus getTransportStatus() {
        return this.transportStatus;
    }

    public Location getLastKnownLocation() {
        return this.lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public void setLastEvent(LastCargoHandledEvent lastEvent) {
        this.lastEvent = lastEvent;
    }

    public Voyage getCurrentVoyage() {
        return this.currentVoyage;
    }

}
