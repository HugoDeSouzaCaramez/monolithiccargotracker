package com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.valueobjects;

import com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.entities.CarrierMovement;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

/**
 * Um cronograma de Viagem
 */
@Embeddable
public class Schedule {

    public static final Schedule EMPTY = new Schedule();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "voyage_id")
    private List<CarrierMovement> carrierMovements = Collections.emptyList();

    public Schedule() {
        // Nada para Inicializar
    }

    Schedule(List<CarrierMovement> carrierMovements) {
        this.carrierMovements = carrierMovements;
    }

    public List<CarrierMovement> getCarrierMovements() {
        return Collections.unmodifiableList(carrierMovements);
    }
}
