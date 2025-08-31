package io.jp.database.entities.route;

import lombok.Getter;

import java.util.List;

@Getter
public enum PlaceType {
    DEFAULT(""),
    HOUSE("building.historic"),
    MEMORIAL("tourism.sights.memorial"),
    SQUARE("tourism.sights.square"),
    RESTAURANT("catering.restaurant"),
    CHURCH("tourism.sights.place_of_worship"),
    CASTLE("tourism.sights.castle"),
    UNIVERSITY("building.university"),
    OLD("building.historic"),
    COFFEE("catering.cafe.coffee_shop"),
    THEATRE("entertainment.culture.theatre"),
    STORE("commercial"),
    BUILDING("building"),
    MAGICK("tourism.sights.place_of_worship"),
    MUSEUM("entertainment.museum"),
    INFO("tourism.information"),
    PARK("leisure.park"),
    WATER("natural.water"),
    VIEW("tourism.attraction.viewpoint"),
    TRAIN("railway"),
    DISTRICT("populated_place.district"),
    GALLERY("entertainment.culture.gallery");

    private final String category;

    PlaceType(String category) {
        this.category = category;
    }

    public static List<PlaceType> coveredPlaceTypes() {
        return List.of(CHURCH, MUSEUM, COFFEE, GALLERY, THEATRE, UNIVERSITY);
    }

    public static List<PlaceType> additionalPlaceTypes() {
        return List.of(CHURCH, MUSEUM, COFFEE, GALLERY, THEATRE, UNIVERSITY, BUILDING);
    }
}
