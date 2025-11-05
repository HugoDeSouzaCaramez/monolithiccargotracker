package com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoHandledEvent;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoHandledEventData;
import com.br.hugo.ddd.monolithiccargotracker.tracking.application.internal.commandservices.AssignTrackingIdCommandService;
import com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events.transform.TrackingActivityCommandEventAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class CargoHandledEventHandler {

    @Inject
    private AssignTrackingIdCommandService assignTrackingIdCommandService; // Injeção do Serviço de Aplicação

    /**
     * Processador do Evento de Carga Manipulada
     * 
     * @param event
     */
    @Transactional
    public void observeCargoHandledEvent(@Observes CargoHandledEvent event) {
        System.out.println("***Cargo Handled Event****" + event.getContent());
        CargoHandledEventData eventData = event.getContent();
        System.out.println(eventData.getBookingId());
        System.out.println(eventData.getHandlingLocation());
        System.out.println(eventData.getHandlingCompletionTime());
        System.out.println(eventData.getHandlingType());
        System.out.println(eventData.getVoyageNumber());
        assignTrackingIdCommandService.addTrackingEvent(
                TrackingActivityCommandEventAssembler.toCommandFromEvent(event));
    }

}
