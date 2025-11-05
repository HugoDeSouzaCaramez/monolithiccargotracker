package com.br.hugo.ddd.monolithiccargotracker.booking.application.internal.commandservices;

import com.br.hugo.ddd.monolithiccargotracker.booking.application.internal.outboundservices.acl.ExternalCargoRoutingService;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates.BookingId;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates.Cargo;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.commands.BookCargoCommand;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.commands.RouteCargoCommand;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.CargoItinerary;
import com.br.hugo.ddd.monolithiccargotracker.booking.infrastructure.repositories.jpa.CargoRepository;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoBookedEvent;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoRoutedEvent;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoRoutedEventData;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Classe de Serviço de Aplicação para o Comando de Reserva de Carga
 */
@ApplicationScoped
public class CargoBookingCommandService {

    @Inject
    private CargoRepository cargoRepository; // Serviço de Saída para conectar à Instância do Banco de Dados MySQL do
                                             // Bounded Context de Reserva

    @Inject
    private Event<CargoBookedEvent> cargoBookedEventControl;

    @Inject
    private Event<CargoRoutedEvent> cargoRoutedEventControl; // Evento que precisa ser disparado quando a Carga é
                                                             // Reservada

    @Inject
    private ExternalCargoRoutingService externalCargoRoutingService;

    /**
     * Método de Comando do Serviço para reservar uma nova Carga
     * 
     * @return ID da Reserva da Carga
     */
    @Transactional // Inicia a Transação
    public BookingId bookCargo(BookCargoCommand bookCargoCommand) {
        String bookingId = cargoRepository.nextBookingId();
        bookCargoCommand.setBookingId(bookingId);
        Cargo cargo = new Cargo(bookCargoCommand);
        cargoRepository.store(cargo); // Armazena a Carga

        CargoBookedEvent cargoBookedEvent = new CargoBookedEvent();
        cargoBookedEvent.setId(bookingId); // Define o conteúdo do evento
        cargoBookedEventControl.fire(cargoBookedEvent);

        return new BookingId(bookingId);
    }

    /**
     * Método de Comando do Serviço para atribuir uma rota a uma Carga
     * 
     * @param routeCargoCommand
     */
    @Transactional
    public void assignRouteToCargo(RouteCargoCommand routeCargoCommand) {
        Cargo cargo = cargoRepository.find(new BookingId(routeCargoCommand.getCargoBookingId()));

        CargoItinerary cargoItinerary = externalCargoRoutingService
                .fetchRouteForSpecification(cargo.getRouteSpecification());

        cargo.assignToRoute(cargoItinerary);
        cargoRepository.store(cargo);

        CargoRoutedEvent cargoRoutedEvent = new CargoRoutedEvent();
        CargoRoutedEventData eventData = new CargoRoutedEventData();
        eventData.setBookingId(routeCargoCommand.getCargoBookingId());
        cargoRoutedEvent.setContent(eventData);
        cargoRoutedEventControl.fire(cargoRoutedEvent);
    }

}
