package yokono.gav.graphic;

import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import yokono.gav.collbacks.setHevyLabelCollback;
import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.DoubleNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.NewHv;
import yokono.gav.datastructer.NewPath;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;

public class InputGraphMenuMouseListener {
	private static Node addPathFrom = null;
	private static int mouseX = -1, mouseY = -1;

	public static void mousePressed(MouseEvent e, ArrayList<Node> graph, NewPath newPath) {
		if(mouseX == -1 && mouseY == -1) {
			mouseX = e.getX();
			mouseY = e.getY();
			for(Node n : graph) {
				if(n.contains(mouseX, mouseY) == true) {
					mouseX = (int)n.getCenterX();
					mouseY = (int)n.getCenterY();
					addPathFrom = n;
					newPath.makingNewPath = true;
					newPath.newPathLine = null;
				}
			}
		}
	}

	public static void mouseReleased(MouseEvent e, ConsolePanel p, GraphElements graphElements, int getWidth, int getHeight, NewPath newPath, boolean mode, boolean hvm, NewHv nh, setHevyLabelCollback shl) {
		long hv = 1;
		if(mouseX != -1 && mouseY != -1) {
			boolean isTouchNode = false;
			for(Node n : graphElements.graph) {
				if(n.contains(e.getX(), e.getY()) == true) {
					if(addPathFrom != null) {
						if(n.equals(addPathFrom) == true) {
							graphElements.setCurrentNode(n);
							mrEnd(newPath);
							return ;
						}

						if(hvm == true) {
							if(nh.chkHv == false) {
								mrEnd(newPath);
								return ;
							}
							hv = nh.nxHv;
							nh.nxHv = 0L;
							nh.chkHv = false;
							shl.setHevyLabel();
						}

						boolean f = false;
						for(Path ph : addPathFrom.path) {
							if(ph.to.equals(n) == true) f = true;
						}
						if(f == false) {
							addPathFrom.addPath(n, hv);
							if(mode == false) {
								boolean f2 = false;
								for(Path p2 : n.path) {
									if(p2.to.equals(addPathFrom) == true) f2 = true;
								}
								if(f2 == false) {
									n.addPath(addPathFrom, hv);
									graphElements.doubleNodeArray.add(new DoubleNode(n, addPathFrom));
								}
							}
						}
						mrEnd(newPath);
						return ;
					}
				}else {
					if(Math.hypot(n.getCenterX()-e.getX(), n.getCenterY()-e.getY()) <= Node.DEFAULT_SIZE*1.5) {
						isTouchNode = true;
					}
				}
			}
			if(Math.hypot(mouseX - e.getX(), mouseY-e.getY()) > 10 || isTouchNode == true) {
				mrEnd(newPath);
				return ;
			}
			double dx = (double)(e.getX() - Node.DEFAULT_SIZE/2);
			double dy = (double)(e.getY() - Node.DEFAULT_SIZE/2);
			if(dy <= 0 || dx <= 0 || getWidth <= dx+Node.DEFAULT_SIZE || getHeight <= dy+Node.DEFAULT_SIZE) {
				mrEnd(newPath);
				return ;
			}
			double px = dx/getWidth;
			double py = dy/getHeight;
			Node n = new BNode();//
			n.setRatioCoordinate(px, py);
			graphElements.addNode(n);
			if(graphElements.currentNode == null) {
				graphElements.setCurrentNode(n);
				p.startButton.setEnabled(true);
			}
			mrEnd(newPath);
		}
	}

	private static void mrEnd(NewPath newPath) {
		newPath.makingNewPath = false;
		addPathFrom = null;
		mouseX = -1;
		mouseY = -1;
	}


	public static void mouseDragged(MouseEvent e, NewPath newPath) {
		if(newPath.makingNewPath == true) {
			newPath.newPathLine = new Line2D.Double(mouseX, mouseY, e.getX(), e.getY());
		}
	}
}
