package yokono.gav.manager;

import java.util.LinkedList;

import javax.swing.JLabel;

import yokono.gav.algorithm.DFS;
import yokono.gav.collbacks.EndSolveCollback;
import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;

public class DFSController extends BasicController {

	public DFSController(GraphElements g, long m, EndSolveCollback e, JLabel cpl) {
		super(g, m, e, cpl);
	}

	@Override
	protected void solverSet() {
		this.solver = new DFS(this.graphElements);
	}

	@Override
	protected void printSolverEnd() {
		System.out.println("DFSEnd");
	}

	public LinkedList<BNode> getDFSStack(){
		if(this.solver instanceof DFS) {
			DFS b = (DFS) this.solver;
			return b.getDFSStack();
		}else return null;
	}

}
