package yokono.gav.datastructer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Arrow {
	public Line2D.Double ar1 = new Line2D.Double();
	public Line2D.Double ar2 = new Line2D.Double();
	public double theta;

	public Arrow(Node to, Path path) {
		this.theta = Math.atan2(path.y1-path.y2, path.x1-path.x2);;
		double r = to.getWidth()/2;
		double InsecX = to.getCenterX() + r*Math.cos(this.theta), InsecY = to.getCenterY() + r*Math.sin(this.theta);
		this.theta -= Math.PI/4d;
		ar1.setLine(InsecX, InsecY, InsecX+10*Math.cos(this.theta), InsecY+10*Math.sin(this.theta));
		ar2.setLine(InsecX, InsecY, InsecX+10*Math.cos(this.theta+Math.PI/2d), InsecY+10*Math.sin(this.theta+Math.PI/2d));
	}

	public void paintArrow(Graphics2D g2, Color c) {
		g2.setColor(c);
		g2.draw(this.ar1);
		g2.draw(this.ar2);
	}
}
