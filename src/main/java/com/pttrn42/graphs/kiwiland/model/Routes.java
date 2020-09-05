package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

public class Routes {
    private final Map<Town, List<Route>> routes = new HashMap<>();

    public long trips(Town from, Town to) {
        List<Set<Town>> correct = new ArrayList<>();

        for (Route r: routes.getOrDefault(from, emptyList())) {
            Set<Town> visited = new HashSet<>();
            Stack<Town> stack = new Stack<>();

            visited.add(r.from());
            stack.push(r.to());

            while (!stack.isEmpty()) {
                Town town = stack.pop();
                if (!visited.contains(town)) {
                    visited.add(town);
                    for (Route routesFromTown: routes.getOrDefault(town, emptyList())) {
                        stack.push(routesFromTown.to());
                    }
                }

                if (town.equals(to)) {
                    correct.add(visited);
                }

            }
        }

        return correct.size();
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
        return routes.values().stream()
                .flatMap(Collection::stream)
                .filter(route -> route.from().equals(from) && route.to().equals(to))
                .findFirst()
                .map(Route::distance)
                .orElseThrow(() -> new NoSuchRouteException());
    }

    public Routes addRoute(Town from, Town to, Integer distance) {
        addRoute(new Route(from, to, distance));
        return this;
    }

    public Routes addRoute(Route route) {
        routes.compute(route.from(),
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
        Routes routes1 = (Routes) o;
        return Objects.equals(routes, routes1.routes);
    }

    @Override
    public String toString() {
        return "Routes{" +
                "routes=" + routes +
                '}';
    }

    static class NoSuchRouteException extends RuntimeException {
        public NoSuchRouteException() {
            super("NO SUCH ROUTE");
        }
    }
}
