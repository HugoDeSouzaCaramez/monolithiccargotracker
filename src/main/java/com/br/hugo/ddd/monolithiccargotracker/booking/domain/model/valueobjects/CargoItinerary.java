package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// DOMAIN (Modelo de Domínio)
@Embeddable
public class CargoItinerary {
    public static final CargoItinerary EMPTY_ITINERARY = new CargoItinerary();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cargo_id")
    private final List<Leg> legs; // Campo final

    public CargoItinerary() {
        this.legs = Collections.emptyList();
    }

    public CargoItinerary(List<Leg> legs) {
        this.legs = legs != null ? new ArrayList<>(legs) : Collections.emptyList();
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(legs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CargoItinerary))
            return false;
        CargoItinerary that = (CargoItinerary) o;
        return Objects.equals(legs, that.legs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs);
    }
}
