package com.br.hugo.ddd.monolithiccargotracker.shareddomain.events;

// SHARED KERNEL: EVENTS
/**
 * Classe de Evento para o Evento de Carga Roteada. Encapsula a Carga
 */
public class CargoRoutedEvent {
    // CONCEITO: Complex Domain Event com nested data

    private CargoRoutedEventData cargoRoutedEventData;

    public CargoRoutedEvent() {
    }

    public void setContent(CargoRoutedEventData cargoRoutedEventData) {
        this.cargoRoutedEventData = cargoRoutedEventData;
    }

    public CargoRoutedEventData getContent() {
        return cargoRoutedEventData;
    }
}
