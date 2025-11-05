package com.br.hugo.ddd.monolithiccargotracker.booking.infrastructure.repositories.jpa;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates.BookingId;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates.Cargo;

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
 * Classe de Repositório para o Agregado Carga. Responsável por todas as operações
 * de repositório
 * relacionadas ao estado da Carga
 */
@ApplicationScoped
public class CargoRepository {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(
            CargoRepository.class.getName());

    @PersistenceContext(unitName = "monolithiccargotracker")
    private EntityManager entityManager;

    /**
     * Retorna o Agregado Carga com base no Identificador de Reserva de uma Carga
     * 
     * @param bookingId
     * @return
     */
    public Cargo find(BookingId bookingId) {
        Cargo cargo;
        try {
            cargo = entityManager.createNamedQuery("Cargo.findByBookingId",
                    Cargo.class)
                    .setParameter("bookingId", bookingId)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant Booking ID.", e);
            cargo = null;
        }

        return cargo;
    }

    /**
     * Persiste o Agregado Carga
     * 
     * @param cargo
     */
    public void store(Cargo cargo) {
        System.out.println("***Entity Manager is***" + entityManager);
        entityManager.persist(cargo);
        entityManager.flush();
    }

    /**
     * Obtém o Próximo Identificador de Reserva
     * 
     * @return
     */

    public String nextBookingId() {
        String random = UUID.randomUUID().toString().toUpperCase();
        return random.substring(0, random.indexOf("-"));
    }

    /**
     * Buscar todos os Agregados Carga
     * 
     * @return
     */
    public List<Cargo> findAll() {
        return entityManager.createNamedQuery("Cargo.findAll", Cargo.class)
                .getResultList();
    }

    /**
     * Obter todos os Identificadores de Reserva
     * 
     * @return
     */
    public List<BookingId> findAllBookingIds() {
        List<BookingId> bookingIds = new ArrayList<BookingId>();

        try {
            bookingIds = entityManager.createNamedQuery(
                    "Cargo.getAllBookingIds", BookingId.class).getResultList();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Unable to get all tracking IDs", e);
        }

        return bookingIds;
    }
}
