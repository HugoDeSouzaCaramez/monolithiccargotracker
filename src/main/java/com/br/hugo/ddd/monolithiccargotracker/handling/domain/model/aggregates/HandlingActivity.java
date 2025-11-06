package com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.aggregates;

import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.CargoBookingId;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.Location;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.Type;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.VoyageNumber;

import javax.persistence.*;
import java.util.Date;

/**
 * Agregado Raiz HandlingActivity
 * 
 * Esta classe representa o Agregado Raiz no Bounded Context de Handling. Ela registra uma atividade de manuseio de uma carga.
 * 
 * Conceitos:
 * - Agregado: HandlingActivity é um agregado que contém entidades e objetos de valor (ex: Location, VoyageNumber, Type) e é a raiz do agregado.
 * - Construtores: Possui dois construtores, um para atividades que requerem viagem e outro para as que não requerem, garantindo as invariantes.
 * - Domain Richness: O agregado possui atributos de negócio (type, location, etc.) e validações (ex: se o tipo de atividade requer ou proíbe viagem).
 * - JPA: É uma entidade JPA, mapeada para a tabela "handling_activity".
 * Aggregate Root
 * 
 * O agregado HandlingActivity é uma JPA Entity, e seus componentes são mapeados como Embedded (objetos de valor) ou diretamente como colunas.
 */
@Entity
@NamedQuery(name = "HandlingEvent.findByBookingId", query = "Select e from HandlingActivity e where e.cargoBookingId.bookingId = :bookingId")
@Table(name = "handling_activity")
public class HandlingActivity {
    // CONCEITO: Aggregate Root com construtores específicos
    // CONCEITO: Domain Constraints no constructor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private Type type;
    @Embedded
    private VoyageNumber voyageNumber;
    @Embedded
    private Location location;
    @Temporal(TemporalType.DATE)
    @Column(name = "event_completion_time")
    private Date completionTime;

    @Embedded
    private CargoBookingId cargoBookingId;

    public HandlingActivity() {
    }

    /**
     * Construtor da Atividade de Manuseio - Com VoyageNumber
     * 
     * @param cargoBookingId
     * @param completionTime
     * @param type
     * @param location
     * @param voyageNumber
     */
    public HandlingActivity(CargoBookingId cargoBookingId, Date completionTime,
            Type type, Location location, VoyageNumber voyageNumber) {

         // CONCEITO: Business Rules no constructor
        if (type.prohibitsVoyage()) {
            throw new IllegalArgumentException(
                    "VoyageNumber is not allowed with event type " + type);
        }

        this.voyageNumber = voyageNumber;
        this.completionTime = (Date) completionTime.clone();
        this.type = type;
        this.location = location;
        this.cargoBookingId = cargoBookingId;
    }

    /**
     * Construtor da Atividade de Manuseio - Sem VoyageNumber
     * 
     * CONCEITO: Factory Method via constructor overloading
     * 
     * @param cargoBookingId
     * @param completionTime
     * @param type
     * @param location
     */
    public HandlingActivity(CargoBookingId cargoBookingId, Date completionTime,
            Type type, Location location) {

        System.out.println("***Type is**" + type);
        if (type.requiresVoyage()) {
            throw new IllegalArgumentException(
                    "VoyageNumber is required for event type " + type);
        }

        this.completionTime = (Date) completionTime.clone();
        this.type = type;
        this.location = location;
        this.cargoBookingId = cargoBookingId;
        this.voyageNumber = null;
    }

    public Type getType() {
        return this.type;
    }

    public VoyageNumber getVoyage() {
        return this.voyageNumber;
    }

    public Date getCompletionTime() {
        return new Date(this.completionTime.getTime());
    }

    public Location getLocation() {
        return this.location;
    }

    public CargoBookingId getCargoBookingId() {
        return this.cargoBookingId;
    }

}
