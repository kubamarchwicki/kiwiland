package com.pttrn42.graphs.kiwiland.model;

import java.util.ArrayList;
import java.util.List;

public class Routes {
    private final List<Route> routes = new ArrayList<>();

    public String distance(Town from, Town to) {
        return routes.stream()
                .filter(route -> route.from().equals(from) && route.to().equals(to))
                .findFirst()
                .map(Route::distance)
                .map(String::valueOf)
                .orElse("NO SUCH ROUTE");
    }

    public Routes addRoute(Route route) {
        routes.add(route);
        return this;
    }
}
