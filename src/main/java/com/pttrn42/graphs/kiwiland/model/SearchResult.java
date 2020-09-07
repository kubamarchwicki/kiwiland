package com.pttrn42.graphs.kiwiland.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class SearchResult {
    public record Trip(List<Town> stops, Integer distance){}

    private final AtomicBoolean valid = new AtomicBoolean(true);
    private final Town source;
    private final Set<Trip> trips;
    private final Predicate<Trip> validResult;

    public SearchResult(Town source, Predicate<Trip> validResult) {
        this.source = source;
        this.trips = new HashSet<>();
        this.validResult = validResult;
    }

    public void append(List<Town> stops, Integer distance) {
        Trip trip = new Trip(stops, distance);

        if (!validResult.test(trip)) {
            this.valid.set(false);
        } else {
            trips.add(trip);
        }
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

    public boolean valid() {
        return valid.get();
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "\n\tsource=" + source +
                "\n\ttrips=" + trips +
                '}';
    }
}
