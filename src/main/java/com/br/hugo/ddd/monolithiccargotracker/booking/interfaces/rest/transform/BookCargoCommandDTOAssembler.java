package com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.transform;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.commands.BookCargoCommand;
import com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.dto.BookCargoResource;

// INTERFACES (Adaptadores de Entrada)
/**
 * Classe Assembler para converter os Dados do Recurso de Reserva de Carga no
 * Modelo de Reserva de Carga
 * É uma interface (adaptador de entrada) (INTERFACES)
 */
public class BookCargoCommandDTOAssembler {

    /**
     * Método estático dentro da classe Assembler
     * 
     * @param bookCargoResource
     * @return Modelo BookCargoCommand
     */
    public static BookCargoCommand toCommandFromDTO(BookCargoResource bookCargoResource) {

        return new BookCargoCommand(
                bookCargoResource.getBookingAmount(),
                bookCargoResource.getOriginLocation(),
                bookCargoResource.getDestLocation(),
                java.sql.Date.valueOf(bookCargoResource.getDestArrivalDeadline()));
    }
}
