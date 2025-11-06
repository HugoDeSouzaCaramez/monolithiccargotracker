package com.br.hugo.ddd.monolithiccargotracker.handling.domain.model.valueobjects;

/**
 * Tipo de evento de manuseio. Ou exige ou proíbe uma associação com movimento
 * de transportadora,
 * nunca é opcional.
 * 
 * Enum com Domain Logic
 * 
 * CONCEITO: Smart Enum com regras de domínio
 */
public enum Type {

    // Carregado na viagem a partir do local do porto
    LOAD(true),
    // Descarregado da viagem para o local do porto
    UNLOAD(true),
    // Recebido pela transportadora
    RECEIVE(false),
    // CargoBookingId reivindicado pelo destinatário
    CLAIM(false),
    // CargoBookingId passou pela alfândega
    CUSTOMS(false);

    private final boolean voyageRequired;

    /**
     * Construtor privado da enumeração.
     *
     * @param voyageRequired indica se uma viagem está associada a este
     *                       tipo de evento ou não
     */
    private Type(boolean voyageRequired) {
        this.voyageRequired = voyageRequired;
    }

    /**
     * CONCEITO: Business Methods no enum
     * 
     * @return Verdadeiro se uma associação de viagem é necessária para este tipo de
     *         evento.
     */
    public boolean requiresVoyage() {
        return voyageRequired;
    }

    /**
     * @return Verdadeiro se uma associação de viagem é proibida para este tipo de
     *         evento.
     */
    public boolean prohibitsVoyage() {
        return !requiresVoyage();
    }

    public boolean sameValueAs(Type other) {
        return other != null && this.equals(other);
    }
}