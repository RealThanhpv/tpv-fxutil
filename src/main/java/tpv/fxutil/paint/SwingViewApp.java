package tpv.fxutil.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SwingViewApp {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                TestPanel panel = new TestPanel();
                frame.add(panel);
                frame.setSize(200, 200);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    static public TexturePaint makeTexturePaint(int size, boolean alpha){
        int s2 =  (size / 2);
        int type =
                alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage img = new BufferedImage(size, size, type);
        Color[] colors = makeGradientColors(4, alpha);
        Graphics2D g2d = img.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.setColor(colors[0]);
        g2d.fillRect(0, 0, s2, s2);
        g2d.setColor(colors[1]);
        g2d.fillRect(s2, 0, s2, s2);
        g2d.setColor(colors[3]);
        g2d.fillRect(0, s2, s2, s2);
        g2d.setColor(colors[2]);
        g2d.fillRect(s2, s2, s2, s2);
        g2d.dispose();
        Rectangle2D bounds = new Rectangle2D.Float(0, 0, size, size);

        TexturePaint paint =  new TexturePaint(img, bounds);

        return paint;
    }

    private static Color[] makeGradientColors(int numColors, boolean alpha) {
        Color[] colors = new Color[] {Color.red, Color.blue,
                Color.green, Color.yellow};
        Color[] ret = new Color[numColors];
        for (int i = 0; i < numColors; i++) {
            ret[i] = alpha ? makeAlphaColor(colors[i], 32) : colors[i];
        }
        return ret;
    }

    private LinearGradientPaint makeLinear(int numColors, boolean alpha) {
        float interval = 1.0f / (numColors - 1);
        float[] fractions = new float[numColors];
        for (int i = 0; i < fractions.length; i++) {
            fractions[i] = i * interval;
        }
        Color[] colors = makeGradientColors(numColors, alpha);
        return new LinearGradientPaint(0.0f, 0.0f,
                10.0f, 10.0f,
                fractions, colors,
                MultipleGradientPaint.CycleMethod.REFLECT);
    }

    static Color makeAlphaColor(Color origColor, int alpha){
        return new Color(origColor.getRed(),origColor.getGreen(),origColor.getBlue(),alpha);
    }

    private RadialGradientPaint makeRadial(int numColors, boolean alpha) {
        float interval = 1.0f / (numColors - 1);
        float[] fractions = new float[numColors];
        for (int i = 0; i < fractions.length; i++) {
            fractions[i] = i * interval;
        }
        Color[] colors = makeGradientColors(numColors, alpha);
        return new RadialGradientPaint(0.0f, 0.0f, 10.0f,
                fractions, colors,
                MultipleGradientPaint.CycleMethod.REFLECT);
    }


    static public RadialGradientPaint makeRadialGradientPaint(){
        Point2D center = new Point2D.Float(50, 50);
        float radius = 25;
        float[] dist = {0.0f, 0.2f, 1.0f};
        Color[] colors = {Color.RED, Color.WHITE, Color.BLUE};
        RadialGradientPaint p =
                new RadialGradientPaint(center, radius, dist, colors);

        return p;
    }

    static public LinearGradientPaint makeLinearGradientPaint() {
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(200, 200);
        float[] dist = {0.0f,  1.0f};
        Color[] colors = {Color.RED,  Color.BLUE};
        LinearGradientPaint p =
                new LinearGradientPaint(start, end, dist, colors);

        return p;
    }

    static public GradientPaint makeGradientPaint(double w, double h){
        Color color1 = Color.RED;
        Color color2 = Color.GREEN;
        GradientPaint gp = new GradientPaint(0, 0, color1, (float) w, (float) h, color2);

        return gp;
    }


    public static class TestPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = 200;
            int h = 200;
            g2d.setPaint(makeTexturePaint(10, true));
            g2d.fillRect(0, 0, w, h);
        }


    }
}
