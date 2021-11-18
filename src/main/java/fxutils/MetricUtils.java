package fxutils;

public class MetricUtils {

    static public double pointToPixel(double points){
        return points*0.75;
    }

    static public double pixelsToPoint(double pixels){
        return pixels/0.75;
    }
}
