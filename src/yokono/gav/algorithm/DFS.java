package yokono.gav.algorithm;

import java.util.LinkedList;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Path;

public class DFS extends Solver {

	private LinkedList<BNode> stack = new LinkedList<BNode>();

	public DFS(GraphElements ge) {
		super.getGraphs(ge);
	}

	@Override
	public void solveGraphs() {
		try {
			BNode now = (BNode)this.graphElements.currentNode;
			if(now == null) return ;
			super.nodeBooleanf2t(now);
			this.stack.clear();
			this.stack.push(now);
			while(!this.stack.isEmpty()) {
				super.stepStoper();
				BNode n = this.stack.pop();
				this.graphElements.setCurrentNode(n);
				for(Path nx : n.path) {
					super.stepStoper();
					BNode bnx = (BNode)nx.to;
					this.graphElements.setSubCurrentNode(bnx);
					if(bnx.chk == true) continue;
					super.nodeBooleanf2t(bnx);
					super.setPathColor(n, (BNode)nx.to, true);
					this.stack.push(bnx);
				}
			}
			super.stepStoper();
			this.graphElements.setCurrentNode(null);
			this.solverEnded = true;
		} catch(ResetException e) {
			System.out.println("DFSSolver is Ended by Reset");
		}
	}

	public LinkedList<BNode> getDFSStack(){
		return this.stack;
	}

}
