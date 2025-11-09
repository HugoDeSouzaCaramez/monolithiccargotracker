package com.br.hugo.ddd.monolithiccargotracker.booking.infrastructure.repositories.jpa;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects.BookingId;
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

// INFRASTRUCTURE (Adaptadores de Saída)
/**
 * Classe de Repositório para o Agregado Cargo
 * 
 * Esta classe é um Repositório no sentido DDD, responsável por persistir e recuperar o agregado Cargo.
 * Ela está localizada no pacote 'infrastructure' do Bounded Context de Booking.
 * 
 * Conceitos:
 * - Repositório: Abstração sobre a camada de persistência, fornecendo uma interface baseada em coleção para acessar agregados.
 * - Outbound Service: Este repositório é um adaptador de saída (outbound adapter) que conecta o domínio ao banco de dados.
 * - JPA: Utiliza o EntityManager do JPA para realizar operações de persistência.
 * - CDI: É um CDI Bean, gerenciado pelo container, com escopo de aplicação.
 *  Repository
 * 
 * O repositório é responsável por operations como store, find, nextBookingId, etc.
 */
@ApplicationScoped
public class CargoRepository {
    // CONCEITO: Repository Pattern - Abstraction over persistence
    // CONCEITO: Outbound Service - Infrastructure layer

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(
            CargoRepository.class.getName());

    @PersistenceContext(unitName = "monolithiccargotracker")
    private EntityManager entityManager; // CONCEITO: JPA Managed Resource

    /**
     * Retorna o Agregado Carga com base no Identificador de Reserva de uma Carga
     * 
     * CONCEITO: Aggregate-oriented persistence
     * 
     * @param bookingId
     * @return
     */
    public Cargo find(BookingId bookingId) {
        Cargo cargo;
        try {
            // CONCEITO: Named Queries no Aggregate Root
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
     * CONCEITO: Store whole aggregate
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
     * CONCEITO: Business Key Generation
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
