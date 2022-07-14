package tpv.fxawt.util;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tpv.fxawt.util.paint.PaintUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.IOException;

import static tpv.fxawt.util.SwingViewApp.makeAwtTexturePaint;

public class FxViewApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    double w = 200;
    double h = 200;
    int s = 20;
    TexturePaint swPaint;

    @Override
    public void start(Stage stage) throws IOException {
        HBox root = new HBox();
        StackPane fxCon = new StackPane();
        StackPane swCon = new StackPane();
//        swCon.prefWidthProperty().bind(fxCon.prefWidthProperty());
//        swCon.prefHeightProperty().bind(fxCon.prefHeightProperty());
        root.getChildren().addAll(fxCon, swCon);

        Rectangle patternRect = new Rectangle(w, h);
        swPaint = makeAwtTexturePaint(s, true);

        javafx.scene.paint.Paint fxPaint = PaintUtils.awtTexturePaintToFXImagePattern(swPaint);

        patternRect.setFill(fxPaint);

        FileInputStream is = new FileInputStream("background.jpeg");
//        System.out.println(is.getFD());
        Image image = new Image(is);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(75);

        fxCon.getChildren().add(imageView);
        fxCon.getChildren().add(patternRect);


        Rectangle colorRect = new Rectangle(w, h);
//        colorRect.setFill(new java.awt.Color());



        SwingNode swNode = new SwingNode();

        createSwingContent(swNode);
        swCon.getChildren().add(swNode);


        Scene scene = new Scene(root);
        stage.setScene(scene);
//        swNode.prefHeight(w);
//        swNode.prefHeight(h);

        stage.show();
    }


    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    int sw = (int) w;
                    int sh = (int) h;
                    g2d.setPaint(makeAwtTexturePaint(s, true));
                    g2d.fillRect(0, 0, sw, sh);
                }

            };
            panel.setSize(600, 600);
            swingNode.setContent(panel);
        });
    }
}
