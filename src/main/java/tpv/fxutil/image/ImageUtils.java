package tpv.fxutil.image;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import tpv.fxutil.paint.PaintUtils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.nio.ByteBuffer;

public class ImageUtils {
    private ImageUtils() {
    }

    public static byte[] fxImageToBytes(Image image) {
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
     public static Image fxImageFromBytes(byte[] bytes) {
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
     public static byte[] awtImageToFxImageBytes(BufferedImage image) {

        int w = image.getWidth();
        int h = image.getHeight();

        byte[] imgArr = new byte[8 + w * h * 32 ]; // 16 bytes for image size and 32 bytes per pixel.

        ByteBuffer bf = ByteBuffer.wrap(imgArr);
        bf.putInt(w);
        bf.putInt(h);

        ColorModel cm = image.getColorModel();
        int pixel;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                pixel = image.getRGB(i, j);

                PaintUtils.writeColorToByteBuffer(
                        Color.rgb(cm.getRed(pixel), cm.getGreen(pixel),
                                cm.getBlue(pixel), cm.getAlpha(pixel) / 255.0)
                        , bf);
            }
        }

        return imgArr;
    }



    public static Image awtImageToFxImage(BufferedImage bfImage) {
        double width = bfImage.getWidth();
        double height = bfImage.getHeight();

        WritableImage image = new WritableImage((int) width, (int) height);
        PixelWriter writer = image.getPixelWriter();

        ColorModel cm = bfImage.getColorModel();
        int pixel;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixel = bfImage.getRGB(i, j);
                writer.setColor(i, j,
                        Color.rgb(cm.getRed(pixel), cm.getGreen(pixel),
                        cm.getBlue(pixel), cm.getAlpha(pixel) / 255.0)
                );
            }
        }

        return image;
    }
    /**
     * Returns a JavaFX color that is equivalent to the specified AWT color.
     *
     * @param c the color ({@code null} not permitted).
     * @return A JavaFX color.
     */



}
