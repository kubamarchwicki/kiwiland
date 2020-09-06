package com.pttrn42.graphs.kiwiland.model;

import com.pttrn42.graphs.kiwiland.model.algorithms.AlgorithmsFactory;
import com.pttrn42.graphs.kiwiland.model.algorithms.Search;
import com.pttrn42.graphs.kiwiland.model.algorithms.Traversal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

public class Network {
    private final Graph networkGraph = new Graph();

    private final Search searchDelegate;
    private final Traversal traversalDelegate;

    public Network() {
        searchDelegate = AlgorithmsFactory.getDefaultSearch(networkGraph);
        traversalDelegate = AlgorithmsFactory.getDefaultTraversal(networkGraph);
    }

    public long shortest(Town from, Town to) {
        return searchDelegate.search(from, to, __ -> true)
                .shortest();
    }

    public long trips(Town from, Town to) {
        return trips(from, to, __ -> true);
    }

    public long trips(Town from, Town to, Predicate<Integer> nbOfStopsCriteria) {
        return searchDelegate.search(from, to, nbOfStopsCriteria)
                .size();
    }

    public String distance(Town... towns) {
        //TODO: maybe try monad?
        //TODO: more functional approach
        try {
            return traversalDelegate.distance(towns).toString();
        } catch (Traversal.NoSuchRouteException ex) {
            return ex.getMessage();
        }
    }

    public Network addRoute(Town from, Town to, Integer distance) {
        addRoute(new Route(from, to, distance));
        return this;
    }

    public Network addRoute(Route route) {
        networkGraph.compute(route.from(),
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
        Network routes1 = (Network) o;
        return Objects.equals(networkGraph, routes1.networkGraph);
    }

    @Override
    public String toString() {
        return "Routes{" +
                "routes=" + networkGraph +
                '}';
    }

}
