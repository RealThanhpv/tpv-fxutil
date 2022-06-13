package tpv.fxutil.image;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tpv.fxutil.paint.PaintUtils;
import tpv.fxutil.paint.SwingViewApp;

import javax.swing.*;
import java.awt.*;

public class FxViewApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        HBox root = new HBox();
        VBox fxCon = new VBox();
        VBox swCon = new VBox();
        root.getChildren().addAll(fxCon, swCon);

        Rectangle rect = new Rectangle(200, 200);
        TexturePaint swPaint = SwingViewApp.makeTexturePaint(10, true);

        javafx.scene.paint.Paint fxPaint = PaintUtils.awtTexturePaintToFXImagePattern(swPaint);

        rect.setFill(fxPaint);
        fxCon.getChildren().add(rect);

//        SwingNode swNode = new SwingNode();
//        createSwingContent(swNode);
//        swCon.getChildren().add(swNode);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }


    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            SwingViewApp.TestPanel panel = new SwingViewApp.TestPanel();

            swingNode.setContent(
                    new JLabel("Here is Swing")
//                  panel
            );
        });
    }
}
