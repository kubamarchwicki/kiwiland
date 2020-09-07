package com.pttrn42.graphs;

import com.pttrn42.graphs.kiwiland.model.Network;
import com.pttrn42.graphs.kiwiland.model.Town;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignmentExamplesTest {

    Network routes = NetworkParser.parse("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

    @Test
    void ex_1() {
        var distance = routes.distance(t("A"), t("B"), t("C"));
        Assertions.assertEquals("9", distance);
    }

    @Test
    void ex_2() {
        var  distance = routes.distance(t("A"), t("D"));
        Assertions.assertEquals("5", distance);
    }

    @Test
    void ex_3() {
        var distance = routes.distance(t("A"), t("D"), t("C"));
        Assertions.assertEquals("13", distance);
    }

    @Test
    void ex_4() {
        var distance = routes.distance(t("A"), t("E"), t("B"), t("C"), t("D"));
        Assertions.assertEquals("22", distance);
    }

    @Test
    void ex_5() {
        var distance = routes.distance(t("A"), t("E"), t("D"));
        Assertions.assertEquals("NO SUCH ROUTE", distance);
    }

    @Test
    void ex_6() {
        long trips = routes.trips(t("C"), t("C"), l -> l <= 3);
        Assertions.assertEquals(2, trips);
    }

    @Test
    void ex_7() {
        long trips = routes.trips(new Town("A"), new Town("C"), l -> l == 4);
        Assertions.assertEquals(3, trips);
    }

    @Test
    void ex_8() {
        long shortest = routes.shortest(t("A"), t("C"));
        Assertions.assertEquals(9, shortest);
    }

    @Test
    void ex_9() {
        long shortest = routes.shortest(t("B"), t("B"));
        Assertions.assertEquals(9, shortest);
    }

    @Test
    void ex_10() {
        long trips = routes.trips(t("C"), t("C"), __ -> true, trip -> trip.distance() < 30);
        Assertions.assertEquals(7, trips);
    }

    static Town t(String townName) {
        return new Town(townName);
    }
}
