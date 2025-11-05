package com.br.hugo.ddd.monolithiccargotracker.tracking.application.internal.commandservices;

import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.aggregates.TrackingActivity;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.aggregates.TrackingNumber;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.commands.AddTrackingEventCommand;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.commands.AssignTrackingNumberCommand;
import com.br.hugo.ddd.monolithiccargotracker.tracking.domain.model.entities.TrackingBookingId;
import com.br.hugo.ddd.monolithiccargotracker.tracking.infrastructure.repositories.jpa.TrackingRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Classe de Serviço de Aplicação para o Serviço de Comando de Rastreamento
 */
@ApplicationScoped
public class AssignTrackingIdCommandService {

    @Inject
    private TrackingRepository trackingRepository;

    /**
     * Método de Comando do Serviço para atribuir um ID de rastreamento à carga
     * reservada
     * 
     * @return Número de Rastreamento da Carga
     */
    @Transactional // Inicia a Transação de Banco de Dados
    public TrackingNumber assignTrackingNumberToCargo(AssignTrackingNumberCommand assignTrackingNumberCommand) {
        String trackingNumber = trackingRepository.nextTrackingNumber();
        assignTrackingNumberCommand.setTrackingNumber(trackingNumber);
        TrackingActivity activity = new TrackingActivity(assignTrackingNumberCommand);
        System.out.println("***Going to store in repository");
        trackingRepository.store(activity); // Store the Cargo

        return new TrackingNumber(trackingNumber);
    }

    /**
     * Método de Comando do Serviço para atribuir uma rota a uma Carga
     * 
     * @param addTrackingEventCommand
     */
    @Transactional
    public void addTrackingEvent(AddTrackingEventCommand addTrackingEventCommand) {
        TrackingActivity trackingActivity = trackingRepository.findByBookingId(
                new TrackingBookingId(addTrackingEventCommand.getBookingId()));

        trackingActivity.addTrackingEvent(addTrackingEventCommand);
        trackingRepository.store(trackingActivity);
    }

}
