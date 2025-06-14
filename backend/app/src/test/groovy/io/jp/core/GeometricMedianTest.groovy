package io.jp.core

import spock.lang.Specification

class GeometricMedianTest extends Specification {

    def 'should calculate correct center'() {
        given:
        List<GeometricMedian.Point> points = [new GeometricMedian.Point(50.0653, 19.9402),
                                              new GeometricMedian.Point(50.0646, 19.9430),
                                              new GeometricMedian.Point(50.0645, 19.9445),
                                              new GeometricMedian.Point(50.0617, 19.9379),
                                              new GeometricMedian.Point(50.0615, 19.9382),
                                              new GeometricMedian.Point(50.0616, 19.9373),
                                              new GeometricMedian.Point(50.0610, 19.9339),
                                              new GeometricMedian.Point(50.0588, 19.9365),
                                              new GeometricMedian.Point(50.0573, 19.9373),
                                              new GeometricMedian.Point(50.0543, 19.9366)]

        when:
        GeometricMedian.Point result = GeometricMedian.calculate(points)

        then:
        result != null
        assert (Double) 50.06148493243695 == result.x
        assert (Double) 19.93800165430533 == result.y
    }
}
