package io.jp.core

import io.jp.core.domain.place.Place
import io.jp.core.domain.place.PlaceBoxed
import io.jp.core.domain.point.PointBoxed
import io.jp.core.domain.benchmark.PlacePrimitivePoint
import io.jp.core.domain.benchmark.PlacePrimitives
import io.jp.core.domain.point.Point
import io.jp.core.placeresolver.NearestPlaceResolverPointBase
import io.jp.core.placeresolver.NearestPlaceResolverPointBoxed
import io.jp.core.placeresolver.NearestPlaceResolverPointPrimitive
import io.jp.core.placeresolver.NearestPlaceResolverPrimitiveArrays
import io.jp.core.placeresolver.NearestPlaceResolverPrimitiveArraysSplit
import io.jp.core.placeresolver.NearestPlaceResolverPrimitives
import org.openjdk.jmh.annotations.*
import spock.lang.Specification

import static io.jp.core.DataGenerator.*
import static io.jp.database.entities.route.PlaceType.BUILDING
import static java.util.concurrent.TimeUnit.MILLISECONDS
import static java.util.concurrent.TimeUnit.NANOSECONDS
import static java.util.concurrent.TimeUnit.SECONDS

@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = SECONDS)
@Fork(value = 2, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(MILLISECONDS)
class NearestPlaceResolverBenchmark extends Specification {
    static PlaceBoxed start = PlaceBoxed.builder()
            .name("start")
            .placeType(BUILDING)
            .position(PointBoxed.of(50.0, 19.0))
            .build()
    static PlacePrimitivePoint startPrimitivePoint = PlacePrimitivePoint.builder()
            .name("start")
            .placeType(BUILDING)
            .position(Point.of(50.0, 19.0))
            .build()
    static PlacePrimitives startPrimitives = PlacePrimitives.builder()
            .name("start")
            .lng(50.0)
            .lng(19.0)
            .placeType(BUILDING)
            .build()
    static Place startBest = Place.builder()
            .name("start")
            .latitude(50.0)
            .longitude(19.0)
            .placeType(BUILDING)
            .build()
    static List<PlaceBoxed> places_boxed_100 = generatePlaces(100, start)
    static List<PlaceBoxed> places_boxed_1k = generatePlaces(1000, start)
    static List<PlaceBoxed> places_boxed_10k = generatePlaces(10000, start)
    static List<PlaceBoxed> places_boxed_100k = generatePlaces(100000, start)
    static List<PlaceBoxed> places_boxed_1m = generatePlaces(1000000, start)
    static List<PlacePrimitivePoint> places_point_primitive_100 = mapToPrimitivePoint(places_boxed_100)
    static List<PlacePrimitivePoint> places_point_primitive_1k = mapToPrimitivePoint(places_boxed_1k)
    static List<PlacePrimitivePoint> places_point_primitive_10k = mapToPrimitivePoint(places_boxed_10k)
    static List<PlacePrimitivePoint> places_point_primitive_100k = mapToPrimitivePoint(places_boxed_100k)
    static List<PlacePrimitivePoint> places_point_primitive_1m = mapToPrimitivePoint(places_boxed_1m)
    static List<PlacePrimitives> places_primitives_100 = mapToPrimitives(places_boxed_100)
    static List<PlacePrimitives> places_primitives_1k = mapToPrimitives(places_boxed_1k)
    static List<PlacePrimitives> places_primitives_10k = mapToPrimitives(places_boxed_10k)
    static List<PlacePrimitives> places_primitives_100k = mapToPrimitives(places_boxed_100k)
    static List<PlacePrimitives> places_primitives_1m = mapToPrimitives(places_boxed_1m)
    static double[][] places_array_100 = mapToPrimitiveArray(places_boxed_100)
    static double[][] places_array_1k = mapToPrimitiveArray(places_boxed_1k)
    static double[][] places_array_10k = mapToPrimitiveArray(places_boxed_10k)
    static double[][] places_array_100k = mapToPrimitiveArray(places_boxed_100k)
    static double[][] places_array_1m = mapToPrimitiveArray(places_boxed_1m)

    // --- Base ---
    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void A_100_places_A_base() {
        NearestPlaceResolverPointBase.findClosestPlace(start, places_boxed_100)
    }

    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void B_1k_places_A_base() {
        NearestPlaceResolverPointBase.findClosestPlace(start, places_boxed_1k)
    }

    @Benchmark
    void C_10k_places_A_base() {
        NearestPlaceResolverPointBase.findClosestPlace(start, places_boxed_10k)
    }

    @Benchmark
    void D_100k_places_A_base() {
        NearestPlaceResolverPointBase.findClosestPlace(start, places_boxed_100k)
    }

    @Benchmark
    void E_1m_places_A_base() {
        NearestPlaceResolverPointBase.findClosestPlace(start, places_boxed_1m)
    }


    // --- Boxed ---
    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void A_100_places_A_boxed() {
        NearestPlaceResolverPointBoxed.findClosestPlace(start, places_boxed_100)
    }

    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void B_1k_places_A_boxed() {
        NearestPlaceResolverPointBoxed.findClosestPlace(start, places_boxed_1k)
    }

    @Benchmark
    void C_10k_places_A_boxed() {
        NearestPlaceResolverPointBoxed.findClosestPlace(start, places_boxed_10k)
    }

    @Benchmark
    void D_100k_places_A_boxed() {
        NearestPlaceResolverPointBoxed.findClosestPlace(start, places_boxed_100k)
    }

    @Benchmark
    void E_1m_places_A_boxed() {
        NearestPlaceResolverPointBoxed.findClosestPlace(start, places_boxed_1m)
    }

    // --- Point Primitive ---
    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void A_100_places_A_point_primitive() {
        NearestPlaceResolverPointPrimitive.findClosestPlace(startPrimitivePoint, places_point_primitive_100)
    }

    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void B_1k_places_A_point_primitive() {
        NearestPlaceResolverPointPrimitive.findClosestPlace(startPrimitivePoint, places_point_primitive_1k)
    }

    @Benchmark
    void C_10k_places_A_point_primitive() {
        NearestPlaceResolverPointPrimitive.findClosestPlace(startPrimitivePoint, places_point_primitive_10k)
    }

    @Benchmark
    void D_100k_places_A_point_primitive() {
        NearestPlaceResolverPointPrimitive.findClosestPlace(startPrimitivePoint, places_point_primitive_100k)
    }

    @Benchmark
    void E_1m_places_A_point_primitive() {
        NearestPlaceResolverPointPrimitive.findClosestPlace(startPrimitivePoint, places_point_primitive_1m)
    }

    // --- Primitives ---
    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void A_100_places_B_primitives() {
        NearestPlaceResolverPrimitives.findClosestPlace(startPrimitives, places_primitives_100)
    }

    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void B_1k_places_B_primitives() {
        NearestPlaceResolverPrimitives.findClosestPlace(startPrimitives, places_primitives_1k)
    }

    @Benchmark
    void C_10k_places_B_primitives() {
        NearestPlaceResolverPrimitives.findClosestPlace(startPrimitives, places_primitives_10k)
    }

    @Benchmark
    void D_100k_places_B_primitives() {
        NearestPlaceResolverPrimitives.findClosestPlace(startPrimitives, places_primitives_100k)
    }

    @Benchmark
    void E_1m_places_B_primitives() {
        NearestPlaceResolverPrimitives.findClosestPlace(startPrimitives, places_primitives_1m)
    }

    // --- Primitive Arrays ---
    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void A_100_places_C_primitive_arrays() {
        NearestPlaceResolverPrimitiveArrays.findClosestPlace(startPrimitives, places_array_100[0], places_array_100[1])
    }

    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void B_1k_places_C_primitive_arrays() {
        NearestPlaceResolverPrimitiveArrays.findClosestPlace(startPrimitives, places_array_1k[0], places_array_1k[1])
    }

    @Benchmark
    void C_10k_places_C_primitive_arrays() {
        NearestPlaceResolverPrimitiveArrays.findClosestPlace(startPrimitives, places_array_10k[0], places_array_10k[1])
    }

    @Benchmark
    void D_100k_places_C_primitive_arrays() {
        NearestPlaceResolverPrimitiveArrays.findClosestPlace(startPrimitives, places_array_100k[0], places_array_100k[1])
    }

    @Benchmark
    void E_1m_places_C_primitive_arrays() {
        NearestPlaceResolverPrimitiveArrays.findClosestPlace(startPrimitives, places_array_1m[0], places_array_1m[1])
    }


    // --- Primitive Arrays Split ---
    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void A_100_places_C_primitive_arrays_split() {
        NearestPlaceResolverPrimitiveArraysSplit.findClosestPlace(startBest, places_array_100[0], places_array_100[1])
    }

    @Benchmark
    @OutputTimeUnit(NANOSECONDS)
    void B_1k_places_C_primitive_arrays_split() {
        NearestPlaceResolverPrimitiveArraysSplit.findClosestPlace(startBest, places_array_1k[0], places_array_1k[1])
    }

    @Benchmark
    void C_10k_places_C_primitive_arrays_split() {
        NearestPlaceResolverPrimitiveArraysSplit.findClosestPlace(startBest, places_array_10k[0], places_array_10k[1])
    }

    @Benchmark
    void D_100k_places_C_primitive_arrays_split() {
        NearestPlaceResolverPrimitiveArraysSplit.findClosestPlace(startBest, places_array_100k[0], places_array_100k[1])
    }

    @Benchmark
    void E_1m_places_C_primitive_arrays_split() {
        NearestPlaceResolverPrimitiveArraysSplit.findClosestPlace(startBest, places_array_1m[0], places_array_1m[1])
    }
}
