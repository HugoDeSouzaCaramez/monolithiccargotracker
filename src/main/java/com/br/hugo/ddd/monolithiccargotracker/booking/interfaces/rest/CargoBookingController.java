package com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest;

import com.br.hugo.ddd.monolithiccargotracker.booking.application.internal.commandservices.CargoBookingCommandService;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates.BookingId;
import com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.dto.BookCargoResource;
import com.br.hugo.ddd.monolithiccargotracker.booking.interfaces.rest.transform.BookCargoCommandDTOAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
