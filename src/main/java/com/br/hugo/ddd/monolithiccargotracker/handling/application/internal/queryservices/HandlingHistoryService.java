package com.br.hugo.ddd.monolithiccargotracker.handling.application.internal.queryservices;

import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.HandlingActivityHistory;
import com.br.hugo.ddd.monolithiccargotracker.handling.infrastructure.repositories.jpa.HandlingActivityRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Serviço de Aplicação que atende a todas as consultas relacionadas ao Agregado
 * de Atividade de Manuseio
 * 
 * QUERIES - Apenas leem estado 
 */
@ApplicationScoped
public class HandlingHistoryService {

    @Inject
    private HandlingActivityRepository handlingActivityRepository;

    @Transactional
    public HandlingActivityHistory getHandlingActivityHistory(String bookingId) {
        return handlingActivityRepository.lookupHandlingHistoryOfCargo(bookingId);
    }
}
