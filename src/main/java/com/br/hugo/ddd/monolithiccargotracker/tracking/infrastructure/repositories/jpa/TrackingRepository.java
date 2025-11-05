package com.br.hugo.ddd.monolithiccargotracker.tracking.infrastructure.repositories.jpa;

import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.aggregates.TrackingActivity;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.aggregates.TrackingNumber;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.entities.TrackingBookingId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe de Repositório para o Agregado de Rastreamento. Responsável por todas
 * as operações
 * de repositório
 * relacionadas ao estado do Rastreamento da Carga
 */
@ApplicationScoped
public class TrackingRepository {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(
            TrackingRepository.class.getName());

    @PersistenceContext(unitName = "monolithiccargotracker")
    private EntityManager entityManager;

    /**
     * Retorna o Agregado Carga com base no Número de Rastreamento da Carga
     * 
     * @param trackingNumber
     * @return TrackingActivity
     */
    public TrackingActivity find(TrackingNumber trackingNumber) {
        TrackingActivity trackingActivity;
        try {
            trackingActivity = entityManager.createNamedQuery("TrackingActivity.findByTrackingNumber",
                    TrackingActivity.class)
                    .setParameter("trackingNumber", trackingNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant Tracking No.", e);
            trackingActivity = null;
        }

        return trackingActivity;
    }

    /**
     * Retorna o Agregado de Rastreamento com base no Identificador de Reserva
     * 
     * @param bookingId
     * @return
     */
    public TrackingActivity findByBookingId(TrackingBookingId bookingId) {
        TrackingActivity trackingActivity;
        try {
            trackingActivity = entityManager.createNamedQuery("TrackingActivity.findByBookingNumber",
                    TrackingActivity.class)
                    .setParameter("bookingId", bookingId)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant Booking ID.", e);
            trackingActivity = null;
        }
        return trackingActivity;
    }

    /**
     * Armazena o Agregado de Atividade de Rastreamento
     * 
     * @param trackingActivity
     */
    public void store(TrackingActivity trackingActivity) {
        entityManager.persist(trackingActivity);
        entityManager.flush();
    }

    /**
     * Obtém o Próximo Identificador de Rastreamento
     * 
     * @return
     */
    public String nextTrackingNumber() {
        String random = UUID.randomUUID().toString().toUpperCase();
        return random.substring(0, random.indexOf("-"));
    }

    /**
     * Busca todos os Agregados de Atividade de Rastreamento
     * 
     * @return
     */
    public List<TrackingActivity> findAll() {
        return entityManager.createNamedQuery("TrackingActivity.findAll", TrackingActivity.class)
                .getResultList();
    }

    /**
     * Obtém todos os Números de Rastreamento
     * 
     * @return
     */
    public List<TrackingNumber> findAllTrackingNumbers() {
        List<TrackingNumber> trackingNumbers = new ArrayList<TrackingNumber>();

        try {
            trackingNumbers = entityManager.createNamedQuery(
                    "TrackingActivity.getAllTrackingNos", TrackingNumber.class).getResultList();

        } catch (NoResultException e) {
            logger.log(Level.FINE, "Unable to get all tracking IDs", e);
        }

        return trackingNumbers;
    }
}
