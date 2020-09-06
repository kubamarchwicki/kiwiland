package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.List;

public interface Traversal {
    default Integer distance(List<Town> towns) {
        return distance(towns.toArray(new Town[]{}));
    }
    Integer distance(Town... towns);

    class NoSuchRouteException extends RuntimeException {

        public NoSuchRouteException() {
            super("NO SUCH ROUTE");
        }
    }

}
