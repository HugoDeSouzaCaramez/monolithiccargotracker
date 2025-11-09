package com.br.hugo.ddd.monolithiccargotracker.shareddomain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Representa uma aresta em um caminho através de um grafo, descrevendo a rota
 * de uma
 * carga
 */
public class TransitEdge implements Serializable {
    private final String voyageNumber; // Campo final
    private final String fromUnLocode; // Campo final
    private final String toUnLocode; // Campo final
    private final Date fromDate; // Campo final
    private final Date toDate; // Campo final

    public TransitEdge() {
        this.voyageNumber = null;
        this.fromUnLocode = null;
        this.toUnLocode = null;
        this.fromDate = null;
        this.toDate = null;
    }

    public TransitEdge(String voyageNumber, String fromUnLocode,
            String toUnLocode, Date fromDate, Date toDate) {
        this.voyageNumber = voyageNumber;
        this.fromUnLocode = fromUnLocode;
        this.toUnLocode = toUnLocode;
        this.fromDate = fromDate != null ? new Date(fromDate.getTime()) : null;
        this.toDate = toDate != null ? new Date(toDate.getTime()) : null;
    }

    // Apenas getters
    public String getVoyageNumber() {
        return voyageNumber;
    }

    public String getFromUnLocode() {
        return fromUnLocode;
    }

    public String getToUnLocode() {
        return toUnLocode;
    }

    public Date getFromDate() {
        return fromDate != null ? new Date(fromDate.getTime()) : null;
    }

    public Date getToDate() {
        return toDate != null ? new Date(toDate.getTime()) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TransitEdge))
            return false;
        TransitEdge that = (TransitEdge) o;
        return Objects.equals(voyageNumber, that.voyageNumber) &&
                Objects.equals(fromUnLocode, that.fromUnLocode) &&
                Objects.equals(toUnLocode, that.toUnLocode) &&
                Objects.equals(fromDate, that.fromDate) &&
                Objects.equals(toDate, that.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyageNumber, fromUnLocode, toUnLocode, fromDate, toDate);
    }

    @Override
    public String toString() {
        return "TransitEdge{" + "voyageNumber=" + voyageNumber
                + ", fromUnLocode=" + fromUnLocode + ", toUnLocode="
                + toUnLocode + ", fromDate=" + fromDate
                + ", toDate=" + toDate + '}';
    }
}
