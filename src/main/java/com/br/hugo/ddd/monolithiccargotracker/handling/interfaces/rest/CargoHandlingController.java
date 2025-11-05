package com.br.hugo.ddd.monolithiccargotracker.handling.interfaces.rest;

import com.br.hugo.ddd.monolithiccargotracker.handling.application.internal.commandservices.commandservices.HandlingActivityRegistrationCommandService;
import com.br.hugo.ddd.monolithiccargotracker.handling.interfaces.rest.dto.HandlingActivityRegistrationResource;
import com.br.hugo.ddd.monolithiccargotracker.handling.interfaces.rest.transform.HandlingActivityRegistrationCommandDTOAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Controlador para a API REST
 */
@Path("/cargohandling")
@ApplicationScoped
public class CargoHandlingController {

        @Inject
        private HandlingActivityRegistrationCommandService handlingActivityRegistrationCommandService; // Injeção do
                                                                                                       // Serviço de
                                                                                                       // Aplicação

        /**
         * Método POST para registrar Atividades de Manuseio
         * 
         * @param handlingActivityRegistrationResource
         */
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        public Response registerHandlingActivity(
                        HandlingActivityRegistrationResource handlingActivityRegistrationResource) {
                System.out.println("***" + handlingActivityRegistrationResource.getBookingId());
                System.out.println("***" + handlingActivityRegistrationResource.getHandlingType());

                handlingActivityRegistrationCommandService.registerHandlingActivityService(
                                HandlingActivityRegistrationCommandDTOAssembler
                                                .toCommandFromDTO(handlingActivityRegistrationResource));
                final Response returnValue = Response.ok()
                                .entity("Handling Activity Registered")
                                .build();
                return returnValue;
        }
}
