package cn.harry12800.vchat.model.diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.tab.Tab;
import cn.harry12800.j2se.tab.TabRootPane;
import cn.harry12800.tools.Lists;
import cn.harry12800.vchat.entity.Diary;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.panels.DiaryPanel;

@SuppressWarnings("serial")
public class DiaryScanDialog extends JDialog {
	private static Logger LOG = LoggerFactory.getLogger(DiaryScanDialog.class);
	public static DiaryScanDialog instance;

	public DiaryScanDialog(DiaryPanel diaryPanel, Diary[] aritcles, int currIndex) {
		super(MainFrame.getContext());
		instance = this;
		setType(JFrame.Type.UTILITY);
		setSize(500, 600);

		// setModal(true);
		TabRootPane tabRootPane = new TabRootPane();
		tabRootPane.add(new OperatePanel(this), BorderLayout.SOUTH);
		List<Tab> tabs = Lists.newArrayList();
		for (Diary a : aritcles) {
			Tab tab = new Tab(a.getTitle());
			DiaryItemScanPanel item = new DiaryItemScanPanel(a);
			tab.setContentPane(item);
			item.requestFocus();
			tabs.add(tab);
		}
		Tab tab = tabs.get(currIndex);
		Collections.reverse(tabs);
		tabRootPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				LOG.info(e + "");
				if (e.getKeyCode() == 27) {
					DiaryScanDialog.this.dispose();
				}
			}
		});

		tabRootPane.addTab(tabs.toArray(new Tab[] {}));
		tabRootPane.showTab(tab);
		JPanel jpeg = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			}
		};
		jpeg.setBorder(new EmptyBorder(1, 1, 1, 1));
		jpeg.setLayout(new BorderLayout());
		jpeg.add(tabRootPane, BorderLayout.CENTER);
		setContentPane(jpeg);
		setAlwaysOnTop(true);
		setUndecorated(true);
		new DragListener(this);
	}

	public void setCenterScreen() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = (int) d.getWidth();
		int h = (int) d.getHeight();
		this.setLocation((w - this.getWidth()) / 2, (h - this.getHeight()) / 2);
	}
}
