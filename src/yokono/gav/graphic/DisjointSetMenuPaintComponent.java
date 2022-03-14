package yokono.gav.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.DoubleNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;
import yokono.gav.staticDefine.SDC;

public class DisjointSetMenuPaintComponent extends BFSMenuPaintComponent{
	protected static final double LIST_EREA_X2 = 0.85;
	private static Ellipse2D.Double[] listEllipseL = new Ellipse2D.Double[15];
	private static Ellipse2D.Double[] listEllipseR = new Ellipse2D.Double[15];
	private static boolean setCoordinateList = false;

	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge) {
		Graphics2D g2 = (Graphics2D)g;
		DisjointSetMenuPaintComponent.paintArray(g2, getWidth, getHeight, ge.doubleNodeArray, ge.doubleNodeIndex);

		double makeListErea = 1;
		for(Node n : ge.graph) {
			if(n.px != -1 && n.py != -1) {
				if(listLineL.x1 <= n.px*getWidth + Node.DEFAULT_SIZE) makeListErea = DisjointSetMenuPaintComponent.LIST_EREA_X2;
			}
		}

		GenericMenuPaintComponent.gAllNodeSCAS(ge, getWidth, getHeight, makeListErea);
		for(Node n : ge.graph) {
			for(Path p : n.path) {
				if(ge.primaryCurrentPath != null && ge.primaryCurrentPath.equals(p) == true || ge.secondaryCurrentPath != null && ge.secondaryCurrentPath.equals(p) == true) {
					p.setCoordinate(n);
					p.paintPath(g2, SDC.CURRENT_P);
				}else {
					if(p.pColor.equals(Color.white) == true) continue;
					p.setCoordinate(n);
					p.paintPath(g2);
				}
			}
		}
		for(Node n : ge.graph) {
			if(ge.currentNode != null && ge.currentNode.equals(n) == true || ge.currentNode2 != null && ge.currentNode2.equals(n) == true) {
				if(n instanceof BNode) {
					BNode bn = (BNode)n;
					bn.paintNode(g2, SDC.CURRENT_N, SDC.CURRENT_N_F, CentralPanel.DISJOINTSET_MENU, getWidth, getHeight);
				}
			}else {
				if(n instanceof BNode) {
					BNode bn = (BNode)n;
					bn.paintNode(g2, CentralPanel.DISJOINTSET_MENU, getWidth, getHeight);
				}
			}
		}
	}

	protected static void setLineCoordinate(int panelW, int panelH) {
		if(setCoordinateList == false) {
			listLineL.setLine(panelW*(LIST_EREA_X2+0.04), panelH*0.1, panelW*(LIST_EREA_X2+0.04), panelH*0.1+(Node.DEFAULT_SIZE*listEllipse.length));
			listLineR.setLine(panelW*0.98, panelH*0.1, panelW*0.98, panelH*0.1+(Node.DEFAULT_SIZE*listEllipse.length));
			double centerX = (listLineL.x1 + listLineR.x1)/2;
			double centerX1 = (listLineL.x1+centerX)/2-Node.DEFAULT_SIZE/2;
			double centerX2 = (listLineR.x1+centerX)/2-Node.DEFAULT_SIZE/2;
			for(int i = 0; i < listEllipse.length; i++) {
				double y;
				if(i != 0) y = listEllipseL[i-1].y + listEllipseL[i-1].height;
				else y = listLineL.y1;
				listEllipseL[i] = new Ellipse2D.Double(centerX1, y, Node.DEFAULT_SIZE, Node.DEFAULT_SIZE);
			}
			for(int i = 0; i < listEllipse.length; i++) {
				double y;
				if(i != 0) y = listEllipseR[i-1].y + listEllipseR[i-1].height;
				else y = listLineR.y1;
				listEllipseR[i] = new Ellipse2D.Double(centerX2, y, Node.DEFAULT_SIZE, Node.DEFAULT_SIZE);
			}
			setCoordinateList = true;
		}
	}

	protected static void paintArray(Graphics2D g2, int panelW, int panelH, ArrayList<DoubleNode> doubleNodeArray, int doubleNodeIndex) {
		if(setCoordinateList == false) {
			DisjointSetMenuPaintComponent.setLineCoordinate(panelW, panelH);
		}
		g2.setColor(Color.black);
		g2.draw(listLineL);
		g2.draw(listLineR);
		for(int i = 0; i < listEllipse.length; i++) {
			int index = doubleNodeIndex+1+i;
			if(index < doubleNodeArray.size()) {
				DoubleNode dn = doubleNodeArray.get(index);
				Ellipse2D.Double nowl = listEllipseL[i];
				Ellipse2D.Double nowr = listEllipseR[i];
				DisjointSetMenuPaintComponent.paintEllipse(g2, panelW, panelH, dn.node1, nowl);
				DisjointSetMenuPaintComponent.paintEllipse(g2, panelW, panelH, dn.node2, nowr);
			}
		}
	}

	private static void paintEllipse(Graphics2D g2, int panelW, int panelH, Node n, Ellipse2D.Double now) {
		g2.setColor(n.nodeColor);
		g2.fill(now);
		g2.setColor(n.fontColor);
		if(n.number < 10) {
			g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 28));
			g2.drawString(Integer.toString(n.number), (int)(now.getCenterX()-now.getWidth()/4), (int)(now.getCenterY()+now.getHeight()/3));
		}else if(n.number < 100) {
			g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 23));
			g2.drawString(Integer.toString(n.number), (int)(now.getCenterX()-now.getWidth()/2.5), (int)(now.getCenterY()+now.getHeight()/3));
		}else{
			g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
			g2.drawString(Integer.toString(n.number), (int)(now.getCenterX()-now.getWidth()/2.5), (int)(now.getCenterY()+now.getHeight()/4));
		}
	}
}
