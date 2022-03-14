package yokono.gav.graphic;

import java.awt.Graphics;

import yokono.gav.datastructer.GraphElements;

public class SFDisjointSetMenu {
	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge) {
		DisjointSetMenuPaintComponent.MenuPaint(g, getWidth, getHeight, ge);
	}
}
