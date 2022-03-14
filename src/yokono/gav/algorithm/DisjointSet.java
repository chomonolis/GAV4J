package yokono.gav.algorithm;

import java.awt.Color;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.DoubleNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;

public class DisjointSet extends Solver {
	public static final Color PARENT = Color.red;
	public static final Color PARENT_F = Color.white;
	public static final Color CHILD = Color.gray;
	public static final Color CHILD_F = Color.white;
	public static final Color C2PPATH = Color.blue;
	public static final Color NC2PPATH = Color.white;

	public DisjointSet(GraphElements ge) {
		super.getGraphs(ge);
	}

	@Override
	public void solveGraphs() {
		try {
			if((this.graphElements.graph.get(0) instanceof BNode) == false) {
				return ;
			}
			for(Node n : this.graphElements.graph) {
				BNode bn = (BNode) n;
				bn.subNumber = 1;
				bn.nodeColor = DisjointSet.PARENT;
				bn.fontColor = DisjointSet.PARENT_F;
				bn.parent = null;
				for(Path path : bn.path) {
					path.pColor = DisjointSet.NC2PPATH;
				}
			}
			super.stepStoper();
			for(int i = 0; i < this.graphElements.doubleNodeArray.size(); i++) {
				DoubleNode dn = this.graphElements.doubleNodeArray.get(i);
				this.graphElements.doubleNodeIndex = i;
				if(dn.node1 instanceof BNode && dn.node2 instanceof BNode) {
					this.graphElements.currentNode = null;
					this.graphElements.currentNode2 = null;
					this.graphElements.setCurrentN2NPaht(dn.node1, dn.node2);
					this.unite((BNode)dn.node1, (BNode)dn.node2);
					super.stepStoper();
				}
			}
			this.solverEnded = true;
			this.graphElements.currentNode = null;
			this.graphElements.currentNode2 = null;
			this.graphElements.primaryCurrentPath = null;
			this.graphElements.secondaryCurrentPath = null;
		} catch (ResetException e) {
			System.out.println("Disjoint set is Ended by Reset");
		}
	}

	private BNode find(BNode x) throws ResetException{
		this.graphElements.currentNode = x;
		super.stepStoper();
		if(x.parent == null) return x;
		this.changeParent(x, find(x.parent));
		return x.parent;
	}

	private boolean unite(BNode x, BNode y) throws ResetException{
		BNode xp = find(x);
		this.graphElements.currentNode2 = xp;
		BNode yp = find(y);
		this.graphElements.currentNode = yp;
		if(xp.equals(yp) == true) {
			return false;
		}
		if(xp.subNumber < yp.subNumber || xp.subNumber == yp.subNumber && xp.number > yp.number) {
			BNode tmp = xp;
			xp = yp;
			yp = tmp;
		}
		xp.subNumber += yp.subNumber;
		this.changeParent(yp, xp);
		return true;
	}

	private void changeParent(BNode child, BNode parent) {
		child.parent = parent;
		child.subNumber = 0;
		child.fontColor = DisjointSet.CHILD_F;
		child.nodeColor = DisjointSet.CHILD;
		boolean f = false;
		for(Path n : child.path) {
			if(n.to.equals(child.parent) == true) {
				n.pColor = DisjointSet.C2PPATH;
				f = true;
			}else {
				n.pColor = DisjointSet.NC2PPATH;
			}
		}
		if(f == false) {
			child.addPath(child.parent, 1L);
			Path p = child.path.get(child.path.size()-1);
			p.setCoordinate(child);
			p.pColor = DisjointSet.C2PPATH;
			child.subCounter++;
		}
	}

}
