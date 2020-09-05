package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;

import static java.util.Collections.*;
import static java.util.Objects.isNull;

public class Network {
    private final Map<Town, List<Route>> network = new HashMap<>();

    public long trips(Town from, Town to) {
        return trips(from, to, __ -> true);
    }

    public long trips(Town from, Town to, Predicate<Integer> nbOfStopsCriteria) {
        Set<Town> stops = new LinkedHashSet<>(), visited = new LinkedHashSet<>();
        List<Set<Town>> correctRoutes = new ArrayList<>();
        search(from, to, visited, stops, nbOfStopsCriteria, correctRoutes);

        return correctRoutes.size();
    }

    private void search(Town from, Town to, Set<Town> visited, Set<Town> stops, Predicate<Integer> nbOfStopsCriteria, List<Set<Town>> correctRoutes) {
        visited.add(from);
        for (Route r: network.getOrDefault(from, emptyList())) {
//            System.out.println("routes from(" + from +"): " + r);
            stops.add(r.to());
            if (r.to().equals(to) && nbOfStopsCriteria.test(stops.size())) {
                correctRoutes.add(stops);
                return;
            }

            if (!visited.contains(r.to())) {
                search(r.to(), to, visited, stops, nbOfStopsCriteria, correctRoutes);
            }

            stops.remove(r.to());
        }

        visited.remove(from);
    }

    public String distance(Town... towns) {
        //TODO: maybe try monad?
        //TODO: more functional approach
        try {
            Integer sumDistance = 0;
            for (int i = 0; i<towns.length-1; i++) {
                sumDistance += distance(towns[i], towns[i+1]);
            }
            return sumDistance.toString();
        } catch (NoSuchRouteException ex) {
            return ex.getMessage();
        }
    }

    private Integer distance(Town from, Town to) {
        return network.values().stream()
                .flatMap(Collection::stream)
                .filter(route -> route.from().equals(from) && route.to().equals(to))
                .findFirst()
                .map(Route::distance)
                .orElseThrow(() -> new NoSuchRouteException());
    }

    public Network addRoute(Town from, Town to, Integer distance) {
        addRoute(new Route(from, to, distance));
        return this;
    }

    public Network addRoute(Route route) {
        network.compute(route.from(),
                (__, adjacencyList) -> isNull(adjacencyList)? List.of(route) : append(adjacencyList, route)
        );
        return this;
    }

    private <T> List<T> append(List<T> to, T element) {
        var list = new ArrayList<>(to);
        list.add(element);
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Network routes1 = (Network) o;
        return Objects.equals(network, routes1.network);
    }

    @Override
    public String toString() {
        return "Routes{" +
                "routes=" + network +
                '}';
    }

    static class NoSuchRouteException extends RuntimeException {
        public NoSuchRouteException() {
            super("NO SUCH ROUTE");
        }
    }
}
