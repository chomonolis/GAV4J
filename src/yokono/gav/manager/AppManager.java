package yokono.gav.manager;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import yokono.gav.graphic.BasicFrame;
import yokono.gav.graphic.CentralPanel;
import yokono.gav.graphic.ConsolePanel;

public class AppManager {

	public AppManager() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BasicFrame frame = new BasicFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout());
				ConsolePanel consolePanel = new ConsolePanel(frame.getWidth(), 80);
				CentralPanel centralPanel = new CentralPanel(frame.getWidth(), frame.getHeight()-80, consolePanel);
				frame.getContentPane().add("North", consolePanel);
				frame.getContentPane().add("South", centralPanel);
				frame.setVisible(true);
			}
		});
//		this.mode = new BFSMenu(g, m);
	}


}
