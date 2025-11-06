package com.br.hugo.ddd.monolithiccargotracker.handling.interfaces.rest.transform;

import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.commands.HandlingActivityRegistrationCommand;
import com.br.hugo.ddd.monolithiccargotracker.handling.interfaces.rest.dto.HandlingActivityRegistrationResource;

/**
 * Classe Assembler para converter os Dados do Recurso de Reserva de Carga no
 * Modelo de Reserva de Carga
 * É uma interface (adaptador de entrada) (INTERFACES)
 */
public class HandlingActivityRegistrationCommandDTOAssembler {

    /**
     * Método estático dentro da classe Assembler
     * 
     * @param handlingActivityRegistrationResource
     * @return Modelo BookCargoCommand
     */
    public static HandlingActivityRegistrationCommand toCommandFromDTO(
            HandlingActivityRegistrationResource handlingActivityRegistrationResource) {

        return new HandlingActivityRegistrationCommand(
                handlingActivityRegistrationResource.getBookingId(),
                handlingActivityRegistrationResource.getVoyageNumber(),
                handlingActivityRegistrationResource.getUnLocode(),
                handlingActivityRegistrationResource.getHandlingType(),
                java.sql.Date.valueOf(handlingActivityRegistrationResource.getCompletionTime()));
    }
}
