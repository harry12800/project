package cn.harry12800.vchat.panels;

import javax.swing.JPanel;

/**
 * Created by harry12800 on 17-5-30.
 */
@SuppressWarnings("serial")
public class ParentAvailablePanel extends JPanel {
	private JPanel parent;

	public ParentAvailablePanel(JPanel parent) {
		this.parent = parent;
	}

	public JPanel getParentPanel() {
		return parent;
	}
}
