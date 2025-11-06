package com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoRoutedEvent;
import com.br.hugo.ddd.monolithiccargotracker.tracking.application.internal.commandservices.AssignTrackingIdCommandService;
import com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events.transform.TrackingDetailsCommandEventAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

// EVENT HANDLER
/**
 * Event Handler para CargoRoutedEvent
 * 
 * Esta classe é um Event Handler no Bounded Context de Tracking, responsável por reagir ao evento CargoRoutedEvent (disparado pelo Bounded Context de Booking).
 * 
 * Conceitos:
 * - Event Handler: Consome eventos de domínio de outros Bounded Contexts e executa lógica em resposta.
 * - Inbound Service: Do ponto de vista do Tracking, este handler é um adaptador de entrada (inbound) que recebe eventos via CDI.
 * - CDI Events: Utiliza a anotação @Observes para observar eventos do tipo CargoRoutedEvent.
 * - Serviço de Aplicação: Utiliza o AssignTrackingIdCommandService (um Serviço de Aplicação) para executar a lógica de negócio em resposta ao evento.
 * 
 * Este handler está localizado no pacote 'interfaces.events' do Bounded Context de Tracking, demonstrando que eventos são uma forma de comunicação entre Bounded Contexts.
 */
@ApplicationScoped
public class CargoRoutedEventHandler {
    // CONCEITO: Event Handler em Bounded Context diferente do produtor

    @Inject
    private AssignTrackingIdCommandService assignTrackingIdCommandService; // Injeção do Serviço de Aplicação

    /**
     * "Método do Manipulador do Evento de Carga Roteada
     * 
     * @param event
     */

    @Transactional
    public void observeCargoRoutedEvent(@Observes CargoRoutedEvent event) {
        // CONCEITO: CDI Event Observation - @Observes
        // CONCEITO: Event Reaction - inicia novo workflow
        System.out.println("****Observing Cargo Routed Event***");
        assignTrackingIdCommandService
                .assignTrackingNumberToCargo(TrackingDetailsCommandEventAssembler.toCommandFromEvent(event));
    }
}
