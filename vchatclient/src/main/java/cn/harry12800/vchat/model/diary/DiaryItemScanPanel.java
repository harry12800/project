package cn.harry12800.vchat.model.diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JEditorPane;
import javax.swing.JLayeredPane;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import cn.harry12800.j2se.style.UI;
import cn.harry12800.vchat.entity.Diary;

public class DiaryItemScanPanel extends JLayeredPane implements HyperlinkListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JEditorPane textPane = new JEditorPane();

	public DiaryItemScanPanel(Diary aritcle) {
		setLayout(new BorderLayout());

		textPane.setContentType("text/html; charset=utf-8");
		textPane.setBackground(UI.backColor);
		textPane.setSelectionColor(UI.foreColor);
		textPane.setSelectedTextColor(Color.WHITE);
		textPane.setBorder(new EmptyBorder(0, 15, 0, 15));
		textPane.setCaretColor(new Color(51, 136, 255));
		textPane.setForeground(Color.WHITE);
		// SlidePanel sp = new SlidePanel(textPane, 550);
		// JScrollPane jScrollPane = new JScrollPane(textPane);
		// jScrollPane.getHorizontalScrollBar().setUI(new MultiScrollBarUI());
		final JViewport port = new JViewport();
		port.setView(textPane);
		// port.setViewSize(new Dimension(500, 10000));
		add(port, BorderLayout.CENTER);
		textPane.setEditable(false);
		textPane.setAutoscrolls(true);
		textPane.addHyperlinkListener(this);
		textPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyevent) {
				if (keyevent.getKeyCode() == 27) {
					DiaryScanDialog.instance.dispose();
				}
			}
		});

		port.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// System.out.println(textPane.getBounds());
				int left = textPane.getLocation().x;
				int top = textPane.getLocation().y;
				// System.out.println( e.getWheelRotation());
				// Rectangle aRect = new Rectangle(left, top +e.getWheelRotation(),
				// textPane.getBounds().width, textPane.getBounds().height);
				// textPane.scrollRectToVisible(aRect );
				// System.out.println(rootPane);
				// System.out.println(componentCount);
				// port.setViewPosition(new Point(0, 50));
				if (e.getWheelRotation() > 0)
					for (int i = 0; i < 8; i++) {
						top = textPane.getLocation().y;
						if (getHeight() - top < textPane.getBounds().height)
							textPane.setLocation(left, top - e.getWheelRotation());
					}
				if (e.getWheelRotation() < 0)
					for (int i = 0; i < 8; i++) {
						top = textPane.getLocation().y;
						if (top <= 0)
							textPane.setLocation(left, top - e.getWheelRotation());
					}

			}
		});
		String html;
//		try {
//			html = new Markdown4jProcessor().process(aritcle.getContent());
//			System.out.println(html);
//			html = "<head><style type=\"text/css\">body {font-size:14px !important;font-family: \"微软雅黑\";color:#FFFFFF;}</style></head><body>"
//					+ html + "</body>";
//			// System.out.println(html);
//			textPane.setText(html);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// for (String string : rowByFile) {
		// if (string.startsWith("##")) {
		// textPane.setText("<p>aaaaaaaaaaaaaaaaa</p>");
		// insertTitle(string.replaceAll("#", ""));
		// } else {
		// InsertContent(string);
		// System.out.println(string);
		// }
		// }
	}

	@SuppressWarnings("unused")
	private void InsertContent(String string) {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, Color.white);
		StyleConstants.setBold(attrSet, true);
		StyleConstants.setFontSize(attrSet, 12);
		StyleConstants.setFontFamily(attrSet, "宋体");

		Document doc = textPane.getDocument();
		try {
			doc.insertString(doc.getLength(), string + "\r\n", attrSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		textPane.setCaretPosition(doc.getLength() - 1);
		textPane.setEditable(true);
		textPane.setEnabled(true);
		System.out.println(textPane.getText());
	}

	@SuppressWarnings("unused")
	private void insertTitle(String string) {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, Color.RED);
		StyleConstants.setBold(attrSet, true);
		StyleConstants.setFontSize(attrSet, 12);
		StyleConstants.setFontFamily(attrSet, "宋体");
		Document doc = textPane.getDocument();
		try {
			doc.insertString(doc.getLength(), string + "</br>", attrSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		textPane.setCaretPosition(doc.getLength() - 1);
		textPane.setEditable(true);
		textPane.setEnabled(true);
		System.out.println(textPane.getText());
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			JEditorPane pane = (JEditorPane) e.getSource();
			if (e instanceof HTMLFrameHyperlinkEvent) {
				HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
				HTMLDocument doc = (HTMLDocument) pane.getDocument();
				System.out.println(evt);
				doc.processHTMLFrameHyperlinkEvent(evt);
			} else {
				try {
					System.out.println(e.getURL());
					// URI uri = new URI(file.getPath().replace("\r\n",""));
					java.awt.Desktop.getDesktop().browse(e.getURL().toURI());
					// UIUtils.setPreferredLookAndFeel();
					// NativeInterface.open();
					// JPanel webBrowserPanel = new JPanel(new BorderLayout());
					// webBrowserPanel.setBorder(new EmptyBorder(0,0,0,0));
					// final JWebBrowser webBrowser = new JWebBrowser();
					// webBrowser.setBarsVisible(false);
					// webBrowser.setButtonBarVisible(false);
					// webBrowser.setMenuBarVisible(false);
					// webBrowser.navigate(e.getURL().toString());
					// webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
					// JFrame frame = new JFrame("Ives");
					// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					// frame.getContentPane().add(webBrowserPanel, BorderLayout.CENTER);
					// frame.setSize(800, 600);
					// frame.setLocationByPlatform(true);
					// frame.setVisible(true);
					// pane.setPage(e.getURL());
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
}
