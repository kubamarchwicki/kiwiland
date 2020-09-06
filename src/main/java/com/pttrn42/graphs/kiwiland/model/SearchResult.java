package com.pttrn42.graphs.kiwiland.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchResult {
    record Trip(List<Town> stops, Integer distance){}

    private final Town source;
    private final Set<Trip> trips;

    public SearchResult(Town source) {
        this.source = source;
        this.trips = new HashSet<>();
    }

    public void append(List<Town> stops, Integer distance) {
        trips.add(new Trip(stops, distance));
    }

    public long size() {
        return trips.size();
    }

    public long shortest() {
        return trips.stream()
                .sorted(Comparator.comparing(Trip::distance))
                .map(Trip::distance)
                .findFirst()
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "\n\tsource=" + source +
                "\n\ttrips=" + trips +
                '}';
    }
}
