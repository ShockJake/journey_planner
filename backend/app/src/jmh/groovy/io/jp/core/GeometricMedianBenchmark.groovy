package io.jp.core

import io.jp.core.geometricmedian.*
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import spock.lang.Specification

import static DataGenerator.generatePoints
import static DataGenerator.mapToBoxed
import static io.jp.mapper.point.PointMapper.mapToPoints2DArray
import static java.util.concurrent.TimeUnit.MILLISECONDS
import static java.util.concurrent.TimeUnit.SECONDS

@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = SECONDS)
@Fork(value = 2, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(MILLISECONDS)
class GeometricMedianBenchmark extends Specification {
    static Point center = new Point(50.0, 19.0)
    static List<Point> points_100 = generatePoints(100, center)
    static List<Point> points_1000 = generatePoints(1000, center)
    static List<Point> points_10k = generatePoints(10000, center)
    static List<Point> points_100k = generatePoints(100000, center)
    static List<Point> points_1m = generatePoints(1000000, center)
    static List<PointBoxed> points_boxed_100 = mapToBoxed(points_100)
    static List<PointBoxed> points_boxed_1000 = mapToBoxed(points_1000)
    static List<PointBoxed> points_boxed_10k = mapToBoxed(points_10k)
    static List<PointBoxed> points_boxed_100k = mapToBoxed(points_100k)
    static List<PointBoxed> points_boxed_1m = mapToBoxed(points_1m)
    static double[][] points_100_arr = mapToPoints2DArray(points_100)
    static double[][] points_1000_arr = mapToPoints2DArray(points_1000)
    static double[][] points_10k_arr = mapToPoints2DArray(points_10k)
    static double[][] points_100k_arr = mapToPoints2DArray(points_100k)
    static double[][] points_1m_arr = mapToPoints2DArray(points_1m)
    static Point[] points_array_100 = points_100.toArray() as Point[]
    static Point[] points_array_1000 = points_1000.toArray() as Point[]
    static Point[] points_array_10k = points_10k.toArray() as Point[]
    static Point[] points_array_100k = points_100k.toArray() as Point[]
    static Point[] points_array_1m = points_1m.toArray() as Point[]

    @Benchmark
    void A_100_calculateMedianStatic() {
        GeometricMedian.calculateGeometricMedian(points_100)
    }

    @Benchmark
    void B_1000_calculateMedianStatic() {
        GeometricMedian.calculateGeometricMedian(points_1000)
    }

    @Benchmark
    void C_10k_calculateMedianStatic() {
        GeometricMedian.calculateGeometricMedian(points_10k)
    }

    @Benchmark
    void D_100k_calculateMedianStatic() {
        GeometricMedian.calculateGeometricMedian(points_100k)
    }


    @Benchmark
    void E_1m_calculateMedianStatic() {
        GeometricMedian.calculateGeometricMedian(points_1m)
    }

    @Benchmark
    void A_100_calculateMedianVector(Blackhole blackHole) {
        GeometricMedianVector.calculateGeometricMedian(points_100_arr[0], points_100_arr[1])
    }

    @Benchmark
    void B_1000_calculateMedianVector() {
        GeometricMedianVector.calculateGeometricMedian(points_1000_arr[0], points_1000_arr[1])
    }

    @Benchmark
    void C_10k_calculateMedianVector() {
        GeometricMedianVector.calculateGeometricMedian(points_10k_arr[0], points_10k_arr[1])
    }

    @Benchmark
    void D_100k_calculateMedianVector() {
        GeometricMedianVector.calculateGeometricMedian(points_100k_arr[0], points_100k_arr[1])
    }

    @Benchmark
    void E_1m_calculateMedianVector() {
        GeometricMedianVector.calculateGeometricMedian(points_1m_arr[0], points_1m_arr[1])
    }

    @Benchmark
    void A_100_calculateMedianBoxed() {
        GeometricMedianBoxed.calculateGeometricMedian(points_boxed_100)
    }

    @Benchmark
    void B_1000_calculateMedianBoxed() {
        GeometricMedianBoxed.calculateGeometricMedian(points_boxed_1000)
    }

    @Benchmark
    void C_10k_calculateMedianBoxed() {
        GeometricMedianBoxed.calculateGeometricMedian(points_boxed_10k)
    }

    @Benchmark
    void D_100k_calculateMedianBoxed() {
        GeometricMedianBoxed.calculateGeometricMedian(points_boxed_100k)
    }

    @Benchmark
    void E_1m_calculateMedianBoxed() {
        GeometricMedianBoxed.calculateGeometricMedian(points_boxed_1m)
    }

    @Benchmark
    void A_100_calculateMedianArray() {
        GeometricMedianArray.calculateGeometricMedian(points_array_100)
    }

    @Benchmark
    void B_1000_calculateMedianArray() {
        GeometricMedianArray.calculateGeometricMedian(points_array_1000)
    }

    @Benchmark
    void C_10k_calculateMedianArray() {
        GeometricMedianArray.calculateGeometricMedian(points_array_10k)
    }

    @Benchmark
    void D_100k_calculateMedianArray() {
        GeometricMedianArray.calculateGeometricMedian(points_array_100k)
    }

    @Benchmark
    void E_1m_calculateMedianArray() {
        GeometricMedianArray.calculateGeometricMedian(points_array_1m)
    }
}
