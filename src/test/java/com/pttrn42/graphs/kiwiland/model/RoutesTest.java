package com.pttrn42.graphs.kiwiland.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoutesTest {

    @Test
    void noRoutes_returnsNoSuchRoute() {
        Routes routes = new Routes();

        var distance = routes.distance(new Town("A"), new Town("B"));
        Assertions.assertEquals("NO SUCH ROUTE", distance);
    }

    @Test
    void singleRoute_returnsRouteDistance() {
        Town a=new Town("A"), b=new Town("B");
        Routes routes = new Routes()
            .addRoute(new Route(a, b, 5));

        var distance = routes.distance(a, b);
        Assertions.assertEquals("5", distance);
    }

}
