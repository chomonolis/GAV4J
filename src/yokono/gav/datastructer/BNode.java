package yokono.gav.datastructer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import yokono.gav.graphic.CentralPanel;
import yokono.gav.staticDefine.SDC;

public class BNode extends Node {
	public static final long INF_DISTANCE = (long) 9e18;

	public long subNumber = INF_DISTANCE;
	public BNode parent = null;
	public boolean chk;
	public int snx = -1, sny = -1;
	public int subCounter = 0;

	public BNode() {
		super();
		this.chk = false;
	}

	public BNode(int n) {
		super(n);
		this.chk = false;
	}

	public void resetNode() {
		this.chk = false;
		this.subNumber = INF_DISTANCE;
		this.nodeColor = SDC.DEFAULT_N;
		this.fontColor = SDC.DEFAULT_N_F;
		for(int i = 0; i < this.subCounter; i++) {
			this.path.remove(this.path.size() - 1);
		}
		this.subCounter = 0;
		for(Path nh : this.path) {
			nh.resetPath();
		}
	}

	@Override
	public void printNode() {
		super.printNode();
		System.out.println("BNode : " + this.chk);
	}

	public void paintNode(Graphics2D g2, int menuFlag){
		this.paintNode(g2, this.nodeColor, this.fontColor, menuFlag);
	}

	public void paintNode(Graphics2D g2, Color fl, Color ft, int menuFlag) {
		if(menuFlag == CentralPanel.DIJKSTRA_MENU) {
			g2.setColor(fl);
			g2.fill(this);
			g2.setColor(ft);
			if(this.subNumber < 10 || this.subNumber == INF_DISTANCE) {
				g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 28));
				if(this.subNumber == INF_DISTANCE) g2.drawString("∞", (int)(this.getCenterX()-this.getWidth()/2.5), (int)(this.getCenterY()+this.getHeight()/3));
				else g2.drawString(Long.toString(this.subNumber), (int)(this.getCenterX()-this.getWidth()/4), (int)(this.getCenterY()+this.getHeight()/3));;

			}else if(this.subNumber < 100) {
				g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 23));
				g2.drawString(Long.toString(this.subNumber), (int)(this.getCenterX()-this.getWidth()/2.5), (int)(this.getCenterY()+this.getHeight()/3));
			}else{
				g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
				g2.drawString(Long.toString(this.subNumber), (int)(this.getCenterX()-this.getWidth()/2.5), (int)(this.getCenterY()+this.getHeight()/4));
			}
		}else {
			super.paintNode(g2, fl, ft);
		}
	}

	public void paintNode(Graphics2D g2, int menuFlag, int getWidth, int getHeight) {
		this.paintNode(g2, this.nodeColor, this.fontColor, menuFlag, getWidth, getHeight);
	}

	public void paintNode(Graphics2D g2, Color fl, Color ft, int menuFlag, int getWidth, int getHeight) {
		if(menuFlag == CentralPanel.DISJOINTSET_MENU){
			if(this.subNumber != 0) {
				int dist = 20;
				int mh = getHeight/2;
				this.snx = (int)this.getCenterX() + dist;
				if(this.getCenterY() < mh) {
					this.sny = (int)this.getCenterY() + dist;
				}else {
					this.sny = (int)this.getCenterY() - dist;
				}
				g2.setColor(Color.black);
				g2.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 23));
				String s = Long.toString(this.subNumber);
				if(this.subNumber == BNode.INF_DISTANCE) s = "1";
				g2.drawString(s, (int)(this.snx), (int)(this.sny));
			}
		}
		this.paintNode(g2, fl, ft, menuFlag);
	}
}
