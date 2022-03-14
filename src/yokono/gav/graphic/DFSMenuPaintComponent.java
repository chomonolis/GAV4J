package yokono.gav.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;

public class DFSMenuPaintComponent extends BFSMenuPaintComponent {

	public static void MenuPaint(Graphics g, int getWidth, int getHeight, GraphElements ge, LinkedList<BNode> stack) {
		BFSMenuPaintComponent.MenuPaint(g, getWidth, getHeight, ge, stack, 1);
	}

	protected static void paintStack(Graphics2D g2, int panelW, int panelH, LinkedList<BNode> stack) {
		if(setCoordinate == false) {
			BFSMenuPaintComponent.setLineCoordinate(panelW, panelH);
		}
		g2.setColor(Color.black);
		g2.draw(listBottom);
		if(stack.size() >= listEllipse.length) {
			BFSMenuPaintComponent.paintQueue(g2, panelW, panelH, stack);
			return ;
		}
		g2.draw(listLineL);
		g2.draw(listLineR);
		int diff = listEllipse.length-stack.size();
		for(int i = 0; i < listEllipse.length; i++) {
			if(i < stack.size()) {
				Ellipse2D.Double now = listEllipse[i+diff];
				g2.setColor(BFSMenuPaintComponent.CHECKED_N);
				g2.fill(now);
				BNode n = stack.get(i);
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
