package com.br.hugo.ddd.monolithiccargotracker.booking.domain.model.commands;

/**
 * Classe de Comando para atribuir uma rota a uma carga reservada
 */
public class RouteCargoCommand {
    private String cargoBookingId;

    public RouteCargoCommand() {
    }

    public RouteCargoCommand(String cargoBookingId) {
        this.setCargoBookingId(cargoBookingId);
    }

    public String getCargoBookingId() {
        return cargoBookingId;
    }

    public void setCargoBookingId(String cargoBookingId) {
        this.cargoBookingId = cargoBookingId;
    }

}
