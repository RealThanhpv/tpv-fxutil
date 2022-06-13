package tpv.fxutil.paint;

import javafx.scene.paint.Color;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import tpv.fxutil.image.ImageUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PaintUtils {

    private PaintUtils(){}

    static public Paint awtPaintToFxPaint(java.awt.Paint p){
        if(p instanceof java.awt.Color ){
            return awtColorToFXColor((java.awt.Color)p);
        }

        if(p instanceof java.awt.GradientPaint  ){
            return awtGradientToFxLinearGradient((java.awt.GradientPaint)p);
        }

        if(p instanceof java.awt.LinearGradientPaint  ){
            return awtLinearGradientToFxLinearGradient((java.awt.LinearGradientPaint)p);
        }

        if(p instanceof java.awt.RadialGradientPaint  ){
            return awtRadialGradientPaintToFxRadialGradient((java.awt.RadialGradientPaint)p);
        }

        if(p instanceof java.awt.TexturePaint  ){
            return awtTexturePaintToFXImagePattern((java.awt.TexturePaint)p);
        }

        try {
            throw new Exception("Unsupport type of "+ p);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;

    }

    public static Color awtColorToFXColor(java.awt.Color c) {
        return Color.rgb(c.getRed(), c.getGreen(),
                c.getBlue(), c.getAlpha() / 255.0);
    }

    public static ImagePattern awtTexturePaintToFXImagePattern(TexturePaint paint){

        return new ImagePattern(ImageUtils.awtImageToFxImage(paint.getImage()),
                paint.getAnchorRect().getX(), paint.getAnchorRect().getY(),
                paint.getAnchorRect().getWidth(), paint.getAnchorRect().getHeight(),
                false);


    }

    static public RadialGradient awtRadialGradientPaintToFxRadialGradient(RadialGradientPaint paint){
        double cx = paint.getCenterPoint().getX();
        double cy = paint.getCenterPoint().getY();
        double rad = paint.getRadius();
        Point2D fp = paint.getFocusPoint();
        double dx = fp.getX()-cx;
        double dy = fp.getY() - cy;
        double angle = Math.atan2(dx, dy);
        double dsqr = dx*dx + dy*dy;
        double distance = Math.sqrt(dsqr);

        RadialGradient fxp = new RadialGradient(angle, distance,
                cx, cy,  rad,
                false, awtCycleMethodToFXCycleMethod(paint.getCycleMethod()),
                awtColorsToFxStops(paint.getColors(), paint.getFractions())
        );

        return fxp;
    }

    static public LinearGradient awtLinearGradientToFxLinearGradient(LinearGradientPaint paint){
        double startX = paint.getStartPoint().getX();
        double startY = paint.getStartPoint().getY();
        double endX = paint.getEndPoint().getX();
        double endY = paint.getEndPoint().getY();

        paint.getColors();
        paint.getFractions();

        return   new LinearGradient(startX, startY, endX, endY,
                false,
                awtCycleMethodToFXCycleMethod(paint.getCycleMethod()),
                awtColorsToFxStops(paint.getColors(), paint.getFractions())
        );

    }
    static public LinearGradient awtGradientToFxLinearGradient(GradientPaint paint){
        double startX = paint.getPoint1().getX();
        double startY = paint.getPoint1().getY();
        double endX = paint.getPoint2().getX();
        double endY = paint.getPoint2().getY();

        LinearGradient fxpaint  = new LinearGradient(startX, startY, endX, endY,
                false,
                CycleMethod.NO_CYCLE,
                awtColorsToFxStops(new java.awt.Color[]{paint.getColor1(), paint.getColor2()}, new float[]{0.0f, 1.0f})
        );

        return fxpaint;
    }



    static private List<Stop> awtColorsToFxStops(java.awt.Color[] colors, float[] fractions){
        List<Stop> stops = new ArrayList<>();
        for (int i = 0; i < colors.length; i++) {
            Stop stop = new Stop(fractions[i], awtColorToFXColor(colors[i]));
            stops.add(stop);
        }

        return stops;
    }

    static public CycleMethod awtCycleMethodToFXCycleMethod(MultipleGradientPaint.CycleMethod method){

        if(method == MultipleGradientPaint.CycleMethod.REFLECT){
            return  CycleMethod.REFLECT;
        }

        if(method == MultipleGradientPaint.CycleMethod.REPEAT){
            return  CycleMethod.REPEAT;
        }
        return  CycleMethod.NO_CYCLE;
    }

    //User 32 bytes
    final public static void writeColorToByteBuffer(Color color, ByteBuffer bf) {
        writeColorRgbBuffer(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity(), bf);
    }

    final public static void writeColorRgbBuffer(double red, double green, double blue, double opacity, ByteBuffer bf) {

        bf.putDouble(red);
        bf.putDouble(green);
        bf.putDouble(blue);
        bf.putDouble(opacity);
    }
    //return 32 bytes array
    final public static byte[] colorToBytes(Color color) {

        byte[] colorArr = new byte[32];
        ByteBuffer bf = ByteBuffer.wrap(colorArr);
        bf.putDouble(color.getRed());
        bf.putDouble(color.getGreen());
        bf.putDouble(color.getBlue());
        bf.putDouble(color.getOpacity());

        return colorArr;
    }
    //need 32 bytes array
    final public static Color colorFromBytes(byte[] bytes) {
        ByteBuffer bf = ByteBuffer.wrap(bytes);
        return Color.color(
                bf.getDouble(),
                bf.getDouble(),
                bf.getDouble(),
                bf.getDouble()
        );


    }
    final public static Color colorFromByteBuffer(ByteBuffer bf) {
        return Color.color(
                bf.getDouble(),
                bf.getDouble(),
                bf.getDouble(),
                bf.getDouble()
        );

    }


}
