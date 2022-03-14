package yokono.gav.datastructer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import yokono.gav.staticDefine.SDC;

public class Node extends Ellipse2D.Double {
	public static final double DEFAULT_SIZE = 30d;
	public static int allCount = 0;

	public int number;
	public double px = -1, py = -1;
	public Color nodeColor = SDC.DEFAULT_N, fontColor = SDC.DEFAULT_N_F;
	public ArrayList<Path> path = new ArrayList<Path>();

	public void setCoordinateAndSize(double x, double y, double sz) {
		this.x = x;
		this.y = y;
		this.width = sz;
		this.height = sz;
	}

	public void setRatioCoordinate(double px, double py) {
		if(px < 0 || 1 < px || py < 0 || 1 < py) return;
		this.px = px;
		this.py = py;
	}

	public Node() {
		this.number = Node.allCount;
		Node.allCount++;
	}

	public Node(int n) {
		this.number = n;
	}

	public void addPath(Node nx, long hv) {
		this.path.add(new Path(nx, hv));
	}

	public void addPath(int nx, long hv, GraphElements g) {
		this.path.add(new Path(g.graph.get(nx), hv));
	}

	public void setColor(Color fl, Color ft) {
		this.nodeColor = fl;
		this.fontColor = ft;
	}

	public void paintNode(Graphics2D g2) {
		paintNode(g2, this.nodeColor, this.fontColor);
	}

	public void paintNode(Graphics2D g2, Color fl, Color ft) {
		g2.setColor(fl);
		g2.fill(this);
		g2.setColor(ft);
		if(this.number < 10) {
			g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 28));
			g2.drawString(Integer.toString(this.number), (int)(this.getCenterX()-this.getWidth()/4), (int)(this.getCenterY()+this.getHeight()/3));
		}else if(this.number < 100) {
			g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 23));
			g2.drawString(Integer.toString(this.number), (int)(this.getCenterX()-this.getWidth()/2.5), (int)(this.getCenterY()+this.getHeight()/3));
		}else{
			g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
			g2.drawString(Integer.toString(this.number), (int)(this.getCenterX()-this.getWidth()/2.5), (int)(this.getCenterY()+this.getHeight()/4));
		}
	}

	public void printNode() {
		System.out.print(this.number + ": ");
		for(Path p : this.path) {
			System.out.print(p.to.number + "," + p.hv + " " + p.pTheta*180/Math.PI + " ");
		}
		System.out.println();
	}

	public static void reset() {
		Node.allCount = 0;
	}
}
