package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.Town;

public interface Traversal {
    Integer distance(Town source, Town... towns);

    Integer distance(Town... towns);

    class NoSuchRouteException extends RuntimeException {
        public NoSuchRouteException() {
            super("NO SUCH ROUTE");
        }
    }

}
