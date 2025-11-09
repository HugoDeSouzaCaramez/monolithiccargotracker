package com.br.hugo.ddd.monolithiccargotracker.shareddomain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class TransitPath implements Serializable {
    private final List<TransitEdge> transitEdges; // Campo final

    public TransitPath() {
        this.transitEdges = new ArrayList<>();
    }

    public TransitPath(List<TransitEdge> transitEdges) {
        this.transitEdges = transitEdges != null ? new ArrayList<>(transitEdges) : new ArrayList<>();
    }

    public List<TransitEdge> getTransitEdges() {
        return Collections.unmodifiableList(transitEdges);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TransitPath))
            return false;
        TransitPath that = (TransitPath) o;
        return Objects.equals(transitEdges, that.transitEdges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transitEdges);
    }

    @Override
    public String toString() {
        return "TransitPath{" + "transitEdges=" + transitEdges + '}';
    }
}
