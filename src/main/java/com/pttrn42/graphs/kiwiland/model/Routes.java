package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Routes {
    private final List<Route> routes = new ArrayList<>();

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
        return routes.stream()
                .filter(route -> route.from().equals(from) && route.to().equals(to))
                .findFirst()
                .map(Route::distance)
                .orElseThrow(() -> new NoSuchRouteException());
    }

    public Routes addRoute(Town from, Town to, Integer distance) {
        routes.add(new Route(from, to, distance));
        return this;
    }

    public Routes addRoute(Route route) {
        routes.add(route);
        return this;
    }

    static class NoSuchRouteException extends RuntimeException {

        public NoSuchRouteException() {
            super("NO SUCH ROUTE");
        }
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
}
