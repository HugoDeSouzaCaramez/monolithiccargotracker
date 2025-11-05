package com.br.hugo.ddd.monolithiccargotracker.routing.application.internal.queryservices;

import com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.aggregates.Voyage;
import com.br.hugo.ddd.monolithiccargotracker.routing.infrastructure.repositories.jpa.VoyageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Classe de Serviço de Aplicação para o serviço de Consulta de Roteamento de
 * Carga
 */
@ApplicationScoped
public class CargoRoutingQueryService {

    @Inject
    private VoyageRepository voyageRepository; // Injeção de Dependências

    /**
     * Retorna todas as Viagens
     * 
     * @return
     */
    @Transactional
    public List<Voyage> findAll() {
        return voyageRepository.findAll();
    }

}
