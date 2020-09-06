package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class SearchResult {
    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    record Trip(Set<Town> stops, Integer distance){}

    List<Trip> trips = new ArrayList<>();

    void append(Trip trip) {
        trips.add(trip);
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
