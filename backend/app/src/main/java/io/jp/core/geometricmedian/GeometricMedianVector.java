package io.jp.core.geometricmedian;


import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;


public class GeometricMedianVector {
    private static final double TOLERANCE = 1e-10;
    private static final int MAX_ITERATIONS = 50;
    private static final VectorSpecies<Double> SPECIES = DoubleVector.SPECIES_PREFERRED;

    public static Point calculateGeometricMedian(double[] xs, double[] ys) {
        var result = centroid(xs, ys);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            var values = calculateNextPoint(xs, ys, result);

            if (Math.hypot(values[0] - result[0], values[1] - result[1]) < TOLERANCE) {
                break;
            }
            result[0] = values[0];
            result[1] = values[1];
        }
        return new Point(result[0], result[1]);
    }

    private static double[] centroid(double[] xs, double[] ys) {
        var numberOfPoints = xs.length;
        var xsVector = DoubleVector.fromArray(SPECIES, xs, 0);
        var ysVector = DoubleVector.fromArray(SPECIES, ys, 0);
        var xResult = xsVector.reduceLanes(VectorOperators.ADD) / numberOfPoints;
        var yResult = ysVector.reduceLanes(VectorOperators.ADD) / numberOfPoints;

        return new double[]{xResult, yResult};
    }

    private static double[] calculateNextPoint(double[] xs, double[] ys, double[] xy) {
        double[] xyDenom = new double[]{0.0, 0.0, 0.0};
        for (int i = 0; i < xs.length; i++) {
            double dist = Math.hypot(xy[0] - xs[i], xy[1] - ys[i]);
            if (dist < 1e-10) continue;
            double weight = 1.0 / dist;
            xyDenom[0] += xs[i] * weight;
            xyDenom[1] += ys[i] * weight;
            xyDenom[2] += weight;
        }

        double newX = xyDenom[0] / xyDenom[2];
        double newY = xyDenom[1] / xyDenom[2];
        return new double[]{newX, newY};
    }
}
