package yokono.gav.manager;

import yokono.gav.collbacks.SolveEndAndCentralPanelCollback;

public interface Controllable extends Runnable, SolveEndAndCentralPanelCollback {
	void solveStart();
	void solveEnded();
}
