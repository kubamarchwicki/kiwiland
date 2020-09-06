package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.Graph;
import com.pttrn42.graphs.kiwiland.model.Route;
import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.Collection;
import java.util.logging.Logger;

class NaiveTraversal implements Traversal {
    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Graph networkGraph;

    public NaiveTraversal(Graph graph) {
        this.networkGraph = graph;
    }

    @Override
    public Integer distance(Town source, Town... towns) {
        Integer sumDistance = distance(source, towns[0]);
        for (int i = 0; i<towns.length-1; i++) {
            sumDistance += distance(towns[i], towns[i+1]);
        }
        return sumDistance;
    }

    @Override
    public Integer distance(Town... towns) {
        Integer sumDistance = 0;
        for (int i = 0; i<towns.length-1; i++) {
            sumDistance += distance(towns[i], towns[i+1]);
        }
        return sumDistance;
    }

    private Integer distance(Town from, Town to) {
        LOG.finer(String.format("Calculating route from (%s) to (%s)", from, to));
        return networkGraph.values().stream()
                .flatMap(Collection::stream)
                .filter(route -> route.from().equals(from) && route.to().equals(to))
                .findFirst()
                .map(Route::distance)
                .orElseThrow(() -> new NoSuchRouteException());
    }

}
