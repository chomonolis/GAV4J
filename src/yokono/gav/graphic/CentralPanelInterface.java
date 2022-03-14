package yokono.gav.graphic;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import yokono.gav.collbacks.ConsoleToCentralCollback;
import yokono.gav.collbacks.EndSolveCollback;
import yokono.gav.collbacks.setHevyLabelCollback;

public interface CentralPanelInterface extends ConsoleToCentralCollback, Runnable , MouseListener, MouseMotionListener, KeyListener, EndSolveCollback, setHevyLabelCollback{
}
