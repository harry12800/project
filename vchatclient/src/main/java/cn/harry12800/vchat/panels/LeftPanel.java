package cn.harry12800.vchat.panels;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.vchat.components.GBC;

/**
 * Created by harry12800 on 17-5-29.
 */
@SuppressWarnings("serial")
public class LeftPanel extends JPanel {
	private MyInfoPanel myInfoPanel;
	private SearchPanel searchPanel;
	private TabOperationPanel mainOperationPanel;
	private ListPanel listPanel;

	public LeftPanel() {

		initComponents();
		initView();
	}

	private void initComponents() {
		myInfoPanel = new MyInfoPanel(this);

		searchPanel = new SearchPanel(this);

		mainOperationPanel = new TabOperationPanel(this);
		// mainOperationPanel.setBackground(Color.blue);

		listPanel = new ListPanel(this);
		listPanel.setBackground(Colors.DARK);
	}

	private void initView() {
		this.setBackground(Colors.DARK);
		this.setLayout(new GridBagLayout());
		add(myInfoPanel, new GBC(0, 0).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(1, 7));
		add(searchPanel, new GBC(0, 1).setAnchor(GBC.CENTER).setFill(GBC.HORIZONTAL).setWeight(1, 1));
		add(mainOperationPanel, new GBC(0, 2).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(1, 1));
		add(listPanel, new GBC(0, 3).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setWeight(1, 60));

	}

	public ListPanel getListPanel() {
		return this.listPanel;
	}

}
