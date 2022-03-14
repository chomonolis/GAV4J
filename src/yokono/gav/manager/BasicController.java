package yokono.gav.manager;

import javax.swing.JLabel;

import yokono.gav.algorithm.Solver;
import yokono.gav.collbacks.EndSolveCollback;
import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;
import yokono.gav.graphic.CentralPanel;

public abstract class BasicController implements Controllable {
	protected long stepTime = 10;
	protected GraphElements graphElements;
	protected Thread thread;
	protected EndSolveCollback esc;
	protected boolean stopFlag = false, resetFlag = false, oneStepFlag = false;
	protected Solver solver;
	protected boolean solveFinished = false;
	protected JLabel consolPanelLabel;

	protected abstract void solverSet();
	protected abstract void printSolverEnd();

	public BasicController(GraphElements g, long m, EndSolveCollback e, JLabel cpl) {
		this.graphElements = g;
		this.esc = e;
		this.consolPanelLabel = cpl;
		this.changeStepTime(m);
		this.solverSet();
		this.thread = new Thread(this);
	}

	public void changeStepTime(long t) {
		if(t == yokono.gav.algorithm.Solver.STEP_TIME_STOP) {
			this.stopFlag = true;
			return ;
		}
		this.stopFlag = false;
		if(t < yokono.gav.algorithm.Solver.STEP_TIME_MIN) t = yokono.gav.algorithm.Solver.STEP_TIME_MIN;
		if(t > yokono.gav.algorithm.Solver.STEP_TIME_MAX) t = yokono.gav.algorithm.Solver.STEP_TIME_MAX;
		this.stepTime = t;
	}


	@Override
	public void solveStart() {
		long mn = yokono.gav.algorithm.Solver.STEP_TIME_MIN;
		while(this.solver.getSolverEnded() == false && this.solveFinished == false) {
			for(long i = 0; i < this.stepTime/mn; i++) {
				try {
					Thread.sleep(mn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(this.oneStepFlag == true) break;
			}
			if(this.stopFlag == false || this.oneStepFlag == true) {
				this.solver.callBack();
				this.oneStepFlag = false;
			}
		}
		if(this.resetFlag == false) {
			printSolverEnd();
			this.consolPanelLabel.setText("←Click");
			this.solveFinished = true;
		}else {
			solveEnded();
		}
	}

	@Override
	public void solveEnded() {
		if(this.resetFlag == false) {
			this.graphElements.doubleNodeIndex = 0;
			for(Node n : this.graphElements.graph) {
				if(n instanceof BNode) {
					BNode bn = (BNode)n;
					bn.resetNode();
				}
			}
		}
		this.resetFlag = false;
		this.esc.changeMenu(CentralPanel.INPUT_GRAPH_MENU);
	}

	@Override
	public void run() {
		this.solver.threadStart();
		this.solveStart();
	}

	public void threadStart() {
		this.thread.start();
	}

	public boolean getStopFlag() {
		return this.stopFlag;
	}

	public boolean getSolveFInished() {
		return this.solveFinished;
	}

	@Override
	public void clearSolve() {
		if(this.solveFinished == true) {
			this.solveFinished = false;
			this.solveEnded();
		}
	}

	public void reset(){
		this.solveFinished = true;
		this.resetFlag = true;
		this.solver.reset();
	}

	public void oneStepDo() {
		this.oneStepFlag = true;
	}
}
