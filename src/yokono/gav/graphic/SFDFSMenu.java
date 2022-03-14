package yokono.gav.graphic;

import java.awt.Graphics;
import java.util.LinkedList;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;

public class SFDFSMenu {
	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge, LinkedList<BNode> stack) {
		DFSMenuPaintComponent.MenuPaint(g, getWidth, getHeight, ge, stack);
	}
}
