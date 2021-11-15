package fxutils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

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

    static public RadialGradientPaint createRadialGradientPaint(){
        Point2D center = new Point2D.Float(50, 50);
        float radius = 25;
        float[] dist = {0.0f, 0.2f, 1.0f};
        Color[] colors = {Color.RED, Color.WHITE, Color.BLUE};
        RadialGradientPaint p =
                new RadialGradientPaint(center, radius, dist, colors);

        return p;
    }

    static public LinearGradientPaint createLinearGradientPaint() {
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(200, 200);
        float[] dist = {0.0f,  1.0f};
        Color[] colors = {Color.RED,  Color.BLUE};
        LinearGradientPaint p =
                new LinearGradientPaint(start, end, dist, colors);

        return p;
    }

    static public GradientPaint createGradientPaint(double w, double h){
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
            g2d.setPaint(createRadialGradientPaint());
            g2d.fillRect(0, 0, w, h);
        }


    }
}
