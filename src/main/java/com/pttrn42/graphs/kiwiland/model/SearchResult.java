package com.pttrn42.graphs.kiwiland.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class SearchResult {
    public record Trip(List<Town> stops, Integer distance){}

    private final Town source;
    private final Set<Trip> trips;
    private final Predicate<Trip> validResult;

    public SearchResult(Town source, Predicate<Trip> validResult) {
        this.source = source;
        this.trips = new HashSet<>();
        this.validResult = validResult;
    }

    public boolean append(List<Town> stops, Integer distance) {
        Trip trip = new Trip(stops, distance);

        System.out.println("distance=" + distance + " is valid=" + validResult.test(trip));
        if (validResult.test(trip)) {
            return trips.add(trip);
        }

        return false;
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
        StringBuilder b = new StringBuilder();
        trips.forEach(t -> b.append("\n\t\t").append(t.toString()));
        return "SearchResult{" +
                "\n\tsource=" + source +
                "\n\ttrips=[" + b.toString() +
                "\n\t]" +
                '}';
    }
}
