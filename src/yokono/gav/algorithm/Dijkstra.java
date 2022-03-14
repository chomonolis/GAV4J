package yokono.gav.algorithm;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;

public class Dijkstra extends Solver {

	public Dijkstra(GraphElements g) {
		super.getGraphs(g);
	}

	@Override
	public void solveGraphs() {
		try {
			BNode now = (BNode)this.graphElements.currentNode;
			if(now == null) return ;
			now.subNumber = 0;
			while((now = chooseNode()) != null) {
				this.graphElements.setCurrentNode(now);
				super.stepStoper();
				for(Path nx : now.path) {
					BNode bnx = (BNode)nx.to;
					if(bnx.chk == true) continue;
					this.graphElements.setSubCurrentNode(nx.to);
					if(bnx.subNumber > now.subNumber + nx.hv) {
						bnx.subNumber = now.subNumber + nx.hv;
					}
					super.stepStoper();
				}
				super.nodeBooleanf2t(now);
			}
			this.solverEnded = true;
			this.graphElements.setCurrentNode(null);
		} catch(ResetException e) {
			System.out.println("Dijkstra Solver is Ended by Reset");
		}
	}

	private BNode chooseNode() {
		BNode res = null;
		long mn = Long.MAX_VALUE;
		for(Node n : this.graphElements.graph) {
			BNode bn = (BNode)n;
			if(bn.chk == false) {
				if(bn.subNumber < mn) {
					res = bn;
					mn = bn.subNumber;
				}
			}
		}
		return res;
	}
}
