package ru.peregruzochka.travel_guide.controller;


import lombok.Getter;

@Getter
public enum SortedType {
    GRADE("avg_grade"),
    LOCATION("location");

    private final String value;

    SortedType(String value) {
        this.value = value;
    }
}
