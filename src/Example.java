import processing.core.*;

public class Example extends PApplet {
	PImage img;
	public void settings() {
		size(800, 800);
	}
	public void setup() {
		img=loadImage("data\\20161.jpg");
		img.resize(100, 100);
	}

	public void draw() {
		image(img,20,20);
		stroke(255,0,0);
		line(0, 0, mouseX, mouseY);
	}

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new Example());
	}

}
