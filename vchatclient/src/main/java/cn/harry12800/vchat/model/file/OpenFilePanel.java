//package cn.harry12800.vchat.model.file;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.dnd.DnDConstants;
//import java.awt.dnd.DropTargetDragEvent;
//import java.awt.dnd.DropTargetDropEvent;
//import java.awt.dnd.DropTargetEvent;
//import java.awt.dnd.DropTargetListener;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//import java.io.File;
//import java.util.List;
//
//import javax.swing.BorderFactory;
//import javax.swing.JMenuItem;
//import javax.swing.JPopupMenu;
//import javax.swing.JScrollPane;
//import javax.swing.JTree;
//import javax.swing.ScrollPaneConstants;
//import javax.swing.border.Border;
//import javax.swing.event.TreeSelectionEvent;
//import javax.swing.event.TreeSelectionListener;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;
//import javax.swing.tree.TreePath;
//
//import cn.harry12800.j2se.dialog.InputMessageDialog;
//import cn.harry12800.j2se.dialog.InputMessageDialog.Callback;
//import cn.harry12800.j2se.style.MyScrollBarUI;
//import cn.harry12800.j2se.style.MyTreeUI;
//import cn.harry12800.j2se.style.UI;
//import cn.harry12800.j2se.utils.Clip;
//import cn.harry12800.lnk.core.Context;
//import cn.harry12800.lnk.core.CorePanel;
//import cn.harry12800.lnk.core.FunctionPanelConfig;
//import cn.harry12800.lnk.core.FunctionPanelModel;
//import cn.harry12800.tools.Lists;
//
//@FunctionPanelModel(configPath = "file", height = 400, width = 200, backgroundImage = "file_back.jpg", desc = "文件快捷方式\r\n打开工具", headerImage = "IOS.png")
//@FunctionPanelConfig(filename = "filelist.json")
//public class OpenFilePanel extends CorePanel<FileJsonConfig> implements DropTargetListener {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	public boolean drag = false;
//	DefaultMutableTreeNode root;
//	private DefaultTreeModel model;
//	private JTree catalogTree;
//	private List<OpenFileType> openFiles;
//	private Context context;
//	// 微软雅黑
//	public static Font BASIC_FONT = new Font("微软雅黑", Font.PLAIN, 12);
//	public static Font BASIC_FONT2 = new Font("微软雅黑", Font.TYPE1_FONT, 12);
//	// 楷体
//
//	public static Border GRAY_BORDER = BorderFactory.createLineBorder(Color.GRAY);
//	public static Border ORANGE_BORDER = BorderFactory.createLineBorder(Color.ORANGE);
//	public static Border LIGHT_GRAY_BORDER = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
//
//	public static void main(String[] args) throws Exception {
//		//		Main.main(args);
//	}
//
//	public OpenFilePanel(Context context) {
//		super(context);
//		this.context = context;
//		try {
//			this.openFiles = getConfigObject().getList();
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//		setSize(200, 400);
//		setBackground(UI.backColor);
//		setOpaque(false);
//		setLayout(new BorderLayout());
//		root = new DefaultMutableTreeNode();
//		model = new DefaultTreeModel(root);
//		for (final OpenFileType of : openFiles) {
//			FileNode node = new FileNode(this, of);
//			root.add(node);
//		}
//		catalogTree = new JTree(model);
//		catalogTree.setToggleClickCount(1);// 点击次数
//		catalogTree.setOpaque(false);
//		catalogTree.setRootVisible(false);// 隐藏根节点
//		catalogTree.setDragEnabled(true);
//		catalogTree.putClientProperty("JTree.lineStyle", "None");
//		catalogTree.setCellRenderer(new TreeNodeRenderer());
//		catalogTree.setUI(new MyTreeUI());
//		catalogTree.addTreeSelectionListener(new TreeSelectionListener() {
//			public void valueChanged(TreeSelectionEvent evt) {
//				// DefaultMutableTreeNode node = (DefaultMutableTreeNode)
//				// catalogTree
//				// .getLastSelectedPathComponent(); // 返回最后选中的结点
//				// if (node instanceof FileNode) {
//				// try {
//				// Clip.openFile(((FileNode)node).file.getPath());
//				// } catch (Exception e1) {
//				// MainFrame.alert(e1.getMessage());
//				// }
//				// }
//			}
//		});
//		catalogTree.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseExited(MouseEvent e) {
//				TreeNodeRenderer.mouseRow = -1;
//				catalogTree.repaint();
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
//					final TreePath path = catalogTree.getPathForLocation(e.getX(), e.getY());
//					if (null != path) {
//						// path中的node节点（path不为空，这里基本不会空）
//						final Object object = path.getLastPathComponent();
//						if (object instanceof FileNode) {
//							try {
//								Clip.openFile(((FileNode) object).file.getPath());
//							} catch (Exception e1) {
//								context.alert(e1.getMessage());
//							}
//						}
//					}
//				}
//				if (e.getButton() == MouseEvent.BUTTON3) {
//					final TreePath path = catalogTree.getPathForLocation(e.getX(), e.getY());
//					if (null != path) {
//						// path中的node节点（path不为空，这里基本不会空）
//						final Object object = path.getLastPathComponent();
//						if (object instanceof FileNode) {
//							JPopupMenu pm = new JPopupMenu();
//							pm.setBackground(Color.WHITE);
//							pm.setBorder(LIGHT_GRAY_BORDER);
//							JMenuItem mit1 = new JMenuItem("  重命名  ");
//							mit1.setBorderPainted(false);
//							JMenuItem mit0 = new JMenuItem("  删除  ");
//							mit0.setBorderPainted(false);
//							//							mit0.setOpaque(false);
//							mit0.setFont(BASIC_FONT);
//							//							mit1.setOpaque(false);
//							mit1.setFont(BASIC_FONT);
//							mit1.addActionListener(new ActionListener() {
//								public void actionPerformed(ActionEvent e) {
//									String nickName = ((FileNode) object).file.getName();
//									new InputMessageDialog(context.getFrame(), "重命名", nickName, new Callback() {
//										public void callback(String string) {
//											((FileNode) object).file.setName(string);
//											((FileNode) object).builder.text.setText(string);
//											saveConfigObject();
//											context.refresh(OpenFilePanel.this);
//										}
//									});
//								}
//							});
//							mit0.addActionListener(new ActionListener() {
//								public void actionPerformed(ActionEvent e) {
//									TreePath[] selectionPaths = catalogTree.getSelectionPaths();
//									/**
//									 * 是否删除选中部分。
//									 */
//									boolean mark = false;
//									for (TreePath path : selectionPaths) {
//										Object obj = path.getLastPathComponent();
//										if (object.equals(obj)) {
//											mark = true;
//											break;
//										}
//									}
//									if (mark) {
//										List<OpenFileType> list = Lists.newArrayList();
//										for (TreePath path : selectionPaths) {
//											Object obj = path.getLastPathComponent();
//											list.add(((FileNode) (obj)).file);
//											((FileNode) (object)).removeFromParent();
//										}
//										deleteOpenFile(list);
//									} else {
//										OpenFileType file = ((FileNode) object).file;
//										deleteOpenFile(file);
//										((FileNode) (object)).removeFromParent();
//										catalogTree.revalidate();
//									}
//								}
//
//							});
//							pm.add(mit1);
//							pm.add(mit0);
//							pm.show(catalogTree, e.getX(), e.getY());
//						}
//					}
//				}
//			}
//		});
//		catalogTree.addMouseMotionListener(new MouseMotionAdapter() {
//			@Override
//			public void mouseMoved(MouseEvent arg0) {
//				int x = (int) arg0.getPoint().getX();
//				int y = (int) arg0.getPoint().getY();
//				// TreePath path = catalogTree.getPathForLocation(x, y);
//				catalogTree.getComponentAt(x, y).repaint();
//				TreeNodeRenderer.mouseRow = catalogTree.getRowForLocation(x, y);
//				catalogTree.repaint();
//			}
//		});
//		catalogTree.setTransferHandler(new MyJTreeTransferHandler(this));
//		JScrollPane jScrollPane = new JScrollPane();
//		jScrollPane.setViewportView(catalogTree);
//		jScrollPane.setOpaque(false);
//		jScrollPane.getViewport().setOpaque(false);
//		// jScrollPane.getViewport().setBackground( UI.backColor);
//		// jScrollPane.setBackground(Style.backColor);
//		// jScrollPane.getVerticalScrollBar().setBackground(UI.backColor);
//		// jScrollPane.getVerticalScrollBar().setVisible(false);
//		// jScrollPane.setBackground(UI.backColor);
//		jScrollPane.getVerticalScrollBar().setBackground(UI.backColor);
//		jScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
//		// 屏蔽横向滚动条
//		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		add(jScrollPane, BorderLayout.CENTER);
//	}
//
//	private void deleteOpenFile(List<OpenFileType> file) {
//		System.out.println(file);
//		for (OpenFileType o : file) {
//			for (OpenFileType openFileType : openFiles) {
//				if (o.equals(openFileType)) {
//					openFiles.remove(openFileType);
//					break;
//				}
//			}
//		}
//		saveConfigObject();
//		refresh();
//	}
//
//	private void deleteOpenFile(OpenFileType file) {
//		for (OpenFileType openFileType : openFiles) {
//			if (file.equals(openFileType)) {
//				openFiles.remove(openFileType);
//				break;
//			}
//		}
//		saveConfigObject();
//		refresh();
//	}
//
//	// protected void paintComponent(Graphics g) {
//	// super.paintComponent(g);
//	// Graphics2D g2d = (Graphics2D) g.create();
//	// g2d.setColor(Style.backColor);
//	// g2d.fillRect(0, 0, getWidth(), getHeight());
//	// GradientPaint p2= new GradientPaint(0, 1, new Color(186, 131, 164, 200),
//	// 0, 20, new Color(255, 255, 255, 255));
//	// g2d.setPaint(p2);
//	//// g2d.drawRoundRect(1, 20, width, size*25+1, 5, 5);
//	////// g2d.setColor(Style.backColor);
//	//// g2d.fillRoundRect(1, 20, width, size*25+1, 5, 5);
//	//// g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
//	// BasicStroke.JOIN_ROUND)); // 设置新的画刷
//	// g2d.setFont(new Font("宋体", Font.PLAIN, 12));
//	// g2d.drawString("打开", 5, 15);
//	// g2d.dispose();
//	// }
//	@Override
//	public void drop(DropTargetDropEvent dtde) {
//		try {
//			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) // 如果拖入的文件格式受支持
//			{
//				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
//				// 接收拖拽来的数据
//				@SuppressWarnings("unchecked")
//				List<File> list = (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
//				addSave(list);
//			} else {
//				dtde.rejectDrop();// 否则拒绝拖拽来的数据
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	protected void addSave(List<File> list) {
//		boolean falg = false;
//		for (File file : list) {
//			boolean mark = true;
//			for (OpenFileType of : openFiles) {
//				if (file.getAbsolutePath().equals(new File(of.getPath()).getAbsolutePath())) {
//					mark = false;
//					break;
//				}
//			}
//			if (mark) {
//				falg = true;
//				OpenFileType e = new OpenFileType();
//				e.setName(file.getName());
//				e.setPath(file.getAbsolutePath());
//				openFiles.add(e);
//			}
//		}
//		if (falg) {
//			saveConfigObject();
//			context.refresh(this);
//		}
//	}
//
//	@Override
//	public void dragEnter(DropTargetDragEvent dtde) {
//	}
//
//	@Override
//	public void dragExit(DropTargetEvent dte) {
//	}
//
//	@Override
//	public void dragOver(DropTargetDragEvent dtde) {
//	}
//
//	@Override
//	public void dropActionChanged(DropTargetDragEvent dtde) {
//	}
//
//	public void drag(OpenFileType file, int index) {
//		for (OpenFileType f : openFiles) {
//			if (f.equals(file)) {
//				openFiles.remove(f);
//				break;
//			}
//		}
//		openFiles.add(index, file);
//		saveConfigObject();
//		context.refresh(this);
//	}
//
//	public void deleteOpenFile(int x) {
//		openFiles.remove(x);
//		saveConfigObject();
//	}
//
//	@Override
//	public void initLoadData() {
//		// TODO Auto-generated method stub
//
//	}
//}
