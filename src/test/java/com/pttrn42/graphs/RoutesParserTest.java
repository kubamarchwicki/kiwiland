package com.pttrn42.graphs;

import com.pttrn42.graphs.kiwiland.model.Routes;
import com.pttrn42.graphs.kiwiland.model.Town;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoutesParserTest {

    @Test
    void emptyInputGraph_emptyRoutes() {
        assertEquals(new Routes(), RoutesParser.parse(""));
    }

    @Test
    void singleRoute_returnValidRoutes() {
        assertEquals(
                new Routes().addRoute(new Town("A"), new Town("B"), 5),
                RoutesParser.parse("AB5")
        );
    }

    @Test
    void singleLongRoute_returnValidRoutes() {
        assertEquals(
                new Routes().addRoute(new Town("A"), new Town("B"), 10),
                RoutesParser.parse("AB10")
        );
    }

    @Test
    void multipleRoutes_returnValidRoutes() {
        assertEquals(
                new Routes()
                        .addRoute(new Town("A"), new Town("B"), 5)
                        .addRoute(new Town("B"), new Town("C"), 4)
                        .addRoute(new Town("C"), new Town("D"), 8),
                RoutesParser.parse("AB5, BC4, CD8")
        );
    }

    @Test
    void multipleRoutes_ignoresInvalidEntry_returnValidRoutes() {
        assertEquals(
                new Routes()
                        .addRoute(new Town("A"), new Town("B"), 5)
                        .addRoute(new Town("B"), new Town("C"), 4)
                        .addRoute(new Town("C"), new Town("D"), 8),
                RoutesParser.parse("AB5, BC4, A, CD8")
        );
    }
}