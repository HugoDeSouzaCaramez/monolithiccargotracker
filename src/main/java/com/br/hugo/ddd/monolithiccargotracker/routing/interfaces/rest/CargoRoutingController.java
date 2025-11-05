package com.br.hugo.ddd.monolithiccargotracker.routing.interfaces.rest;

import com.br.hugo.ddd.monolithiccargotracker.routing.application.internal.queryservices.CargoRoutingQueryService;
import com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.aggregates.Voyage;
import com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.entities.CarrierMovement;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.model.TransitEdge;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.model.TransitPath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Path("/voyageRouting")
@ApplicationScoped
public class CargoRoutingController {

    @Inject
    private CargoRoutingQueryService cargoRoutingQueryService; // Injeção do Serviço de Aplicação

    /**
     * Uma implementação altamente simplificada do algoritmo de Roteamento.
     * Funciona apenas com as especificações fornecidas no caso de teste (CNHKG -
     * USNYC
     * e 2019-09-28).
     * O Modelo de Domínio pode ser alterado para implementar algoritmos mais
     * sofisticados
     * 
     * @param originUnLocode
     * @param destinationUnLocode
     * @param deadline
     * @return
     */
    @GET
    @Path("/optimalRoute")
    @Produces({ "application/json" })
    public TransitPath findOptimalRoute(
            @QueryParam("origin") String originUnLocode,
            @QueryParam("destination") String destinationUnLocode,
            @QueryParam("deadline") String deadline) {

        List<Voyage> voyages = cargoRoutingQueryService.findAll();
        System.out.println("***Voyages are****" + voyages.size());
        TransitPath transitPath = new TransitPath();
        List<TransitEdge> transitEdges = new ArrayList<>();
        for (Voyage voyage : voyages) {

            TransitEdge transitEdge = new TransitEdge();
            transitEdge.setVoyageNumber(voyage.getVoyageNumber().getVoyageNumber());
            List<CarrierMovement> carrierMovements = voyage.getSchedule().getCarrierMovements();

            CarrierMovement movement = ((List<CarrierMovement>) voyage.getSchedule().getCarrierMovements()).get(0);
            transitEdge.setFromDate(movement.getArrivalDate());
            transitEdge.setToDate(movement.getDepartureDate());
            // transitEdge.setFromUnLocode(movement.getArrivalLocation().getUnLocCode());
            // transitEdge.setToUnLocode(movement.getDepartureLocation().getUnLocCode());
            transitEdges.add(transitEdge);

        }

        transitPath.setTransitEdges(transitEdges);
        return transitPath;

    }
}
