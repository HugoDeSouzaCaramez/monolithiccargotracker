package com.br.hugo.ddd.monolithiccargotracker.handling.infrastructure.repositories.jpa;

import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.aggregates.HandlingActivity;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.HandlingActivityHistory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class HandlingActivityRepository {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "monolithiccargotracker")
    private EntityManager entityManager;

    public void store(HandlingActivity handlingActivity) {
        entityManager.persist(handlingActivity);
    }

    /**
     * Obter o histórico completo de manuseio de uma Carga
     * 
     * @param bookingId
     * @return
     */
    public HandlingActivityHistory lookupHandlingHistoryOfCargo(String bookingId) {
        return new HandlingActivityHistory(entityManager.createNamedQuery(
                "HandlingActivity.findByBookingId", HandlingActivity.class)
                .setParameter("bookingId", bookingId).getResultList());
    }
}
