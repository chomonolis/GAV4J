package yokono.gav.graphic;

import java.awt.Graphics;

import yokono.gav.datastructer.GraphElements;

public class SFDijkstraMenu {
	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge, int menu) {
		GenericMenuPaintComponent.MenuPaint(g, getWidth, getHeight, ge, menu);
	}
}
