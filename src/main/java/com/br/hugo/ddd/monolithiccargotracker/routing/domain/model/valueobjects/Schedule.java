package com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.valueobjects;

import com.br.hugo.ddd.monolithiccargotracker.routing.domain.model.entities.CarrierMovement;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Um cronograma de Viagem
 */
@Embeddable
public class Schedule {
    public static final Schedule EMPTY = new Schedule();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "voyage_id")
    private final List<CarrierMovement> carrierMovements; // Campo final

    public Schedule() {
        this.carrierMovements = Collections.emptyList();
    }

    Schedule(List<CarrierMovement> carrierMovements) {
        this.carrierMovements = carrierMovements != null ? 
            new ArrayList<>(carrierMovements) : Collections.emptyList();
    }

    public List<CarrierMovement> getCarrierMovements() {
        return Collections.unmodifiableList(carrierMovements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(carrierMovements, schedule.carrierMovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrierMovements);
    }
}