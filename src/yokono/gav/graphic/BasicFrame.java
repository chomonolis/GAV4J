package yokono.gav.graphic;

import javax.swing.JFrame;

public class BasicFrame extends JFrame{
	public BasicFrame(int w, int h) {
		this.setSize(w, h);
		this.setTitle("GAV  Graph Algorithm Visualizer");
	}

	public BasicFrame() {
		this(1280, 720);
	}
}
