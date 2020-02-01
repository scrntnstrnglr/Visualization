import processing.core.*;

public class Example extends PApplet {

	public void settings() {
		size(800, 800);
	}

	public void draw() {

		stroke(255);
		line(0, 0, mouseX, mouseY);
	}

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new Example());
	}

}
