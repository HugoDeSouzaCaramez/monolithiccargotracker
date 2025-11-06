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

// APPLICATION (Serviços de Aplicação)
/**
 * Classe de Serviço de Aplicação para o Comando de Reserva de Carga
 * 
 * Esta classe é um Serviço de Aplicação (APPLICATION) no sentido DDD, responsável por orquestrar a execução de comandos que alteram o estado do sistema.
 * Ela está localizada no pacote 'application' do Bounded Context de Booking, que é um dos módulos do monolito.
 * 
 * Conceitos:
 * - Serviço de Aplicação: Coordena a execução de comandos, utiliza o modelo de domínio e dispara eventos de domínio.
 * - Comando: Representa uma intenção de alterar o estado do sistema (ex: bookCargo, assignRouteToCargo).
 * - Eventos de Domínio: Dispara eventos (ex: CargoBookedEvent, CargoRoutedEvent) que são consumidos por outros Bounded Contexts.
 * - Transações: A anotação @Transactional inicia uma transação, que é commitada ao final do método (se bem-sucedido) ou revertida (em caso de exceção).
 * COMMANDS - Alteram estado
 * 
 * Esta classe é um CDI Bean, gerenciado pelo container Jakarta EE, com escopo de aplicação.
 */
@ApplicationScoped
public class CargoBookingCommandService {
    // CONCEITO: Application Service - Coordena casos de uso
    // CONCEITO: Transaction Management - @Transactional
    // CONCEITO: Event Publishing - CDI Events

    @Inject
    private CargoRepository cargoRepository; // Serviço de Saída para conectar à Instância do Banco de Dados MySQL do
                                             // Bounded Context de Reserva
                                             // Outbound Service

    @Inject
    private Event<CargoBookedEvent> cargoBookedEventControl; // CDI Event Producer

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
        // CONCEITO: Aggregate Construction - novo aggregate
        // CONCEITO: Business Key Generation - bookingId

        String bookingId = cargoRepository.nextBookingId();
        bookCargoCommand.setBookingId(bookingId);

        // CONCEITO: Domain Model Invocation - constructor with command
        Cargo cargo = new Cargo(bookCargoCommand);

        // CONCEITO: State Persistence - via repository
        cargoRepository.store(cargo); // Armazena a Carga

        // CONCEITO: Domain Event Publishing - from Application Service
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
