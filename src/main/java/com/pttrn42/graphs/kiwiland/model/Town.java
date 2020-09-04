package com.pttrn42.graphs.kiwiland.model;

public record Town(String name) {
    public Town(char c) {
        this(String.valueOf(c));
    }
}
