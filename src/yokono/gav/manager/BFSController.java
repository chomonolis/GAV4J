package yokono.gav.manager;

import java.util.LinkedList;

import javax.swing.JLabel;

import yokono.gav.algorithm.BFS;
import yokono.gav.collbacks.EndSolveCollback;
import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;

public class BFSController extends BasicController{

	public BFSController(GraphElements g, long m, EndSolveCollback e, JLabel cls) {
		super(g, m, e, cls);
	}

	@Override
	protected void solverSet() {
		this.solver = new BFS(this.graphElements);
	}

	@Override
	protected void printSolverEnd() {
		System.out.println("BFSEnd");
	}

	public LinkedList<BNode> getBFSQueue(){
		if(this.solver instanceof BFS) {
			BFS b = (BFS) this.solver;
			return b.getBFSQueue();
		}else return null;
	}
}
