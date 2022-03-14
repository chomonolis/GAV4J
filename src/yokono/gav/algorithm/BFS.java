package yokono.gav.algorithm;

import java.util.LinkedList;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Path;

public class BFS extends Solver {

	private LinkedList<BNode> queue = new LinkedList<BNode>();

	public BFS(GraphElements g) {
		super.getGraphs(g);
	}

	@Override
	public void solveGraphs() {
		doBFS(this.graphElements);
	}

	protected void doBFS(GraphElements g) {
		try {
			BNode now = (BNode)g.currentNode;
			if(now == null) return ;
			super.nodeBooleanf2t(now);
			this.queue.clear();
			this.queue.offer(now);
			while(!this.queue.isEmpty()) {
				super.stepStoper();
				BNode n = this.queue.poll();
				g.setCurrentNode(n);
				for(Path nx : n.path) {
					super.stepStoper();
					BNode bnx = (BNode)nx.to;
					g.setSubCurrentNode(bnx);
					if(bnx.chk == true) continue;
					super.nodeBooleanf2t(bnx);
					super.setPathColor(n, (BNode)nx.to, true);
					this.queue.offer(bnx);
				}
			}
			super.stepStoper();
			g.setCurrentNode(null);
			this.solverEnded = true;
		} catch(ResetException e) {
			System.out.println("BFSSolver is Ended by Reset");
		}
	}

	public LinkedList<BNode> getBFSQueue(){
		return this.queue;
	}

}
