package fxutils;

public class MetricUtils {

    static public double pointsToPixels(double points){
        return points*0.75;
    }

    static public double pixelsToPoints(double pixels){
        return pixels/0.75;
    }
}
