package yokono.gav.datastructer;

import java.util.ArrayList;

public class GraphElements {
	public ArrayList<Node> graph;
	public ArrayList<DoubleNode> doubleNodeArray = new ArrayList<DoubleNode>();
	public int doubleNodeIndex = 0;
	public Node currentNode = null, currentNode2 = null, subCurrentNode = null;
	public Path primaryCurrentPath = null, secondaryCurrentPath = null;

	public <E> GraphElements(int n, E e) {
		this.graph = new ArrayList<Node>();
		if(e == null || e instanceof Node) {
			for(int i = 0; i < n; i++) {
				if(e instanceof BNode)this.graph.add(new BNode());
				else this.graph.add(new Node());
			}
		}else {
			System.err.println("e not instanceof Node");
		}
	}

	public <E> GraphElements(int n, E e, int st) {
		this(n, e);
		this.currentNode = this.graph.get(st);
	}

	public void setCurrentNode(Node n) {
		this.currentNode = n;
		this.subCurrentNode = null;
		this.primaryCurrentPath = null;
		this.secondaryCurrentPath = null;
	}

	public void setSubCurrentNode(Node n) {
		if(this.currentNode != null) {
			this.subCurrentNode = n;
			for(Path p : this.currentNode.path) {
				if(p.to.equals(this.subCurrentNode) == true) {
					this.primaryCurrentPath = p;
					for(Path sp: this.subCurrentNode.path) {
						if(sp.to.equals(this.currentNode) == true) {
							this.secondaryCurrentPath = sp;
						}
					}
				}
			}
		}
	}

	public void setCurrentN2NPaht(Node n1, Node n2) {
		for(Path p : n1.path) {
			if(p.to.equals(n2) == true) {
				this.primaryCurrentPath = p;
				for(Path p2 : n2.path) {
					if(p2.to.equals(n1) == true) {
						this.secondaryCurrentPath = p2;
						break;
					}
				}
				break;
			}
		}
	}

	protected void setPrimaryCurrentPath(Path p) {
		this.primaryCurrentPath = p;
	}

	protected void setSecondaryCurrentPath(Path p) {
		this.secondaryCurrentPath = p;
	}

	public void addNode(Node a) {
		this.graph.add(a);
	}

	public void addNode() {
		this.graph.add(new Node());
	}

	public void addPath(int num, int nx, long hv) {
		this.graph.get(num).addPath(nx, hv, this);
	}

	public void printGraphElements() {
		if(this.currentNode != null)System.out.println("currentNode: " + this.currentNode.number);
		if(this.currentNode2 != null)System.out.println("currentNode2: " + this.currentNode2.number);
		if(this.subCurrentNode != null)System.out.println("subCurrentNode: " + this.subCurrentNode.number);
		if(this.primaryCurrentPath != null)System.out.println("currentPath: " + this.primaryCurrentPath.to.number + " " + this.primaryCurrentPath.hv);

		for(Node n : graph) {
			n.printNode();
		}

		for(DoubleNode dn : doubleNodeArray) {
			System.out.println(dn.node1.number + " " + dn.node2.number);
		}
	}
}
