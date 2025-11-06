package com.br.hugo.ddd.monolithiccargotracker.booking.application.internal.queryservices;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates.BookingId;
import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.aggregates.Cargo;
import com.br.hugo.ddd.monolithiccargotracker.booking.infrastructure.repositories.jpa.CargoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

// APPLICATION (Serviços de Aplicação)
/**
 * Serviço de Aplicação (APPLICATION) que atende a todas as consultas relacionadas ao
 * Bounded Context de Reserva
 * 
 * QUERIES - Apenas leem estado  
 */
@ApplicationScoped
public class CargoBookingQueryService {

    @Inject
    private CargoRepository cargoRepository; // Injeção de dependências

    /**
     * Buscar todas as Cargas
     * 
     * @return List<Cargo>
     */
    @Transactional
    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    /**
     * Listar Todos os Identificadores de Reserva
     * 
     * @return List<BookingId>
     */
    public List<BookingId> getAllBookingIds() {
        return cargoRepository.findAllBookingIds();
    }

    /**
     * Buscar uma Carga específica com base no seu ID de Reserva
     * 
     * @param bookingId
     * @return Cargo
     */
    public Cargo find(String bookingId) {
        return cargoRepository.find(new BookingId(bookingId));
    }
}
