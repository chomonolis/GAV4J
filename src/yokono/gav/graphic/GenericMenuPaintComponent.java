package yokono.gav.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;
import yokono.gav.staticDefine.SDC;

public class GenericMenuPaintComponent {
	public static void gAllNodeSCAS(GraphElements ge, int getWidth, int getHeight){
		GenericMenuPaintComponent.gAllNodeSCAS(ge, getWidth, getHeight, 1d);
	}

	public static void gAllNodeSCAS(GraphElements ge, int getWidth, int getHeight, double xx) {
		for(Node n : ge.graph) {
			if(n.px != -1 && n.py != -1) {
				n.setCoordinateAndSize(n.px*getWidth*xx, n.py*getHeight, Node.DEFAULT_SIZE);
			}
		}
	}

	public static void gAllPathPaint(Graphics2D g2, GraphElements ge, int menu) {
		for(Node n : ge.graph) {
			for(Path path : n.path) {
				Color c = path.pColor;
				if(ge.primaryCurrentPath != null && ge.primaryCurrentPath.equals(path) == true){
					c = SDC.CURRENT_P;
				}
				if(ge.secondaryCurrentPath != null && ge.secondaryCurrentPath.equals(path) == true){
					c = SDC.CURRENT_P;
				}
				path.setCoordinate(n);
				path.paintPath(g2, c, menu);
			}
		}
	}

	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge, int menu) {
		Graphics2D g2 = (Graphics2D)g;

		GenericMenuPaintComponent.gAllNodeSCAS(ge, getWidth, getHeight);
		GenericMenuPaintComponent.gAllPathPaint(g2, ge, menu);

		for(Node n : ge.graph) {
			BNode bn = null;
			if(n instanceof BNode) bn = (BNode)n;
			else break;
			if(ge.currentNode != null && ge.currentNode.equals(n) == true) {
				bn.paintNode(g2, SDC.CURRENT_N, SDC.CURRENT_N_F, menu);
			}else if(ge.subCurrentNode != null && ge.subCurrentNode.equals(n) == true) {
				bn.paintNode(g2, SDC.SUBCURRENT_N, SDC.SUBCURRENT_N_F, menu);
			}else if(bn != null && bn.chk == true) {
				bn.paintNode(g2, BFSMenuPaintComponent.CHECKED_N, BFSMenuPaintComponent.CHECKED_N_F, menu);
			}else {
				bn.paintNode(g2, SDC.DEFAULT_N, SDC.DEFAULT_N_F, menu);
			}
		}
	}
}
