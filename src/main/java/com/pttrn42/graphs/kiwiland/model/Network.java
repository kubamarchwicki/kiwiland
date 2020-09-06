package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

public class Network {
    private final Map<Town, List<Route>> network = new HashMap<>();

    public long shortest(Town from, Town to) {
        return search(from, to, __ -> true)
                .shortest();
    }

    public long trips(Town from, Town to) {
        return trips(from, to, __ -> true);
    }

    public long trips(Town from, Town to, Predicate<Integer> nbOfStopsCriteria) {
        SearchResult results = search(from, to, nbOfStopsCriteria);
        return results.size();
    }

    private SearchResult search(Town from, Town to, Predicate<Integer> nbOfStopsCriteria) {
        Set<Town> stops = new LinkedHashSet<>(), visited = new LinkedHashSet<>();
        SearchResult results = new SearchResult();
        search(from, to, visited, stops, nbOfStopsCriteria, results);

        return results;
    }

    private void search(Town from, Town to, Set<Town> visited, Set<Town> stops, Predicate<Integer> nbOfStopsCriteria, SearchResult results) {
        visited.add(from);
        for (Route r: network.getOrDefault(from, emptyList())) {
            System.out.println("routes from(" + from +"): " + r);
            if (r.to().equals(to)) {
                stops.add(r.to());
                System.out.println("visited = " + visited);
                System.out.println("stops = " + stops);
                if (nbOfStopsCriteria.test(stops.size())) {
                    results.append(new SearchResult.Trip(new LinkedHashSet<>(stops), _distance(visited.stream().findFirst().get(), stops.toArray(new Town[]{}))));
                }
                stops.remove(r.to());
                break;
            }

            if (!visited.contains(r.to())) {
                stops.add(r.to());
                search(r.to(), to, visited, stops, nbOfStopsCriteria, results);
                stops.remove(r.to());
            }
        }

        visited.remove(from);
    }

    public String distance(Town... towns) {
        //TODO: maybe try monad?
        //TODO: more functional approach
        try {
            return _distance(towns).toString();
        } catch (NoSuchRouteException ex) {
            return ex.getMessage();
        }
    }

    private Integer _distance(Town source, Town... towns) {
        Integer sumDistance = _distance(source, towns[0]);
        for (int i = 0; i<towns.length-1; i++) {
            sumDistance += _distance(towns[i], towns[i+1]);
        }
        return sumDistance;
    }

    private Integer _distance(Town... towns) {
        Integer sumDistance = 0;
        for (int i = 0; i<towns.length-1; i++) {
            sumDistance += _distance(towns[i], towns[i+1]);
        }
        return sumDistance;
    }

    private Integer _distance(Town from, Town to) {
        System.out.println("Calculating route from = " + from + " to = " + to);
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
