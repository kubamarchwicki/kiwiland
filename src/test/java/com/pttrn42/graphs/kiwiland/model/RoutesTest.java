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
    void singleRoute_queryNonExistingRoute_returnsNoSuchRoute() {
        Town a=new Town("A"), b=new Town("B");
        Routes routes = new Routes()
                .addRoute(new Route(a, b, 5));

        var distance = routes.distance(a, new Town("C"));
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

    @Test
    void multipleRoutes_returnDistance() {
        Town a=new Town("A"), b=new Town("B"), c=new Town("C");

        Routes routes = new Routes()
                .addRoute(new Route(a, b, 5))
                .addRoute(new Route(b, c, 4));

        var distance = routes.distance(a, b, c);
        Assertions.assertEquals("9", distance);
    }

    @Test
    void multipleRoutes_routesNotExists_returnsNoSuchRoute() {
        Town a=new Town("A"), e=new Town("E"), d=new Town("D");

        Routes routes = new Routes()
                .addRoute(new Route(a, e, 7))
                .addRoute(new Route(d, e, 6));

        var distance = routes.distance(a, e, d);
        Assertions.assertEquals("NO SUCH ROUTE", distance);
    }
}
