package yokono.gav.manager;

import javax.swing.JLabel;

import yokono.gav.algorithm.DisjointSet;
import yokono.gav.collbacks.EndSolveCollback;
import yokono.gav.datastructer.GraphElements;

public class DisjointSetController extends BasicController {

	public DisjointSetController(GraphElements g, long m, EndSolveCollback e, JLabel cpl) {
		super(g, m, e, cpl);
	}

	@Override
	protected void solverSet() {
		this.solver = new DisjointSet(this.graphElements);
	}

	@Override
	protected void printSolverEnd() {
		System.out.println("Disjoint Set End");
	}

}
