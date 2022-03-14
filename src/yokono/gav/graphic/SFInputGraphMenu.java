package yokono.gav.graphic;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import yokono.gav.collbacks.setHevyLabelCollback;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.NewHv;
import yokono.gav.datastructer.NewPath;
import yokono.gav.datastructer.Node;

public class SFInputGraphMenu {
	public static void mousePressed(MouseEvent e, ArrayList<Node> graph, NewPath newPath) {
		InputGraphMenuMouseListener.mousePressed(e, graph, newPath);
	}

	public static void mouseReleased(MouseEvent e, ConsolePanel p, GraphElements graphElements, int getWidth, int getHeight, NewPath newPath, boolean mode, boolean hvm, NewHv nh, setHevyLabelCollback shl) {
		InputGraphMenuMouseListener.mouseReleased(e, p, graphElements, getWidth, getHeight, newPath, mode, hvm, nh, shl);
	}


	public static void mouseDragged(MouseEvent e, NewPath newPath) {
		InputGraphMenuMouseListener.mouseDragged(e, newPath);
	}

	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge, NewPath newPath, boolean isHaveHevy) {
		InputGraphMenuPaintComponent.MenuPaint(g, getWidth, getHeight, ge, newPath, isHaveHevy);
	}
}
