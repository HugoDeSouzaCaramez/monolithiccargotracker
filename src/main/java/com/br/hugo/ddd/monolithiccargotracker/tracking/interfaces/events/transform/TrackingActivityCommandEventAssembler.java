package com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events.transform;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoHandledEvent;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoHandledEventData;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.commands.AddTrackingEventCommand;

/**
 * Classe Assembler para converter o Evento de Carga Roteada no Modelo de
 * Comando de Atribuição de Número de Rastreamento
 */
public class TrackingActivityCommandEventAssembler {

    /**
     * Método estático dentro da classe Assembler
     *
     * @param cargoHandledEvent
     * @return Modelo do Comando de Atribuição de Número de Rastreamento
     */
    public static AddTrackingEventCommand toCommandFromEvent(CargoHandledEvent cargoHandledEvent) {
        CargoHandledEventData eventData = cargoHandledEvent.getContent();
        return new AddTrackingEventCommand(
                eventData.getBookingId(),
                eventData.getHandlingCompletionTime(),
                eventData.getHandlingType(),
                eventData.getHandlingLocation(),
                eventData.getVoyageNumber());
    }
}
