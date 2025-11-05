package com.br.hugo.ddd.monolithiccargotracker.tracking.interfaces.events;

import com.br.hugo.ddd.monolithiccargotracker.shareddomain.events.CargoBookedEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class CargoBookedEventHandler {

    public void testEventObserving(@Observes CargoBookedEvent event) {
        // Processamento de um evento
        System.out.println("***Just a Test***" + event.getId());
    }
}
