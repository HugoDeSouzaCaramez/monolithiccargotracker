package com.br.hugo.ddd.monolithiccargotracker.handling.application.internal.commandservices.commandservices;

import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.aggregates.HandlingActivity;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.commands.HandlingActivityRegistrationCommand;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.CargoBookingId;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.Location;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.Type;
import com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects.VoyageNumber;
import com.br.hugo.ddd.monolithiccargotracker.handling.infrastructure.repositories.jpa.HandlingActivityRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Logger;

/**
 * Serviço de Aplicação para Registrar Atividades de Manuseio
 * 
 * Esta classe é um Serviço de Aplicação no Bounded Context de Handling, responsável por registrar atividades de manuseio de cargas.
 * 
 * Conceitos:
 * - Serviço de Aplicação: Orquestra a execução do comando HandlingActivityRegistrationCommand, que altera o estado do sistema (registra uma atividade de manuseio).
 * - Comando: HandlingActivityRegistrationCommand é um comando que contém os dados necessários para registrar uma atividade de manuseio.
 * - Validação: O método isValidCommand valida os dados de entrada do comando.
 * - Factory: Utiliza a lógica de fábrica para criar a instância do agregado HandlingActivity, dependendo se a atividade requer uma viagem ou não.
 * - Repositório: Utiliza o HandlingActivityRepository para persistir o agregado.
 * 
 * Esta classe é um CDI Bean com escopo de aplicação.
 */
@ApplicationScoped
public class HandlingActivityRegistrationCommandService {

    private static final Logger logger = Logger.getLogger(HandlingActivityRegistrationCommandService.class.getName());

    @Inject
    private HandlingActivityRepository handlingActivityRepository;

    /**
     * Método de Comando do Serviço para registrar uma nova Atividade de Manuseio
     */
    @Transactional
    public void registerHandlingActivityService(
            HandlingActivityRegistrationCommand handlingActivityRegistrationCommand) {
        if (!isValidCommand(handlingActivityRegistrationCommand)) {
            throw new IllegalArgumentException("Invalid handling activity registration command");
        }

        try {
            CargoBookingId cargoBookingId = new CargoBookingId(handlingActivityRegistrationCommand.getBookingId());
            Location location = new Location(handlingActivityRegistrationCommand.getUnLocode());
            Type type = Type.valueOf(handlingActivityRegistrationCommand.getHandlingType().toUpperCase());

            HandlingActivity handlingActivity;

            if (type.requiresVoyage()) {
                VoyageNumber voyageNumber = new VoyageNumber(handlingActivityRegistrationCommand.getVoyageNumber());
                handlingActivity = new HandlingActivity(
                        cargoBookingId,
                        handlingActivityRegistrationCommand.getCompletionTime(),
                        type,
                        location,
                        voyageNumber);
            } else {
                handlingActivity = new HandlingActivity(
                        cargoBookingId,
                        handlingActivityRegistrationCommand.getCompletionTime(),
                        type,
                        location);
            }

            handlingActivityRepository.store(handlingActivity);

            logger.info("Registered handling activity for booking ID: "
                    + handlingActivityRegistrationCommand.getBookingId());

        } catch (IllegalArgumentException e) {
            logger.severe("Invalid handling type: " + handlingActivityRegistrationCommand.getHandlingType());
            throw new IllegalArgumentException(
                    "Invalid handling type: " + handlingActivityRegistrationCommand.getHandlingType(), e);
        } catch (Exception e) {
            logger.severe("Error registering handling activity: " + e.getMessage());
            throw new RuntimeException("Error registering handling activity", e);
        }
    }

    private boolean isValidCommand(HandlingActivityRegistrationCommand command) {
        return command != null &&
                command.getBookingId() != null && !command.getBookingId().trim().isEmpty() &&
                command.getHandlingType() != null && !command.getHandlingType().trim().isEmpty() &&
                command.getUnLocode() != null && !command.getUnLocode().trim().isEmpty() &&
                command.getCompletionTime() != null;
    }
}
