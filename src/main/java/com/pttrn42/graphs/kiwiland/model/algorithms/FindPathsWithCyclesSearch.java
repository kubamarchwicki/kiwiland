package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.Graph;
import com.pttrn42.graphs.kiwiland.model.Route;
import com.pttrn42.graphs.kiwiland.model.SearchResult;
import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;

class FindPathsWithCyclesSearch implements Search {
    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static Predicate<Integer> ALLOW_MULTIPLE_LOOPS = stackSize -> stackSize < 15;
    private final Graph networkGraph;
    private final Traversal traversalDelegate;

    public FindPathsWithCyclesSearch(Graph graph, Traversal delegate) {
        this.networkGraph = graph;
        this.traversalDelegate = delegate;
    }

    public SearchResult search(Town from, Town to, Predicate<Integer> nbOfStopsCriteria, Predicate<SearchResult.Trip> validResult) {
        SearchResult results = new SearchResult(from, validResult);

        Stack<Town> stack = new Stack<>();
        stack.add(from);

        search(stack, to, nbOfStopsCriteria, results);

        LOG.fine(results.toString());
        return results;
    }

    private void search(Stack<Town> stack, Town last, Predicate<Integer> nbOfStopsCriteria, SearchResult results) {
        Town current = stack.peek();
        LOG.fine("At: " + current + ", with stack="+stack);

        for (Route r: networkGraph.getOrDefault(current, emptyList())) {
            LOG.finer(String.format("routes from(%s): %s", current, r));

            if (last.equals(r.to()) && nbOfStopsCriteria.test(stack.size())) {
                List<Town> completeRoute = new ArrayList<>(stack);
                completeRoute.add(r.to());
                results.append(new ArrayList<>(completeRoute), traversalDelegate.distance(completeRoute));
            }

            if (!stack.contains(r.to())
                    || ALLOW_MULTIPLE_LOOPS.and(nbOfStopsCriteria.negate()).test(stack.size())
                    || (ALLOW_MULTIPLE_LOOPS.test(stack.size()) && !results.valid())) {
                stack.add(r.to());
                search(stack, last, nbOfStopsCriteria, results);
                stack.pop();
            }
        }
    }
}
