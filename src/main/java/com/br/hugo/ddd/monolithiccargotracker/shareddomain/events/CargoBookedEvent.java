package com.br.hugo.ddd.monolithiccargotracker.shareddomain.events;

/**
 * Classe de Evento para o Evento de Carga Reservada. Encapsula o identificador
 * da Reserva de Carga
 * para o evento
 */
public class CargoBookedEvent {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
