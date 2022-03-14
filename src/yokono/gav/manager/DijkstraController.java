package yokono.gav.manager;

import javax.swing.JLabel;

import yokono.gav.algorithm.Dijkstra;
import yokono.gav.collbacks.EndSolveCollback;
import yokono.gav.datastructer.GraphElements;

public class DijkstraController extends BasicController {

	public DijkstraController(GraphElements g, long m, EndSolveCollback e, JLabel cpl) {
		super(g, m, e, cpl);
	}

	@Override
	protected void solverSet() {
		this.solver = new Dijkstra(this.graphElements);
	}

	@Override
	protected void printSolverEnd() {
		System.out.println("Dijkstra End");
	}

}
