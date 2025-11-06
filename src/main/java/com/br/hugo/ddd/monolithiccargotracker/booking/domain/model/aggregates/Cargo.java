package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.commands.BookCargoCommand;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.entities.Location;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.*;

import javax.persistence.*;

// DOMAIN (Modelo de Domínio)
/**
 * Agregado Raiz Cargo
 * 
 * Esta classe representa o Agregado Raiz no Bounded Context de Booking. Ela é a entidade principal que mantém a integridade e consistência do agregado.
 * 
 * Conceitos:
 * - Agregado: Um cluster de entidades e objetos de valor que são tratados como uma única unidade para mudanças de dados.
 * - Agregado Raiz: A entidade que é o ponto de entrada para acessar o agregado. Possui uma identidade única (BookingId) e é responsável por garantir as invariantes.
 * - Entidades: Objetos com identidade única que são parte do agregado (ex: Location, que é uma entidade dentro do agregado Cargo).
 * - Objetos de Valor: Objetos imutáveis sem identidade, que são substituídos como um todo (ex: RouteSpecification, Itinerary, Delivery).
 * - Domain Richness: O agregado Cargo possui atributos de negócio (origin, routeSpecification, etc.) e métodos de negócio (assignToRoute) que encapsulam a lógica de domínio.
 * - Estado do Agregado: O estado do agregado é persistido via JPA (annotations @Entity, @Embedded, etc.).
 * 
 * O agregado Cargo é uma JPA Entity, e suas dependências são mapeadas como Embedded (objetos de valor) ou associations (entidades).
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Cargo.findAll", query = "Select c from Cargo c"),
        @NamedQuery(name = "Cargo.findByBookingId", query = "Select c from Cargo c where c.bookingId = :bookingId"),
        @NamedQuery(name = "Cargo.getAllBookingIds", query = "Select c.bookingId from Cargo c") })
public class Cargo {
    // CONCEITO: Aggregate Root - JPA Entity como raiz do agregado
    // CONCEITO: Technical Key + Business Key

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Surrogate Key
    @Embedded
    private BookingId bookingId; // Business Key - Identificador do Aggregate

    // CONCEITO: Domain Richness - Value Objects embutidos
    @Embedded
    private BookingAmount bookingAmount; // Quantidade Reservada
    @Embedded
    private Location origin; // Entity dentro do Aggregate - Local de Origem da Carga
    @Embedded
    private RouteSpecification routeSpecification; // Value Object - Especificação da Rota da Carga
    @Embedded
    private CargoItinerary itinerary; // Value Object - Itinerário Atribuído à Carga
    @Embedded
    private Delivery delivery; // Value Object com regras de domínio - Verifica o progresso da entrega da carga em relação à Especificação da Rota e
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
     * CONCEITO: State Construction via Command Handler
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
     * CONCEITO: Business Method Coverage - Domain Richness
     * 
     * @param cargoItinerary
     */
    public void assignToRoute(CargoItinerary cargoItinerary) {
        this.itinerary = cargoItinerary;
        // NOTA: No documento ideal, isso publicaria um Domain Event
        // mas aqui fica na Application Service devido a limitação do JPA
    }

}
