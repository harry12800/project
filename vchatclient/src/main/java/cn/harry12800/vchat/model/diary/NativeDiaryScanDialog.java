package cn.harry12800.vchat.model.diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.markdown4j.Markdown4jProcessor;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import cn.harry12800.j2se.action.DragListener;
import cn.harry12800.j2se.tab.Tab;
import cn.harry12800.j2se.tab.TabRootPane;
import cn.harry12800.tools.Lists;
import cn.harry12800.vchat.entity.Diary;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.panels.DiaryCatalogPanel;

@SuppressWarnings("serial")
public class NativeDiaryScanDialog extends JDialog {
	public static NativeDiaryScanDialog instance;

	public NativeDiaryScanDialog(Diary[] aritcles) {
		super(MainFrame.getContext());
		instance = this;
		setType(JFrame.Type.UTILITY);
		setSize(500, 600);
		// setModal(true);
		TabRootPane tabRootPane = new TabRootPane();
		tabRootPane.add(new OperatePanel(this), BorderLayout.SOUTH);
		File file = new File(DiaryCatalogPanel.getContext().dirPath);
		List<Tab> tabs = Lists.newArrayList();
		for (Diary a : aritcles) {
			Tab tab = new Tab(a.getTitle());
			JPanel webBrowserPanel = new JPanel(new BorderLayout());
			webBrowserPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
			final JWebBrowser webBrowser = new JWebBrowser();
			webBrowser.setBarsVisible(false);
			webBrowser.setButtonBarVisible(false);
			webBrowser.setMenuBarVisible(false);
			webBrowser.setSize(480, 600);
			webBrowser.addWebBrowserListener(new WebBrowserListener() {

				@Override
				public void windowWillOpen(WebBrowserWindowWillOpenEvent arg0) {
					arg0.setNewWebBrowser(webBrowser);
					webBrowser.setBarsVisible(false);
					webBrowser.setButtonBarVisible(false);
					webBrowser.setMenuBarVisible(false);
				}

				@Override
				public void windowOpening(WebBrowserWindowOpeningEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowClosing(WebBrowserEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void titleChanged(WebBrowserEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void statusChanged(WebBrowserEvent arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void locationChanging(WebBrowserNavigationEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void locationChanged(WebBrowserNavigationEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void locationChangeCanceled(WebBrowserNavigationEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void loadingProgressChanged(WebBrowserEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void commandReceived(WebBrowserCommandEvent arg0) {

				}
			});
			String html;
			try {
				html = new Markdown4jProcessor().process(a.getContent());
				// System.out.println(file.getAbsolutePath());
				String string = "file:///" + file.getAbsolutePath() + File.separator + "image";
				string = string.replaceAll("\\\\", "/");
				html = html.replaceAll("diaryImage", string);
				html = "<html><head><style type=\"text/css\">body {font-size:14px !important;font-family: \"微软雅黑\";color:#FFFFFF;}</style></head><body style='background:#5B507A'>"
						+ html + "</body></html>";
				webBrowser.setHTMLContent(html);
			} catch (IOException e) {
				e.printStackTrace();
			}
			webBrowserPanel.setBackground(Color.RED);
			webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
			tab.setContentPane(webBrowserPanel);
			webBrowserPanel.requestFocus();
			tabs.add(tab);
		}

		Collections.reverse(tabs);
		tabRootPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println(e);
				if (e.getKeyCode() == 27) {
					NativeDiaryScanDialog.this.dispose();
				}
			}
		});
		tabRootPane.addTab(tabs.toArray(new Tab[] {}));
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
