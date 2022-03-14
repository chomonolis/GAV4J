package yokono.gav.graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import yokono.gav.algorithm.Solver;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.NewHv;
import yokono.gav.datastructer.NewPath;
import yokono.gav.datastructer.Node;
import yokono.gav.manager.BFSController;
import yokono.gav.manager.BasicController;
import yokono.gav.manager.DFSController;
import yokono.gav.manager.DijkstraController;
import yokono.gav.manager.DisjointSetController;

public class CentralPanel extends JPanel implements CentralPanelInterface{
	public static final int INPUT_GRAPH_MENU = 101010001;
	public static final int BFS_MENU = CentralPanel.INPUT_GRAPH_MENU + 1;
	public static final int DFS_MENU = CentralPanel.BFS_MENU + 1;
	public static final int DIJKSTRA_MENU = CentralPanel.DFS_MENU+1;
	public static final int DISJOINTSET_MENU = CentralPanel.DIJKSTRA_MENU+1;
	public static final int[] MENU_FLAG_ARRAY = {CentralPanel.INPUT_GRAPH_MENU, CentralPanel.BFS_MENU, CentralPanel.DFS_MENU, CentralPanel.DIJKSTRA_MENU, CentralPanel.DISJOINTSET_MENU};

	private Thread thread;
	private ConsolePanel consolPanel;
	private long[] solveSpeedArray = {Solver.STEP_TIME_MIN, 100, 250, 500, 1000, 2000, Solver.STEP_TIME_MAX};

	private GraphElements graphElements;
	private NewPath newPath = new NewPath();
	private int menuFlag = CentralPanel.INPUT_GRAPH_MENU;
	private long speed = this.solveSpeedArray[4];
	private int speedIndex = 4;
	private NewHv newHv = new NewHv();
	private BasicController basicController;

	public CentralPanel(int x, int y, ConsolePanel cp) {
		this.consolPanel = cp;
		cp.setCentralPanel(this);
		this.setPreferredSize(new Dimension(x, y));
		this.setLayout(null);
		this.graphElements = new GraphElements(0, null);
//		this.graphElements = TestGraphInput.TestInput();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.thread = new Thread(this);
		this.thread.start();
		this.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		if(this.menuFlag == CentralPanel.INPUT_GRAPH_MENU) SFInputGraphMenu.MenuPaint(g, this.getWidth(), this.getHeight(), this.graphElements, this.newPath, this.consolPanel.isHaveHevy.isSelected());
		else if(this.menuFlag == CentralPanel.BFS_MENU) {
			if(this.basicController instanceof BFSController) {
				BFSController b = (BFSController) this.basicController;
				SFBFSMenu.MenuPaint(g, this.getWidth(), this.getHeight(), this.graphElements, b.getBFSQueue());
			}
		}else if(this.menuFlag == CentralPanel.DFS_MENU) {
			if(this.basicController instanceof DFSController) {
				DFSController d = (DFSController) this.basicController;
				SFDFSMenu.MenuPaint(g, this.getWidth(), this.getHeight(), this.graphElements, d.getDFSStack());
			}
		}else if(this.menuFlag == CentralPanel.DIJKSTRA_MENU) {
			SFDijkstraMenu.MenuPaint(g, this.getWidth(), this.getHeight(), this.graphElements, this.menuFlag);
		}else if(this.menuFlag == CentralPanel.DISJOINTSET_MENU){
			SFDisjointSetMenu.MenuPaint(g, this.getWidth(), this.getHeight(), this.graphElements);
		}else {
			System.err.println("不明なメニューです");
		}
	}

	@Override
	public void run() {
		while(true) {
			this.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(this.menuFlag) {
		case CentralPanel.INPUT_GRAPH_MENU:
			SFInputGraphMenu.mousePressed(e, this.graphElements.graph, this.newPath);
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(this.menuFlag) {
		case CentralPanel.INPUT_GRAPH_MENU:
			SFInputGraphMenu.mouseReleased(e, this.consolPanel, this.graphElements, this.getWidth(), this.getHeight(), this.newPath, this.consolPanel.isOneWayArrow.isSelected(), this.consolPanel.isHaveHevy.isSelected(), this.newHv, this);
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch(this.menuFlag) {
		case CentralPanel.INPUT_GRAPH_MENU:
			InputGraphMenuMouseListener.mouseDragged(e, this.newPath);
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void C2CCollback(ActionEvent e) {
		this.requestFocusInWindow();
		if(e.getSource().equals(this.consolPanel.startButton) == true) {
			if(this.menuFlag == CentralPanel.INPUT_GRAPH_MENU && this.graphElements.currentNode != null) {
				String alg = (String)this.consolPanel.algorithmCB.getSelectedItem();
				boolean chkst = false;
				if(alg.equals("BFS") == true) {
					this.changeMenu(CentralPanel.BFS_MENU);
					this.basicController = new BFSController(this.graphElements, this.speed, this, this.consolPanel.speedLabel);
					chkst = true;
				}else if(alg.equals("DFS") == true) {
					this.changeMenu(CentralPanel.DFS_MENU);
					this.basicController = new DFSController(this.graphElements, this.speed, this, this.consolPanel.speedLabel);
					chkst = true;
				}else if(alg.equals("Dijk") == true) {
					this.changeMenu(CentralPanel.DIJKSTRA_MENU);
					this.basicController = new DijkstraController(this.graphElements, this.speed, this, this.consolPanel.speedLabel);
					chkst = true;
				}else if(alg.equals("DS") == true) {
					this.changeMenu(CentralPanel.DISJOINTSET_MENU);
					this.basicController = new DisjointSetController(this.graphElements, this.speed, this, this.consolPanel.speedLabel);
					chkst = true;
				}
				if(chkst == true) this.basicController.threadStart();
			}else{
				boolean f = false;
				for(int n : CentralPanel.MENU_FLAG_ARRAY) {
					if(n == CentralPanel.INPUT_GRAPH_MENU) continue;
					if(n == this.menuFlag) f = true;
				}
				if(f == true) {
					if(this.basicController != null) {
						this.consolPanel.startButton.setText("▶");
						if(this.basicController.getSolveFInished() == true) {
							this.basicController.clearSolve();
							this.consolPanel.speedLabel.setText(this.setSpeedText());
						}else if(this.basicController.getStopFlag() == false) {
							if(this.basicController != null) {
								this.basicController.changeStepTime(yokono.gav.algorithm.Solver.STEP_TIME_STOP);
								this.consolPanel.startButton.setText("■");
							}
						}else{
							if(this.basicController != null) {
								this.basicController.changeStepTime(this.speed);
							}
						}
					}
				}
			}
		}else if(e.getSource().equals(this.consolPanel.speedUpButton)) {
			if(this.speedIndex > 0) this.speedIndex--;
			this.speed = this.solveSpeedArray[this.speedIndex];
			this.consolPanel.speedLabel.setText(this.setSpeedText());
			if(this.basicController != null && this.basicController.getStopFlag() == false) this.basicController.changeStepTime(this.speed);

		}else if(e.getSource().equals(this.consolPanel.speedDownButton)) {
			if(this.speedIndex < this.solveSpeedArray.length-1) this.speedIndex++;
			this.speed = this.solveSpeedArray[this.speedIndex];
			this.consolPanel.speedLabel.setText(this.setSpeedText());
			if(this.basicController != null && this.basicController.getStopFlag() == false) this.basicController.changeStepTime(this.speed);

		}else if(e.getSource().equals(this.consolPanel.stopButton)) {
			Node.reset();
			if(this.basicController != null) this.basicController.reset();
			this.graphElements = new GraphElements(0, null);
			this.newHv.chkHv = false;
			this.newHv.nxHv = 0L;

		}else if(e.getSource().equals(this.consolPanel.oneStepButton)) {
			if(this.menuFlag != CentralPanel.INPUT_GRAPH_MENU && this.basicController != null && this.basicController.getSolveFInished() == false) {
				this.basicController.oneStepDo();
			}

		}else if(e.getSource().equals(this.consolPanel.isHaveHevy)) {
			boolean isSelected = this.consolPanel.isHaveHevy.isSelected();
			this.consolPanel.hevyChk.setVisible(isSelected);
			this.consolPanel.hevyNum.setVisible(isSelected);
			if(isSelected == true) {
				this.setHevyLabel();
			}else {
				this.newHv.nxHv = 0L;
				this.newHv.chkHv = false;
			}
		}
	}

	private String setSpeedText() {
		String res = "";
		for(int i = this.solveSpeedArray.length; i > this.speedIndex; i--) {
			res += ">";
		}
		return res;
	}

	@Override
	public void setHevyLabel() {
		this.consolPanel.hevyNum.setText(Long.toString(this.newHv.nxHv));
		String s = (this.newHv.chkHv == true) ? "○" : "×";
		this.consolPanel.hevyChk.setText(s);
	}

	@Override
	public void changeMenu(int menunum) {
		boolean f = false;
		for(int i : CentralPanel.MENU_FLAG_ARRAY) {
			if(i == menunum) {
				this.menuFlag = menunum;
				f = true;
			}
		}
		if(f == false) {
			this.menuFlag = CentralPanel.INPUT_GRAPH_MENU;
			System.err.println("メニュー遷移エラー");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(this.menuFlag != INPUT_GRAPH_MENU) return ;
		int n = e.getKeyCode();
		switch(n) {
		case KeyEvent.VK_ENTER:
			this.newHv.chkHv = true;
			break;

		case KeyEvent.VK_BACK_SPACE:
			this.newHv.chkHv = false;
			this.newHv.nxHv = 0L;
			break;

		default:
			if(KeyEvent.VK_0 <= n && n <= KeyEvent.VK_9) {
				n -= KeyEvent.VK_0;
				this.newHv.nxHv *= 10;
				this.newHv.nxHv += n;
				this.newHv.chkHv = false;
			}
			break;
		}
		this.setHevyLabel();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
