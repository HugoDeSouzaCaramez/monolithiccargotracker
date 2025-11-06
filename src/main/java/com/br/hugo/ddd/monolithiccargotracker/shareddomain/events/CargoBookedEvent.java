package com.br.hugo.ddd.monolithiccargotracker.shareddomain.events;

//SHARED KERNEL: EVENTS
/**
 * Evento de Domínio: CargoBookedEvent
 * 
 * Este evento é disparado quando uma carga é reservada (booked) no Bounded Context de Booking.
 * Ele é parte do Shared Kernel (Núcleo Compartilhado) que é compartilhado entre múltiplos Bounded Contexts.
 * 
 * Conceitos:
 * - Evento de Domínio: Representa algo que aconteceu no domínio e que é de interesse para outros partes do sistema.
 * - Shared Kernel: Este evento está em um pacote compartilhado (shareddomain) e é usado por outros Bounded Contexts (ex: Tracking) para reagir à reserva de uma carga.
 * - Estrutura do Evento: Contém o ID da reserva (bookingId) que é a informação necessária para os assinantes (observadores/consumidores).
 * 
 * Os eventos são disparados pelos Serviços de Aplicação (ex: CargoBookingCommandService) e consumidos por Event Handlers em outros Bounded Contexts.
 */
public class CargoBookedEvent {
    // CONCEITO: Domain Event - Shared between Bounded Contexts
    // CONCEITO: Event Carries State - apenas dados necessários
    // CONCEITO: Simple Data Container - sem comportamento

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
