package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class SearchResult {
    record Trip(Set<Town> stops, Integer distance){}

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final Town source;
    private final List<Trip> trips;

    public SearchResult(Town source) {
        this.source = source;
        this.trips = new ArrayList<>();
    }

    public Town source() {
        return source;
    }

    public void append(Set<Town> stops, Integer distance) {
        trips.add(new Trip(new LinkedHashSet<>(stops), distance));
    }

    public long size() {
        return trips.size();
    }

    public long shortest() {
        trips.forEach(s -> LOG.fine(s.toString()));

        return trips.stream()
                .sorted(Comparator.comparing(Trip::distance))
                .map(Trip::distance)
                .findFirst()
                .orElse(Integer.MAX_VALUE);
    }
}
