package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Force 2^n x 2^n display
//Quadrant progressive rendering
//Try adding check before any calculation to see if screen should be redrawn


public class MandelbrotGUI extends Application {
	
	double previousX;
	double previousY;
	double deltaZoom = 0.5;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        Canvas canvas = new Canvas(600, 600);
        MandelbrotDrawer drawer = new MandelbrotDrawer(canvas.getGraphicsContext2D());
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				previousX = (int) Math.round(e.getX());
				previousY = (int) Math.round(e.getY());
			}
        	
        });
        
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				drawer.drag((int)Math.round(e.getX() - previousX), (int)Math.round(e.getY() - previousY));
				previousX = e.getX();
				previousY = e.getY();
			}
        	
        });
        
        pane.setCenter(canvas);
        Scene scene = new Scene(pane, 512, 512);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				switch(e.getCode()) {
				case W:
					drawer.zoom(deltaZoom);
					break;
				case S:
					drawer.zoom(1 + deltaZoom);
					break;
				default:

					break;
				}
			}
        	
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        drawer.draw(512,255);
        
    }
	

}

