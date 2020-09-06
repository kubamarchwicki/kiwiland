package com.pttrn42.graphs.kiwiland.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NetworkTest {

    // routes
    @Test
    void noRoutes_returnsNoSuchRoute() {
        Network routes = new Network();

        var distance = routes.distance(new Town("A"), new Town("B"));
        Assertions.assertEquals("NO SUCH ROUTE", distance);
    }

    @Test
    void singleRoute_queryNonExistingRoute_returnsNoSuchRoute() {
        Town a=new Town("A"), b=new Town("B");
        Network routes = new Network()
                .addRoute(new Route(a, b, 5));

        var distance = routes.distance(a, new Town("C"));
        Assertions.assertEquals("NO SUCH ROUTE", distance);
    }

    @Test
    void singleRoute_returnsRouteDistance() {
        Town a=new Town("A"), b=new Town("B");
        Network routes = new Network()
            .addRoute(new Route(a, b, 5));

        var distance = routes.distance(a, b);
        Assertions.assertEquals("5", distance);
    }

    @Test
    void multipleRoutes_returnDistance() {
        Town a=new Town("A"), b=new Town("B"), c=new Town("C");

        Network routes = new Network()
                .addRoute(new Route(a, b, 5))
                .addRoute(new Route(b, c, 4));

        var distance = routes.distance(a, b, c);
        Assertions.assertEquals("9", distance);
    }

    @Test
    void multipleRoutes_routesNotExists_returnsNoSuchRoute() {
        Town a=new Town("A"), e=new Town("E"), d=new Town("D");

        Network routes = new Network()
                .addRoute(new Route(a, e, 7))
                .addRoute(new Route(d, e, 6));

        var distance = routes.distance(a, e, d);
        Assertions.assertEquals("NO SUCH ROUTE", distance);
    }

    // trips
    @Test
    void noRoutes_returnsZeroTrips() {
        Network routes = new Network();

        var trips = routes.trips(new Town("A"), new Town("B"));
        Assertions.assertEquals(0, trips);
    }

    @Test
    void singleRoute_returnsSingleTrip() {
        Town a=new Town("A"), b=new Town("B");
        Network routes = new Network()
                .addRoute(new Route(a, b, 5));

        var trips = routes.trips(new Town("A"), new Town("B"));
        Assertions.assertEquals(1, trips);
    }

    @Test
    void multipleRoutes_returnsThreeRoutes() {
        Network routes = multipleRoutes();
        var trips = routes.trips(new Town("A"), new Town("F"));
        Assertions.assertEquals(3, trips);
    }

    @Test
    void multipleRoutes_cycle_returnsSingleRoute() {
        Network routes = multipleRoutes();
        var trips = routes.trips(new Town("B"), new Town("B"));
        Assertions.assertEquals(1, trips);
    }

    @Test
    void multipleRoutes_maximumLength() {
        Network routes = multipleRoutes();
        var trips = routes.trips(new Town("A"), new Town("F"), stops -> stops < 3);
        Assertions.assertEquals(1, trips);
    }

    @Test
    void multipleRoutes_exactLength() {
        Network routes = multipleRoutes();
        var trips = routes.trips(new Town("A"), new Town("F"), stops -> stops == 3);
        Assertions.assertEquals(1, trips);
    }

    @Test
    void findShortest_multipleRoutes() {
        Network routes = multipleRoutes();
        var trips = routes.shortest(new Town("A"), new Town("E"));
        Assertions.assertEquals(7, trips);
    }

    @Test
    void findShortest_cycle() {
        Network routes = multipleRoutes();
        var trips = routes.shortest(new Town("B"), new Town("B"));
        Assertions.assertEquals(7, trips);
    }

    Network multipleRoutes() {
        Town a=new Town("A"), b=new Town("B"), c=new Town("C"), d=new Town("D"), e=new Town("E"), f=new Town("F");

        return new Network()
                .addRoute(new Route(a, b, 1))
                .addRoute(new Route(b, c, 2))
                .addRoute(new Route(b, d, 3))
                .addRoute(new Route(c, e, 4))
                .addRoute(new Route(e, b, 1))
                .addRoute(new Route(e, f, 2))
                .addRoute(new Route(d, f, 4))
                .addRoute(new Route(a, f, 10));
    }
}
