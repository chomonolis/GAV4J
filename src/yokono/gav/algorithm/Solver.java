package yokono.gav.algorithm;

import java.awt.Color;
import java.util.Collections;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;
import yokono.gav.datastructer.Path;
import yokono.gav.manager.GraphUpdateCollback;

public abstract class Solver implements GraphUpdateCollback, Runnable {
	public static final long STEP_TIME_STOP = 1000000000000000L;
	public static final long STEP_TIME_MIN = 10L;
	public static final long STEP_TIME_MAX = 5000L;

	public static final String[] ALGORITHMS = {"BFS", "DFS", "Dijk", "DS"};

	protected GraphElements graphElements;
	protected boolean solverStart = false;
	protected boolean solverEnded = false;
	protected boolean oneStepFlag = false;
	protected boolean resetFlag = false;
	protected Thread thread;

	public abstract void solveGraphs();

	public Solver() {
		this.thread = new Thread(this);
		this.solverStart = false;
		this.solverEnded = false;
		this.oneStepFlag = false;
	}

	protected void stepStoper() throws ResetException {
		while(this.oneStepFlag == false && this.resetFlag == false) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.oneStepFlag = false;
		if(this.resetFlag == true) {
			this.resetFlag = false;
			throw new ResetException();
		}
	}

	public void threadStart() {
		this.thread.start();
	}

	public synchronized boolean reverseOneStepFlag() {
		this.oneStepFlag = !this.oneStepFlag;
		return this.oneStepFlag;
	}

	public boolean getSolverEnded() {
		return this.solverEnded;
	}


	protected void setPathColor(BNode from, BNode to, boolean f) {
		for(Path f2t : from.path) {
			if(f2t.to.equals(to)) {
				f2t.setColor(Color.red);
			}
		}
		if(f == false) return;
		for(Path t2f : to.path) {
			if(t2f.to.equals(from)) {
				t2f.setColor(Color.red);
			}
		}
	}

	protected void nodeBooleanf2t(BNode n) {
		if(n.chk == false) {
			n.chk = true;
		}
	}

	public void reset() {
		this.resetFlag = true;
	}



	public boolean getGraphs(GraphElements g) {
		if(g != null && g.graph != null && g.graph.size() != 0 && g.graph.get(0) instanceof BNode) {
			this.graphElements = g;
			for(Node n : this.graphElements.graph) {
				Collections.sort(n.path);
			}
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean callBack() {
		if(this.solverStart == false) this.solverStart = true;
		if(this.oneStepFlag == true) return this.oneStepFlag;
		return this.reverseOneStepFlag();
	}


	@Override
	public void run() {
		while(this.graphElements == null || this.solverStart == false) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.solveGraphs();
	}
}
