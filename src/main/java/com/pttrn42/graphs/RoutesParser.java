package com.pttrn42.graphs;

import com.pttrn42.graphs.kiwiland.model.Route;
import com.pttrn42.graphs.kiwiland.model.Routes;
import com.pttrn42.graphs.kiwiland.model.Town;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RoutesParser {

    public static Routes parse(String inputGraph) {
        if (Objects.isNull(inputGraph) || inputGraph.isEmpty()) {
            return new Routes();
        }

        var routes = new Routes();
        Stream.of(inputGraph.split(","))
                .map(String::trim)
                .filter(RoutesParser::validParsableInput)
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
