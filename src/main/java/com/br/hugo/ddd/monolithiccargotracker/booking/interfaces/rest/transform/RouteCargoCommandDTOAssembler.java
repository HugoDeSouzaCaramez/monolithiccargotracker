package com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.transform;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.commands.RouteCargoCommand;
import com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.dto.RouteCargoResource;

// INTERFACES (Adaptadores de Entrada)
/**
 * Classe Assembler para converter os Dados do Recurso de Reserva de Carga no
 * Modelo de Reserva de Carga
 * É uma interface (adaptador de entrada) (INTERFACES)
 */
public class RouteCargoCommandDTOAssembler {

    /**
     * Método estático dentro da classe Assembler
     * 
     * @param routeCargoResource
     * @return RouteCargoCommand Model
     */
    public static RouteCargoCommand toCommandFromDTO(RouteCargoResource routeCargoResource) {

        return new RouteCargoCommand(routeCargoResource.getBookingId());
    }
}
