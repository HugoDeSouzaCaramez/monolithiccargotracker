package com.br.hugo.ddd.monolithiccargotracker.routing.infrastructure.repositories.jpa;

import com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.aggregates.Voyage;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class VoyageRepository {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(
            VoyageRepository.class.getName());

    @PersistenceContext(unitName = "monolithiccargotracker")
    private EntityManager entityManager;

    /**
     * Armazena o Agregado Viagem
     * 
     * @param voyage
     */
    @Transactional
    public void store(Voyage voyage) {
        entityManager.persist(voyage);
        entityManager.flush();
    }

    /**
     * Buscar todos os Agregados Viagem
     * 
     * @return
     */
    @Transactional
    public List<Voyage> findAll() {
        return entityManager.createNamedQuery("Voyage.findAll", Voyage.class)
                .getResultList();
    }
}
