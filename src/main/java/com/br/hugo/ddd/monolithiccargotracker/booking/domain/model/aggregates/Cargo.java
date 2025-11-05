package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.commands.BookCargoCommand;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.entities.Location;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.*;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Cargo.findAll", query = "Select c from Cargo c"),
        @NamedQuery(name = "Cargo.findByBookingId", query = "Select c from Cargo c where c.bookingId = :bookingId"),
        @NamedQuery(name = "Cargo.getAllBookingIds", query = "Select c.bookingId from Cargo c") })
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private BookingId bookingId; // Identificador do Agregado
    @Embedded
    private BookingAmount bookingAmount; // Quantidade Reservada
    @Embedded
    private Location origin; // Local de Origem da Carga
    @Embedded
    private RouteSpecification routeSpecification; // Especificação da Rota da Carga
    @Embedded
    private CargoItinerary itinerary; // Itinerário Atribuído à Carga
    @Embedded
    private Delivery delivery; // Verifica o progresso da entrega da carga em relação à Especificação da Rota e
                               // Itinerário reais

    /**
     * Construtor Padrão
     */
    public Cargo() {
        // Nada para Inicializar
    }

    /**
     * Manipulador de Comando do Construtor para uma nova reserva de Carga. Define o
     * estado do
     * Agregado
     * e registra o Evento de Carga Reservada
     *
     */
    public Cargo(BookCargoCommand bookCargoCommand) {

        this.bookingId = new BookingId(bookCargoCommand.getBookingId());
        this.routeSpecification = new RouteSpecification(
                new Location(bookCargoCommand.getOriginLocation()),
                new Location(bookCargoCommand.getDestLocation()),
                bookCargoCommand.getDestArrivalDeadline());
        this.origin = routeSpecification.getOrigin();
        this.bookingAmount = new BookingAmount(bookCargoCommand.getBookingAmount());
        this.itinerary = CargoItinerary.EMPTY_ITINERARY; // Itinerário Vazio pois a Carga ainda não foi roteada
        this.delivery = Delivery.derivedFrom(this.routeSpecification,
                this.itinerary, LastCargoHandledEvent.EMPTY);
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getOrigin() {
        return origin;
    }

    public RouteSpecification getRouteSpecification() {
        return this.routeSpecification;
    }

    public BookingAmount getBookingAmount() {
        return this.bookingAmount;
    }

    public void setBookingAmount(BookingAmount bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    /**
     * @return O Itinerário
     */
    public CargoItinerary getItinerary() {
        return this.itinerary;
    }

    public void setItinerary(CargoItinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Atribui Rota à Carga
     * 
     * @param cargoItinerary
     */
    public void assignToRoute(CargoItinerary cargoItinerary) {
        this.itinerary = cargoItinerary;
    }

}
