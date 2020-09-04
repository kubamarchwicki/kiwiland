package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.List;

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

    public Routes addRoute(Route route) {
        routes.add(route);
        return this;
    }

    static class NoSuchRouteException extends RuntimeException {

        public NoSuchRouteException() {
            super("NO SUCH ROUTE");
        }
    }
}
