package yokono.gav.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.NewPath;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;
import yokono.gav.staticDefine.SDC;

public class InputGraphMenuPaintComponent  {

	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge, NewPath newPath, boolean isHaveHevy) {
		Graphics2D g2 = (Graphics2D)g;
		int panelW = getWidth, panelH = getHeight;
		for(Node n : ge.graph) {
			if(n.px != -1 && n.py != -1) {
				n.setCoordinateAndSize(n.px*panelW, n.py*panelH, Node.DEFAULT_SIZE);
			}
		}
		for(Node n : ge.graph) {
			for(Path path : n.path) {
				path.setCoordinate(n);
				path.paintPath(g2, isHaveHevy);
			}
		}
		for(Node n : ge.graph) {
			if(ge.currentNode != null && ge.currentNode.equals(n) == true) {
				n.paintNode(g2, SDC.CURRENT_N, SDC.CURRENT_N_F);
			}else {
				n.paintNode(g2, SDC.DEFAULT_N, SDC.DEFAULT_N_F);
			}

		}
		if(newPath.makingNewPath == true) {
			g2.setColor(new Color(Integer.parseInt("3cb371", 16)));
			if(newPath.newPathLine != null)g2.draw(newPath.newPathLine);
		}
	}
}
