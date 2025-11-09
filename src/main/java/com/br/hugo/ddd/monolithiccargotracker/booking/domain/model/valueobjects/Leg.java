package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

// DOMAIN (Modelo de Domínio)
@Entity
public class Leg {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private final Voyage voyage; // Campo final
    
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "load_location_id"))
    private final Location loadLocation; // Campo final
    
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "unload_location_id"))
    private final Location unloadLocation; // Campo final
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "load_time")
    @NotNull
    private final Date loadTime; // Campo final
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "unload_time")
    @NotNull
    private final Date unloadTime; // Campo final

    public Leg() {
        this.voyage = null;
        this.loadLocation = null;
        this.unloadLocation = null;
        this.loadTime = null;
        this.unloadTime = null;
    }

    public Leg(Voyage voyage, Location loadLocation,
            Location unloadLocation, Date loadTime, Date unloadTime) {
        this.voyage = voyage;
        this.loadLocation = loadLocation;
        this.unloadLocation = unloadLocation;
        this.loadTime = loadTime != null ? new Date(loadTime.getTime()) : null;
        this.unloadTime = unloadTime != null ? new Date(unloadTime.getTime()) : null;
    }

    // Apenas getters
    public Voyage getVoyage() {
        return voyage;
    }

    public Location getLoadLocation() {
        return loadLocation;
    }

    public Location getUnloadLocation() {
        return unloadLocation;
    }

    public Date getLoadTime() {
        return loadTime != null ? new Date(loadTime.getTime()) : null;
    }

    public Date getUnloadTime() {
        return unloadTime != null ? new Date(unloadTime.getTime()) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Leg)) return false;
        Leg leg = (Leg) o;
        return Objects.equals(voyage, leg.voyage) &&
               Objects.equals(loadLocation, leg.loadLocation) &&
               Objects.equals(unloadLocation, leg.unloadLocation) &&
               Objects.equals(loadTime, leg.loadTime) &&
               Objects.equals(unloadTime, leg.unloadTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyage, loadLocation, unloadLocation, loadTime, unloadTime);
    }
}