package fxutils;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.*;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PaintUtils {

    private PaintUtils(){}

    public static Color awtColorToFXColor(java.awt.Color c) {
        return Color.rgb(c.getRed(), c.getGreen(),
                c.getBlue(), c.getAlpha() / 255.0);
    }

    public static ImagePattern awtTexturePaintToFXImagePattern(TexturePaint paint){
        Image image = ImageUtils.awtImageToFxImage(paint.getImage());

        double x = paint.getAnchorRect().getX();
        double y = paint.getAnchorRect().getY();
        double w  = paint.getAnchorRect().getWidth();
        double h = paint.getAnchorRect().getHeight();
        double transparency = paint.getTransparency()/255;

        ImagePattern im = new ImagePattern(image, x, y, w, h, true);

        return im;
    }

//    static public LinearGradient awtRadialGradientPaintToFxRadialGradient(RadialGradientPaint paint){
//
//    }

    static public LinearGradient awtLinearGradientToFxLinearGradient(LinearGradientPaint paint){
        double startX = paint.getStartPoint().getX();
        double startY = paint.getStartPoint().getY();
        double endX = paint.getEndPoint().getX();
        double endY = paint.getEndPoint().getY();

        paint.getColors();
        paint.getFractions();

        LinearGradient fxpaint  = new LinearGradient(startX, startY, endX, endY,
                false,
                awtCycleMethodToFXCycleMethod(paint.getCycleMethod()), awtColorsToFxStops(paint.getColors(), paint.getFractions())
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

        bf.putDouble(color.getRed());
        bf.putDouble(color.getGreen());
        bf.putDouble(color.getBlue());
        bf.putDouble(color.getOpacity());
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
        Color color = Color.color(
                bf.getDouble(),
                bf.getDouble(),
                bf.getDouble(),
                bf.getDouble()
        );

        return color;

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
