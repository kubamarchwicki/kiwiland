package com.pttrn42.graphs;

import com.pttrn42.graphs.kiwiland.model.Route;
import com.pttrn42.graphs.kiwiland.model.Network;
import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class NetworkParser {

    public static Network parse(String inputGraph) {
        if (Objects.isNull(inputGraph) || inputGraph.isEmpty()) {
            return new Network();
        }

        var routes = new Network();
        Stream.of(inputGraph.split(","))
                .map(String::trim)
                .filter(NetworkParser::validParsableInput)
                .forEach(s -> routes.addRoute(tupleToRoute(s)));

        return routes;
    }

    private static boolean validParsableInput(String s) {
        return s.length() >= 3
                && Character.isAlphabetic(s.charAt(0))
                && Character.isAlphabetic(s.charAt(1))
                && isNumeric(s.substring(2));
    }

    private static Route tupleToRoute(String input) {
        return new Route(new Town(input.charAt(0)),
                new Town(input.charAt(1)),
                Integer.valueOf(input.substring(2)));
    }

    private static Pattern pattern = Pattern.compile("\\d+");
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
