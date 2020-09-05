package com.pttrn42.graphs;

import com.pttrn42.graphs.kiwiland.model.Network;
import com.pttrn42.graphs.kiwiland.model.Town;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkParserTest {

    @Test
    void emptyInputGraph_emptyRoutes() {
        assertEquals(new Network(), NetworkParser.parse(""));
    }

    @Test
    void singleRoute_returnValidRoutes() {
        assertEquals(
                new Network().addRoute(new Town("A"), new Town("B"), 5),
                NetworkParser.parse("AB5")
        );
    }

    @Test
    void singleLongRoute_returnValidRoutes() {
        assertEquals(
                new Network().addRoute(new Town("A"), new Town("B"), 10),
                NetworkParser.parse("AB10")
        );
    }

    @Test
    void multipleRoutes_returnValidRoutes() {
        assertEquals(
                new Network()
                        .addRoute(new Town("A"), new Town("B"), 5)
                        .addRoute(new Town("B"), new Town("C"), 4)
                        .addRoute(new Town("C"), new Town("D"), 8),
                NetworkParser.parse("AB5, BC4, CD8")
        );
    }

    @Test
    void multipleRoutes_ignoresInvalidEntry_returnValidRoutes() {
        assertEquals(
                new Network()
                        .addRoute(new Town("A"), new Town("B"), 5)
                        .addRoute(new Town("B"), new Town("C"), 4)
                        .addRoute(new Town("C"), new Town("D"), 8),
                NetworkParser.parse("AB5, BC4, A, CD8")
        );
    }
}