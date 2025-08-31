package io.jp.core.organizer

import io.jp.core.domain.place.PlaceBoxed
import io.jp.core.domain.point.PointBoxed
import spock.lang.Specification
import spock.lang.Subject

import static io.jp.database.entities.route.PlaceType.BUILDING

class PlaceOrganizerTest extends Specification {
    @Subject
    PlaceOrganizerBoxed organizer = new PlaceOrganizerBoxed()

    def 'should organize places'(List<PlaceBoxed> places, List<PlaceBoxed> expectedPlaces) {
        given:
        // ignored

        when:
        organizer.organize(places)

        then:
        expectedPlaces == places

        where:
        places           || expectedPlaces
        getThreePlaces() || getThreeOrganizedPlaces()
        getFourPlaces()  || getFourOrganizedPlaces()
    }

    def getThreePlaces() {
        return [PlaceBoxed.builder()
                        .position(PointBoxed.of(50.0, 19.0))
                        .name("Place1")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.6, 19.2))
                        .name("Far Place")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.3, 19.04))
                        .name("Closer Place")
                        .placeType(BUILDING)
                        .build()]
    }

    def getThreeOrganizedPlaces() {
        return [PlaceBoxed.builder()
                        .position(PointBoxed.of(50.0, 19.0))
                        .name("Place1")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.3, 19.04))
                        .name("Closer Place")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.6, 19.2))
                        .name("Far Place")
                        .placeType(BUILDING)
                        .build()]
    }

    def getFourPlaces() {
        return [PlaceBoxed.builder()
                        .position(PointBoxed.of(50.0, 19.0))
                        .name("Place1")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.6, 19.2))
                        .name("Mid Place")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.8, 19.3))
                        .name("Far Place")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.3, 19.04))
                        .name("Closer Place")
                        .placeType(BUILDING)
                        .build(),
        ]
    }

    def getFourOrganizedPlaces() {
        return [PlaceBoxed.builder()
                        .position(PointBoxed.of(50.0, 19.0))
                        .name("Place1")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.3, 19.04))
                        .name("Closer Place")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.6, 19.2))
                        .name("Mid Place")
                        .placeType(BUILDING)
                        .build(),
                PlaceBoxed.builder()
                        .position(PointBoxed.of(50.8, 19.3))
                        .name("Far Place")
                        .placeType(BUILDING)
                        .build(),

        ]
    }
}
