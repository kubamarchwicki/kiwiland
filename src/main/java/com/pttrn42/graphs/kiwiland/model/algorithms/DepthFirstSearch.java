package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.Graph;
import com.pttrn42.graphs.kiwiland.model.Route;
import com.pttrn42.graphs.kiwiland.model.SearchResult;
import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;

class DepthFirstSearch implements Search {
    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Graph networkGraph;
    private final Traversal traversalDelegate;

    public DepthFirstSearch(Graph graph, Traversal delegate) {
        this.networkGraph = graph;
        this.traversalDelegate = delegate;
    }

    public SearchResult search(Town from, Town to, Predicate<Integer> nbOfStopsCriteria) {
        Set<Town> stops = new LinkedHashSet<>(), visited = new LinkedHashSet<>();
        SearchResult results = new SearchResult(from);
        search(from, to, visited, stops, nbOfStopsCriteria, results);

        return results;
    }

    private void search(Town from, Town to, Set<Town> visited, Set<Town> stops, Predicate<Integer> nbOfStopsCriteria, SearchResult results) {
        visited.add(from);
        for (Route r: networkGraph.getOrDefault(from, emptyList())) {
            LOG.fine(String.format("routes from(%s): %s", from, r));
            if (r.to().equals(to)) {
                stops.add(r.to());
                LOG.fine(String.format("visited: %s", visited));
                LOG.fine(String.format("stops: %s", stops));
                if (nbOfStopsCriteria.test(stops.size())) {
                    results.append(stops, traversalDelegate.distance(results.source(), stops));
                }
                stops.remove(r.to());
                break;
            }

            if (!visited.contains(r.to())) {
                stops.add(r.to());
                search(r.to(), to, visited, stops, nbOfStopsCriteria, results);
                stops.remove(r.to());
            }

        }

        visited.remove(from);
    }
}
