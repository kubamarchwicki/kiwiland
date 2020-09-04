package com.pttrn42.graphs;

import com.pttrn42.graphs.kiwiland.model.Routes;
import com.pttrn42.graphs.kiwiland.model.Town;

public class App {
    public static void main(String[] args) {
        Routes routes = RoutesParser.parse(args.length > 0? args[0] : "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

        System.out.println("Output #1: " + routes.distance(t("A"), t("B"), t("C")));
        System.out.println("Output #2: " + routes.distance(t("A"), t("D")));
        System.out.println("Output #3: " + routes.distance(t("A"), t("D"), t("C")));
        System.out.println("Output #4: " + routes.distance(t("A"), t("E"), t("B"), t("C"), t("D")));
        System.out.println("Output #5: " + routes.distance(t("A"), t("E"), t("D")));
    }
    
    static Town t(String townName) {
        return new Town(townName);
    }
}