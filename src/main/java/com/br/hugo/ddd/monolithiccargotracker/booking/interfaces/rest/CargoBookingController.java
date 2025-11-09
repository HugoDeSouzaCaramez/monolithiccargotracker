package com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest;

import com.br.hugo.ddd.monolithiccargotracker.booking.application.internal.commandservices.CargoBookingCommandService;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.BookingId;
import com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.dto.BookCargoResource;
import com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.transform.BookCargoCommandDTOAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// INTERFACES (Adaptadores de Entrada)
/**
 * Controlador REST para o Bounded Context de Booking
 * 
 * Esta classe é um Adaptador de Entrada (Inbound Adapter) (INTERFACE) no sentido da Arquitetura Hexagonal. Ela expõe endpoints REST para interagir com o Bounded Context de Booking.
 * 
 * Conceitos:
 * - Inbound Service: Adaptador que recebe requisições externas (REST) e as traduz em chamadas para o interior da aplicação (Serviços de Aplicação).
 * - JAX-RS: Utiliza as anotações do JAX-RS para definir os endpoints REST.
 * - CDI: Utiliza injeção de dependência para obter o Serviço de Aplicação (CargoBookingCommandService).
 * 
 * Este controlador está localizado no pacote 'interfaces' do Bounded Context de Booking.
 */
@Path("/cargobooking")
@ApplicationScoped
public class CargoBookingController {

    @Inject
    private CargoBookingCommandService cargoBookingCommandService; // Injeção do Serviço de Aplicação

    /**
     * Método POST para reservar uma carga
     * 
     * @param bookCargoResource
     */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookCargo(BookCargoResource bookCargoResource) {
        System.out.println("****Book Cargo" + cargoBookingCommandService);
        BookingId bookingId = cargoBookingCommandService.bookCargo(
                BookCargoCommandDTOAssembler.toCommandFromDTO(bookCargoResource));

        final Response returnValue = Response.ok()
                .entity(bookingId)
                .build();
        return returnValue;
    }

}
