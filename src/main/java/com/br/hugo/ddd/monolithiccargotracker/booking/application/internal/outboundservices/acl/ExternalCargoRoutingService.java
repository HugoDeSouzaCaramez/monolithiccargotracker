package com.br.hugo.ddd.monolithiccargotracker.booking.application.internal.outboundservices.acl;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.Location;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.CargoItinerary;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.Leg;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.RouteSpecification;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.Voyage;
import com.br.hugo.ddd.monolithiccargotracker.booking.infrastructure.services.http.ExternalCargoRoutingClient;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.model.TransitEdge;
import com.br.hugo.ddd.monolithiccargotracker.shareddomain.model.TransitPath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

// ANTI-CORRUPTION LAYER
/**
 * Classe de Serviço Anticorrupção
 */
@ApplicationScoped
public class ExternalCargoRoutingService {
    // CONCEITO: Anti-Corruption Layer - traduz entre modelos

    @Inject
    private ExternalCargoRoutingClient externalCargoRoutingClient;

    /**
     * O Bounded Context de Reserva faz uma chamada externa ao Serviço de Roteamento
     * do
     * Bounded Context de Roteamento para
     * buscar o Itinerário Ideal para uma Carga com base na Especificação da Rota
     * 
     * @param routeSpecification
     * @return
     */
    public CargoItinerary fetchRouteForSpecification(RouteSpecification routeSpecification) {

        // CONCEITO: External Service Call
        TransitPath transitPath = externalCargoRoutingClient.findOptimalRoute(
                routeSpecification.getOrigin().getUnLocCode(),
                routeSpecification.getDestination().getUnLocCode(),
                routeSpecification.getArrivalDeadline().toString());

        // CONCEITO: Model Translation
        List<Leg> legs = new ArrayList<Leg>(transitPath.getTransitEdges().size());
        for (TransitEdge edge : transitPath.getTransitEdges()) {
            legs.add(toLeg(edge));
        }

        return new CargoItinerary(legs);

    }

    /**
     * Método de conversão da camada anticorrupção do modelo de domínio do serviço
     * de roteamento (TransitEdges)
     * para o modelo de domínio reconhecido pelo Bounded Context de Reserva (Legs)
     * 
     * CONCEITO: Private mapping method
     * 
     * @param edge
     * @return
     */
    private Leg toLeg(TransitEdge edge) {
        return new Leg(
                new Voyage(edge.getVoyageNumber()),
                new Location(edge.getFromUnLocode()),
                new Location(edge.getToUnLocode()),
                edge.getFromDate(),
                edge.getToDate());
    }
}
