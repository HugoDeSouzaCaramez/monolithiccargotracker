package com.br.hugo.ddd.monolithiccargotracker.shareddomain.events;

public class CargoHandledEvent {

    private CargoHandledEventData cargoHandledEventData;

    public CargoHandledEvent() {
    }

    public void setContent(CargoHandledEventData cargoHandledEventData) {
        this.cargoHandledEventData = cargoHandledEventData;
    }

    public CargoHandledEventData getContent() {
        return cargoHandledEventData;
    }
}
