package yokono.gav.graphic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import yokono.gav.algorithm.Solver;

public class ConsolePanel extends JPanel implements ActionListener {
	CentralPanel centralPanel = null;
	JPanel leftPanel = new JPanel();
	JButton stopButton = new JButton("X");
	JButton speedUpButton = new JButton(">>");
	JButton speedDownButton = new JButton(">");
	JButton oneStepButton = new JButton(">|");
	JButton startButton = new JButton("▶");
	JLabel speedLabel = new JLabel(">>>");

	JPanel rightPanel = new JPanel();
	JCheckBox isOneWayArrow = new JCheckBox("片方向", false);
	JCheckBox isHaveHevy = new JCheckBox("重さ付", false);
	JLabel hevyNum = new JLabel("xxx");
	JLabel hevyChk = new JLabel("xxx");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox algorithmCB = new JComboBox(Solver.ALGORITHMS);

	Component[] componentsL = {this.stopButton, this.speedDownButton, this.oneStepButton, this.speedUpButton, this.startButton, this.speedLabel};
	Component[] componentsR = {this.isOneWayArrow, this.isHaveHevy, this.hevyNum, this.hevyChk, this.algorithmCB};


	public ConsolePanel(int x, int y) {
		this.setPreferredSize(new Dimension(x, y));
		this.setLayout(new GridLayout(1, 2));

		this.leftPanel = new JPanel();
		this.leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.leftPanel.setBackground(Color.gray);
		for(Component c : this.componentsL) {
			this.leftPanel.add(c);
			if(c instanceof JButton) {
				JButton jb = (JButton) c;
				jb.addActionListener(this);
			}
		}
		this.startButton.setEnabled(false);
		this.speedLabel.setForeground(Color.white);
		this.add(this.leftPanel);

		this.rightPanel = new JPanel();
		this.rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.rightPanel.setBackground(Color.gray);
		for(Component c : this.componentsR) {
			this.rightPanel.add(c);
		}
		this.isHaveHevy.addActionListener(this);
		this.isOneWayArrow.addActionListener(this);
		this.algorithmCB.addActionListener(this);
		this.hevyChk.setVisible(false);
		this.hevyChk.setForeground(Color.white);
		this.hevyNum.setVisible(false);
		this.hevyNum.setForeground(Color.white);
		this.add(this.rightPanel);

		this.setVisible(true);
	}

	public void setCentralPanel(CentralPanel cp) {
		this.centralPanel = cp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.centralPanel.C2CCollback(e);
	}
}
