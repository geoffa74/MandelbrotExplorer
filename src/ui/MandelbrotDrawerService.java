package ui;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import math.Complex;

public class MandelbrotDrawerService extends Service<Color[]> {
	
	private Complex n;
	private int iterations;
	double minX;
	double minY;
	double maxX;
	double maxY;
	int blockLength;
	int length;
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setBlockLength(int blockLength) {
		this.blockLength = blockLength;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public MandelbrotDrawerService() {
		minX = -2;
		minY = -2;
		maxX = 2;
		maxY = 2;
	}

	@Override
	protected Task<Color[]> createTask() {
		return new Task<Color[]>() {

			@Override
			protected Color[] call() throws Exception {
				Color[] colors;
				if(blockLength == length) {
					colors = new Color[1];
					colors[0] = getColor(0,0);
				} else {
					int numColors = (length * length) / (blockLength * blockLength) * 3 / 4;
					colors = new Color[numColors];
					int index = 0;
					for(int i = 0; i < length; i += blockLength * 2) {
						for(int j = 0; j < length; j += blockLength * 2) {
							colors[index] = getColor(i + blockLength, j);
							index++;
							colors[index] = getColor(i , j + blockLength);
							index++;
							colors[index] = getColor(i + blockLength, j + blockLength);
							index++;

						}
					}
				}
				return colors;
			}
		};
	}
	
	private Color getColor(int x, int y) {
	
		double r;
		double c;
		r = x * (maxX - minX) / length + minX;
		c = maxY - y * (maxY - minY) / length;
		Complex n = new Complex(r, c);
		Complex result = new Complex(r, c);
		int iteration = 0;
		while(result.real < 2 && result.complex < 2 && iteration < iterations) {
			result = (result.multiply(result)).add(n);
			iteration++;
		}
		return new Color(((double)iteration)/((double)iterations), 
				((double)iteration)/((double)iterations), 
				((double)iteration)/((double)iterations), 1);	
			
	}

	public void drag(int deltaX, int deltaY) {
		System.out.println(deltaX + ", " + deltaY);
		double deltaR = -1 * deltaX * (maxX - minX) / length;
		double deltaC = deltaY * (maxY - minY) / length;
		minX += deltaR;
		minY += deltaC;
		maxX += deltaR;
		maxY += deltaC;
		
	}
	
	public void zoom(double zoom) {
		double centerX = (maxX + minX)/2;
		double centerY = (maxY + minY)/2;
		double deltaX = zoom * (maxX - minX)/2;
		double deltaY = zoom * (maxY - minY)/2;
		minX = centerX - deltaX;
		maxX = centerX + deltaX;
		minY = centerY - deltaY;
		maxY = centerY + deltaY;
		
	}

}

/*

*/