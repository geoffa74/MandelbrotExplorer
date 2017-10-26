package ui;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MandelbrotDrawer {
	
	private GraphicsContext gc;
	int length;
	MandelbrotDrawerService service;
	int blockLength;
	
	public MandelbrotDrawer(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public void draw(int length, int iterations) {
		this.length = length;
		blockLength = length;
		service = new MandelbrotDrawerService();
		service.setLength(length);
		service.setIterations(iterations);
		service.setBlockLength(length);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				drawLayer(service.getValue());
				blockLength /= 2;
				if(blockLength >= 1) {
					service.setBlockLength(blockLength);
					service.reset();
					service.start();
				}
			}
			
		});
		service.start();
	}
	
	
	
	protected void drawLayer(Color[] colors) {
		if(blockLength == length) {
			gc.setFill(colors[0]);
			gc.fillRect(0, 0, blockLength, blockLength);
		} else {
			int index = 0;
			for(int i = 0; i < length; i += blockLength * 2) {
				for(int j = 0; j < length; j += blockLength * 2) {
					gc.setFill(colors[index]);
					gc.fillRect(i + blockLength, j, blockLength, blockLength);
					index++;
					gc.setFill(colors[index]);
					gc.fillRect(i , j + blockLength, blockLength, blockLength);
					index++;
					gc.setFill(colors[index]);
					gc.fillRect(i + blockLength, j + blockLength, blockLength, blockLength);
					index++;
				}
			}
		}
	}

	public void drag(int deltaX, int deltaY) {
		service.cancel();
		blockLength = length;
		service.setBlockLength(length);
		service.drag(deltaX, deltaY);
		service.restart();		
	}

	public void zoom(double zoom) {
		service.cancel();
		blockLength = length;
		service.setBlockLength(length);
		service.zoom(zoom);
		service.restart();				
	}
	

}
