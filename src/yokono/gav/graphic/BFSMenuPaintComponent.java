package yokono.gav.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;
import yokono.gav.staticDefine.SDC;

public class BFSMenuPaintComponent {
	protected static final Color CHECKED_N = Color.red;
	protected static final Color CHECKED_N_F = Color.white;
	protected static final double LIST_EREA_X = 0.9;

	protected static Line2D.Double listLineL = new Line2D.Double();
	protected static Line2D.Double listLineR = new Line2D.Double();
	protected static Line2D.Double listBottom = new Line2D.Double();
	protected static Ellipse2D.Double[] listEllipse = new Ellipse2D.Double[15];
	protected static boolean setCoordinate = false;

	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge, LinkedList<BNode> queue, int mode) {
		Graphics2D g2 = (Graphics2D)g;
		int panelW = getWidth, panelH = getHeight;
		if(mode == 0) paintQueue(g2, panelW, panelH, queue);
		else if(mode == 1) DFSMenuPaintComponent.paintStack(g2, panelW, panelH, queue);

		double makeQueueErea = 1;
		for(Node n : ge.graph) {
			if(n.px != -1 && n.py != -1) {
				if(listLineL.x1 <= n.px*panelW + Node.DEFAULT_SIZE) makeQueueErea = BFSMenuPaintComponent.LIST_EREA_X;
			}
		}
		for(Node n : ge.graph) {
			if(n.px != -1 && n.py != -1) {
				n.setCoordinateAndSize(n.px*panelW*makeQueueErea, n.py*panelH, Node.DEFAULT_SIZE);
			}
		}
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
				path.paintPath(g2, c);
			}
		}
		for(Node n : ge.graph) {
			BNode bn = null;
			if(n instanceof BNode) bn = (BNode)n;
			if(ge.currentNode != null && ge.currentNode.equals(n) == true) {
				n.paintNode(g2, SDC.CURRENT_N, SDC.CURRENT_N_F);
			}else if(ge.subCurrentNode != null && ge.subCurrentNode.equals(n) == true) {
				n.paintNode(g2, SDC.SUBCURRENT_N, SDC.SUBCURRENT_N_F);
			}else if(bn != null && bn.chk == true) {
				bn.paintNode(g2, BFSMenuPaintComponent.CHECKED_N, BFSMenuPaintComponent.CHECKED_N_F);
			}else {
				n.paintNode(g2, SDC.DEFAULT_N, SDC.DEFAULT_N_F);
			}

		}
	}

	protected static void setLineCoordinate(int panelW, int panelH) {
		if(setCoordinate == false) {
			listLineL.setLine(panelW*(LIST_EREA_X+0.04), panelH*0.1, panelW*(LIST_EREA_X+0.04), panelH*0.1+(Node.DEFAULT_SIZE*listEllipse.length));
			listLineR.setLine(panelW*0.98, panelH*0.1, panelW*0.98, panelH*0.1+(Node.DEFAULT_SIZE*listEllipse.length));
			listBottom.setLine(listLineL.x1, listLineL.y2, listLineR.x1, listLineR.y2);
			double centerX = (listLineL.x1 + listLineR.x1)/2 - Node.DEFAULT_SIZE/2;
			for(int i = 0; i < listEllipse.length; i++) {
				double y;
				if(i != 0) y = listEllipse[i-1].y + listEllipse[i-1].height;
				else y = listLineL.y1;
				listEllipse[i] = new Ellipse2D.Double(centerX, y, Node.DEFAULT_SIZE, Node.DEFAULT_SIZE);
			}
			setCoordinate = true;
		}
	}

	protected static void paintQueue(Graphics2D g2, int panelW, int panelH, LinkedList<BNode> queue) {
		if(setCoordinate == false) {
			BFSMenuPaintComponent.setLineCoordinate(panelW, panelH);
		}
		g2.setColor(Color.black);
		g2.draw(listLineL);
		g2.draw(listLineR);
		for(int i = 0; i < listEllipse.length; i++) {
			if(queue.size() <= listEllipse.length) {
				if(i < queue.size()) {
					Ellipse2D.Double now = listEllipse[i];
					g2.setColor(BFSMenuPaintComponent.CHECKED_N);
					g2.fill(now);
					BNode n = queue.get(i);
					g2.setColor(BFSMenuPaintComponent.CHECKED_N_F);
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
			}else {
				Ellipse2D.Double now = listEllipse[i];
				if(i == listEllipse.length/2) {
					g2.setColor(Color.gray);
					g2.fill(now);
					continue;
				}
				g2.setColor(BFSMenuPaintComponent.CHECKED_N);
				g2.fill(now);
				BNode n;
				if(i < listEllipse.length/2) n = queue.get(i);
				else n = queue.get(queue.size()-(listEllipse.length-i));
				g2.setColor(BFSMenuPaintComponent.CHECKED_N_F);
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
	}
}
