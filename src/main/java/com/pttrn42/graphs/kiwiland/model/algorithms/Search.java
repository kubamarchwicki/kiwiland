package com.pttrn42.graphs.kiwiland.model.algorithms;

import com.pttrn42.graphs.kiwiland.model.SearchResult;
import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.function.Predicate;

public interface Search {
    default SearchResult search(Town from, Town to, Predicate<Integer> nbOfStopsCriteria) {
        return search(from, to, nbOfStopsCriteria, __ -> true);
    }
    SearchResult search(Town from, Town to, Predicate<Integer> nbOfStopsCriteria, Predicate<SearchResult.Trip> validResult);
}
