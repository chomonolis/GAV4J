package yokono.gav.datastructer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import yokono.gav.graphic.CentralPanel;
import yokono.gav.staticDefine.SDC;

public class Path extends Line2D.Double implements Comparable<Path> {
	public Node to;
	public long hv;
	public Arrow arrow;
	public double pTheta, zTheta, r;
	public double dx, dy;
	public Color pColor = SDC.DEFAULT_P;
	public int fSize = -1;

	public Path(Node to, long hv) {
		this.to = to;
		this.hv = hv;
		long tmp = hv;
		if(tmp != 0) {
			while(tmp != 0) {
				this.fSize++;
				tmp /= 10;
			}
		}else this.fSize++;
	}

	public void setColor(Color c) {
		this.pColor = c;
	}

	public void resetPath() {
		this.pColor = SDC.DEFAULT_P;
	}

	public void setCoordinate(Node from) {
		this.x1 = from.getCenterX(); this.y1 = from.getCenterY();
		this.x2 = this.to.getCenterX(); this.y2 = this.to.getCenterY();
		this.arrow = new Arrow(this.to, this);
		this.pTheta = Math.atan2(this.y2-this.y1, this.x2-this.x1);
		this.zTheta = this.pTheta - Math.PI/2;
		if(this.zTheta < -Math.PI) {
			this.zTheta += 2*Math.PI;
		}
		this.r = Math.hypot(this.x1-x2, this.y1-this.y2);
		this.dx = 0.7*this.r*Math.cos(this.pTheta) + this.x1;
		this.dy = 0.7*this.r*Math.sin(this.pTheta) + this.y1;
		double diff = Math.min(0.3*this.r, Node.DEFAULT_SIZE);
		this.dx += diff*Math.cos(this.zTheta);
		this.dy += diff*Math.sin(this.zTheta);
		if(this.zTheta < 0) {
			this.dy += 20;
		}
		if(this.pTheta < 0) {
			this.dx -= this.fSize*10;
		}
	}

	public void paintPath(Graphics2D g2) {
		paintPath(g2, this.pColor);
	}

	public void paintPath(Graphics2D g2, Color c) {
		g2.setColor(c);
		g2.draw(this);
		this.arrow.paintArrow(g2, c);
	}

	public void paintPath(Graphics2D g2, int mode) {
		this.paintPath(g2, this.pColor, mode);
	}

	public void paintPath(Graphics2D g2, boolean dijk) {
		if(dijk == true)this.paintPath(g2, this.pColor, CentralPanel.DIJKSTRA_MENU);
		else this.paintPath(g2);
	}

	public void paintPath(Graphics2D g2, Color c, int mode) {
		this.paintPath(g2, c);
		if(mode == CentralPanel.DIJKSTRA_MENU) {
			if(c.equals(SDC.DEFAULT_P) == true) g2.setColor(Color.black);
			else g2.setColor(c);
			g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 23));
			g2.drawString(Long.toString(this.hv), (int)(this.dx-10), (int)(this.dy-5));
		}
	}

	@Override
	public int compareTo(Path o) {
		return this.to.number - o.to.number;
	}
}
