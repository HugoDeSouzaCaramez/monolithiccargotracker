package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

// DOMAIN (Modelo de Domínio)
/**
 * Uma atividade de manuseio representa como e onde uma carga pode ser
 * manuseada, e pode
 * ser usada para expressar previsões sobre o que se espera que aconteça com uma
 * carga
 * no futuro.
 *
 */
@Embeddable
public class CargoHandlingActivity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "next_expected_handling_event_type")
    private final String type; // Campo final
    
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "next_expected_location_id"))
    private final Location location; // Campo final
    
    @Column(name = "next_expected_voyage_id")
    @AttributeOverride(name = "voyageNumber", column = @Column(name = "next_expected_voyage_id"))
    private final Voyage voyage; // Campo final

    public CargoHandlingActivity() {
        this.type = null;
        this.location = null;
        this.voyage = null;
    }

    public CargoHandlingActivity(String type, Location location) {
        this.type = type;
        this.location = location;
        this.voyage = null;
    }

    public CargoHandlingActivity(String type, Location location, Voyage voyage) {
        this.type = type;
        this.location = location;
        this.voyage = voyage;
    }

    // Apenas getters
    public String getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CargoHandlingActivity)) return false;
        CargoHandlingActivity that = (CargoHandlingActivity) o;
        return Objects.equals(type, that.type) &&
               Objects.equals(location, that.location) &&
               Objects.equals(voyage, that.voyage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, location, voyage);
    }
}