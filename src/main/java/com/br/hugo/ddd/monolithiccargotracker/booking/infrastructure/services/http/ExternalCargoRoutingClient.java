package com.br.hugo.ddd.monolithiccargotracker.booking.infrastructure.services.http;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.model.TransitPath;

// INFRASTRUCTURE (Adaptadores de Saída)
/**
 * Cliente REST Tipado (Type Safe) para a API do Serviço de Roteamento
 */

@ApplicationScoped
public class ExternalCargoRoutingClient {

    public TransitPath findOptimalRoute(String origin, String destination, String arrivalDeadline) {
        final String REST_URI = "http://localhost:9080/monolithiccargotracker/serviceapi/voyageRouting/optimalRoute";

        Client client = ClientBuilder.newClient();

        return client
                .target(REST_URI)
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .queryParam("deadline", arrivalDeadline)
                .request(MediaType.APPLICATION_JSON)
                .get(TransitPath.class);

    }

}
