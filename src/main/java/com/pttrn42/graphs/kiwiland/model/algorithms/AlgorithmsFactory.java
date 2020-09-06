package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.Graph;

public class AlgorithmsFactory {

    public static Search getDefaultSearch(Graph graph) {
        return new FindPathsWithCyclesSearch(graph, getDefaultTraversal(graph));
    }

    public static NaiveTraversal getDefaultTraversal(Graph graph) {
        return new NaiveTraversal(graph);
    }

}
