package com.mycompany.nmm;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class MainApp extends Application {
    static public final double OUTER_RECT_START_X = 200.0;
    static public final double OUTER_RECT_START_Y = 50.0;
    static public final double OUTER_RECT_SIZE = 400.0;
    
    static public final double MIDDLE_RECT_START_X = 250.0;
    static public final double MIDDLE_RECT_START_Y = 100.0;
    static public final double MIDDLE_RECT_SIZE = 300.0;
    
    static public final double INNER_RECT_START_X = 300.0;
    static public final double INNER_RECT_START_Y = 150.0;
    static public final double INNER_RECT_SIZE = 200.0;
    
    static public final double CIRCLE_RADIUS = 14.0;
    
    static public final double STROKE_WIDTH = 5.0;
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Group root = new Group();
        
        Scene scene = new Scene(root, 800.0, 500.0, Color.color(31.0/255.0, 171.0/255.0, 168.0/255.0));
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Nine Men's Morris");
        stage.setScene(scene);
        
        // draw rectangles
        root.getChildren().add(this.drawRect(OUTER_RECT_START_X, OUTER_RECT_START_Y, OUTER_RECT_SIZE, OUTER_RECT_SIZE));
        root.getChildren().add(this.drawRect(MIDDLE_RECT_START_X, MIDDLE_RECT_START_Y, MIDDLE_RECT_SIZE, MIDDLE_RECT_SIZE));
        root.getChildren().add(this.drawRect(INNER_RECT_START_X, INNER_RECT_START_Y, INNER_RECT_SIZE, INNER_RECT_SIZE));
        
        //draw lines
        root.getChildren().add(this.drawLine(400.0, 50.0, 400.0, 150));
        root.getChildren().add(this.drawLine(400.0, 350.0, 400.0, 450));
        root.getChildren().add(this.drawLine(200.0, 250.0, 300.0, 250));
        root.getChildren().add(this.drawLine(500.0, 250.0, 600.0, 250));
        
        // draw token-holder circles in the outer rect
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(i == 1 && 1 == j))
                    root.getChildren().add(this.drawCircle(OUTER_RECT_START_X + j * (OUTER_RECT_SIZE / 2), OUTER_RECT_START_Y + i * (OUTER_RECT_SIZE / 2), CIRCLE_RADIUS));
            }
        }
        
        // draw token-holder circles in the middle rect
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(i == 1 && 1 == j))
                    root.getChildren().add(this.drawCircle(MIDDLE_RECT_START_X + j * (MIDDLE_RECT_SIZE / 2), MIDDLE_RECT_START_Y + i * (MIDDLE_RECT_SIZE / 2), CIRCLE_RADIUS));
            }
        }
        
        // draw token-holder circles in the inner rect
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(i == 1 && 1 == j))
                    root.getChildren().add(this.drawCircle(INNER_RECT_START_X + j * (INNER_RECT_SIZE / 2), INNER_RECT_START_Y + i * (INNER_RECT_SIZE / 2), CIRCLE_RADIUS));
            }
        }
        
        stage.show();
    }
    
    private Rectangle drawRect(double x, double y, double width, double height) {
        Rectangle r = new Rectangle();
        
        r.setX(x);
        r.setY(y);
        r.setWidth(width);
        r.setHeight(height);
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.WHITE);
        r.setStrokeWidth(STROKE_WIDTH);
        
        return r;
    }
    
    private Line drawLine(double startX, double startY, double endX, double endY) {
        Line l = new Line();
        
        l.setStartX(startX);
        l.setStartY(startY);
        l.setEndX(endX);
        l.setEndY(endY);
        l.setStrokeWidth(STROKE_WIDTH);
        l.setStroke(Color.WHITE);
        
        return l;
    }
    
    private Circle drawCircle(double centerX, double centerY, double radius) {
        Circle c = new Circle();
        
        c.setCenterX(centerX);
        c.setCenterY(centerY);
        c.setRadius(radius);
        c.setFill(Color.WHITE);
        
        return c;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
