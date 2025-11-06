package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.valueobjects;

import com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.entities.Location;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

// DOMAIN (Modelo de Domínio)
// (Value Object)
@Embeddable
public class RouteSpecification {
    // CONCEITO: Value Object - Sem identidade, substituível
    // CONCEITO: Embedded Objects - Composição com outros VOs

    private static final long serialVersionUID = 1L;
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "spec_origin_id"))
    private Location origin;
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "spec_destination_id"))
    private Location destination;
    @Temporal(TemporalType.DATE)
    @Column(name = "spec_arrival_deadline")
    @NotNull
    private Date arrivalDeadline;

    public RouteSpecification() {
    }

    /**
     * CONCEITO: Business Rules no constructor
     * 
     * @param origin          local de origem - não pode ser o mesmo que o destino
     * @param destination     local de destino - não pode ser o mesmo que a origem
     * @param arrivalDeadline prazo de chegada
     */
    public RouteSpecification(Location origin, Location destination,
            Date arrivalDeadline) {

        this.origin = origin;
        this.destination = destination;
        this.arrivalDeadline = (Date) arrivalDeadline.clone();
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

    public Date getArrivalDeadline() {
        return new Date(arrivalDeadline.getTime());
    }

}
