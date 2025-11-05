package com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoRoutedEvent;
import com.br.hugo.ddd.monolithiccargotracker.tracking.application.internal.commandservices.AssignTrackingIdCommandService;
import com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events.transform.TrackingDetailsCommandEventAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class CargoRoutedEventHandler {

    @Inject
    private AssignTrackingIdCommandService assignTrackingIdCommandService; // Injeção do Serviço de Aplicação

    /**
     * "Método do Manipulador do Evento de Carga Roteada
     * 
     * @param event
     */

    @Transactional
    public void observeCargoRoutedEvent(@Observes CargoRoutedEvent event) {
        System.out.println("****Observing Cargo Routed Event***");
        assignTrackingIdCommandService
                .assignTrackingNumberToCargo(TrackingDetailsCommandEventAssembler.toCommandFromEvent(event));
    }
}
