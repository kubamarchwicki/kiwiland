package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class SearchResult {
    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    record Trip(Set<Town> stops, Integer distance){}

    private final Town source;
    private final List<Trip> trips;

    public SearchResult(Town source) {
        this.source = source;
        this.trips = new ArrayList<>();
    }

    Town source() {
        return source;
    }

    void append(Trip trip) {
        trips.add(trip);
    }

    long size() {
        return trips.size();
    }

    long shortest() {
        trips.forEach(s -> LOG.fine(s.toString()));

        return trips.stream()
                .sorted(Comparator.comparing(Trip::distance))
                .map(Trip::distance)
                .findFirst()
                .orElse(Integer.MAX_VALUE);
    }
}
