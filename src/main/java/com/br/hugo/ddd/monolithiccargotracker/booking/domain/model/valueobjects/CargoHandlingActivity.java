package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.entities.Location;

import javax.persistence.*;
import java.io.Serializable;

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
    private String type;
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "next_expected_location_id"))
    private Location location;
    @Column(name = "next_expected_voyage_id")
    @AttributeOverride(name = "voyageNumber", column = @Column(name = "next_expected_voyage_id"))
    private Voyage voyage;

    public CargoHandlingActivity() {
    }

    public CargoHandlingActivity(String type, Location location) {
        this.type = type;
        this.location = location;

    }

    public CargoHandlingActivity(String type, Location location,
            Voyage voyage) {
        this.type = type;
        this.location = location;
        this.voyage = voyage;
    }

    public String getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public Voyage getVoyage() {
        return voyage;
    }

}
