package com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events.transform;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoRoutedEvent;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.commands.AssignTrackingNumberCommand;

/**
 * Classe Assembler para converter o Evento de Carga Roteada no Modelo de
 * Comando de Atribuir Número de Rastreamento
 */
public class TrackingDetailsCommandEventAssembler {

    /**
     * Método estático dentro da classe Assembler
     * 
     * @param cargoRoutedEvent
     * @return Modelo AssignTrackingNumberCommand
     */
    public static AssignTrackingNumberCommand toCommandFromEvent(CargoRoutedEvent cargoRoutedEvent) {
        System.out.println("****cargoRoutedEvent****" + cargoRoutedEvent.getContent().getBookingId());
        return new AssignTrackingNumberCommand(
                cargoRoutedEvent.getContent().getBookingId(), "");
    }
}
