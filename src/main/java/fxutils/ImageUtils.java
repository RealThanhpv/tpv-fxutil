package fxutils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class ImageUtils {
    private ImageUtils() {
    }


    final public static byte[] fxImageToBytes(Image image) {
        PixelReader reader = image.getPixelReader();

        double width = image.getWidth();
        double height = image.getHeight();


        int w = (int) width;
        int h = (int) height;

        byte[] imgArr = new byte[8+ w * h * 32]; // 16 bytes for image size and 32 bytes per pixel.

        ByteBuffer bf = ByteBuffer.wrap(imgArr);
        bf.putInt(w);
        bf.putInt(h);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                PaintUtils.writeColorToByteBuffer(color, bf);
            }
        }

        return imgArr;
    }
    final public static Image fxImageFromBytes(byte[] bytes) {
        ByteBuffer bf = ByteBuffer.wrap(bytes);
        int width = bf.getInt();
        int height = bf.getInt();

        if (width < 0 || height < 0) {
            return new WritableImage(1, 1);
        }

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                writer.setColor(x, y, PaintUtils.colorFromByteBuffer(bf));
            }
        }

        return image;
    }

    /**
     *
     * @param image
     * @return bytes without width and height data
     */
    final public static byte[] awtImageToFxImageBytes(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();

        byte[] imgArr = new byte[8 + w * h * 32 ]; // 16 bytes for image size and 32 bytes per pixel.

        ByteBuffer bf = ByteBuffer.wrap(imgArr);
        bf.putInt(w);
        bf.putInt(h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                PaintUtils.writeColorToByteBuffer( PaintUtils.awtColorToFXColor(new java.awt.Color(image.getRGB(i, j))), bf);
            }
        }

        return imgArr;
    }

    public static Image awtImageToFxImage(BufferedImage image) {

       return fxImageFromBytes(awtImageToFxImageBytes(image));
//        WritableImage image = new WritableImage(width, height);
//        PixelWriter writer = image.getPixelWriter();
//
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                writer.setColor(x, y, PaintUtils.colorFromByteBuffer(bf));
//            }
//        }

    }
    /**
     * Returns a JavaFX color that is equivalent to the specified AWT color.
     *
     * @param c the color ({@code null} not permitted).
     * @return A JavaFX color.
     */



}
