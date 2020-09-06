package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Traversal {
    default Integer distance(Town source, Set<Town> towns) {
        Town[] completeRoute = new Town[towns.size()+1];
        System.arraycopy(towns.toArray(new Town[]{}), 0, completeRoute, 1, towns.size());
        completeRoute[0] = source;
        return distance(completeRoute);
    }

    default Integer distance(Collection<Town> towns, Town destination) {
        Town[] completeRoute = new Town[towns.size()+1];
        System.arraycopy(towns.toArray(new Town[]{}), 0, completeRoute, 0, towns.size());
        completeRoute[towns.size()] = destination;
        return distance(completeRoute);
    }

    Integer distance(Town... towns);

    class NoSuchRouteException extends RuntimeException {
        public NoSuchRouteException() {
            super("NO SUCH ROUTE");
        }
    }

}
