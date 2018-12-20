package cn.harry12800.vchat.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import cn.harry12800.j2se.component.rc.RCMenuItemUI;
import cn.harry12800.j2se.dialog.InputMessageDialog;
import cn.harry12800.j2se.dialog.InputMessageDialog.Callback;
import cn.harry12800.j2se.dialog.YesNoDialog;
import cn.harry12800.j2se.style.UI;
import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.j2se.utils.Clip;
import cn.harry12800.j2se.utils.HttpUtil;
import cn.harry12800.j2se.utils.JsonUtils;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Lists;
import cn.harry12800.tools.StringUtils;
import cn.harry12800.vchat.app.App;
import cn.harry12800.vchat.app.Launcher;
import cn.harry12800.vchat.app.config.Contants;
import cn.harry12800.vchat.entity.Diary;
import cn.harry12800.vchat.entity.DiaryCatalog;
import cn.harry12800.vchat.frames.MainFrame;
import cn.harry12800.vchat.model.diary.AricleNode;
import cn.harry12800.vchat.model.diary.CategoryNode;
import cn.harry12800.vchat.model.diary.MyJTreeTransferHandler;
import cn.harry12800.vchat.model.diary.MyTreeUI;
import cn.harry12800.vchat.model.diary.TreeNodeRenderer;

/**
 * 日志的目录面板
 * 
 * @author Yuexin
 * 
 */
public class DiaryCatalogPanel extends JScrollPane {

	/**
	 * 
	 */
	public static DiaryCatalogPanel context;
	public final static String dirName = "diary";
	public String dirPath = "";
	private static final long serialVersionUID = 1L;
	FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				char charAt = file.getName().charAt(0);
				boolean mark = true;
				if (charAt <= 'z' && charAt >= 'a') {
					mark = false;
				}
				return mark;
			}
			return file.getName().endsWith(".properties");
		}
	};
	JTree catalogTree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode currNode;

	public void selectDiary(File f) {
		if (f.isFile()) {
			Diary a;
			try {
				a = JsonUtils.string2Json(f, Diary.class);
				DiaryPanel.getContext().areaTextPanel.setText(a.getContent());
				DiaryPanel.getContext().searchInputText.setText(a.getTitle());
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.currPath = f.getAbsolutePath();
		}
	}

	public static DiaryCatalogPanel getContext() {
		return context;
	}

	public DiaryCatalogPanel(JPanel parent) {
		context = this;
		String homePath = App.basePath;
		dirPath = homePath + File.separator + "data" + File.separator + dirName + File.separator + Launcher.currentUser.getUserId();
		if (!new File(dirPath).exists()) {
			new File(dirPath).mkdirs();
		}
		root = new DefaultMutableTreeNode();
		model = new DefaultTreeModel(root);
		setOpaque(false);
		loadNode();
		catalogTree = new JTree(model);
		catalogTree.setToggleClickCount(1);// 点击次数
		catalogTree.setOpaque(false);
		catalogTree.setBackground(Color.YELLOW);
		catalogTree.setRootVisible(false);// 隐藏根节点
		catalogTree.setDragEnabled(true);
		catalogTree.putClientProperty("JTree.lineStyle", "None");
		catalogTree.setCellRenderer(new TreeNodeRenderer());
		catalogTree.setUI(new MyTreeUI());
		catalogTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) catalogTree.getLastSelectedPathComponent(); // 返回最后选中的结点
				if (node instanceof AricleNode) {
					RightPanel.getContext().showPanel(RightPanel.DIARY);
					TitlePanel.getContext().updateAppTitle("日记");
					selectDiary(((AricleNode) node).getFile());
					setCurrTree(catalogTree);
					setCurrNode(node);
					// setCurrFile(((AricleNode) node).getFile());
				}
				if (node instanceof CategoryNode) {

				}
			}
		});
		catalogTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				TreeNodeRenderer.mouseRow = -1;
				catalogTree.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					final TreePath path = catalogTree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						// path中的node节点（path不为空，这里基本不会空）
						final Object object = path.getLastPathComponent();
						if (object instanceof CategoryNode) {
							JPopupMenu pm = new JPopupMenu();
							pm.setBorder(new LineBorder(Colors.SCROLL_BAR_TRACK_LIGHT));
							pm.setBackground(Colors.FONT_WHITE);
							pm.setBorder(LIGHT_GRAY_BORDER);
							// pm.setBorderPainted(false);
							JMenuItem mit0 = new JMenuItem("打开目录");
							JMenuItem mit1 = new JMenuItem("更换名称");
							JMenuItem mit2 = new JMenuItem("添加文章");
							JMenuItem mit3 = new JMenuItem("删除分组");
							JMenuItem mit4 = new JMenuItem("导出数据");
							JMenuItem mit5 = new JMenuItem("建子目录");
//							mit3.setBackground(Colors.DARK);
							mit0.setUI(new RCMenuItemUI());
							mit1.setUI(new RCMenuItemUI());
							mit2.setUI(new RCMenuItemUI());
							mit3.setUI(new RCMenuItemUI());
							mit4.setUI(new RCMenuItemUI());
							mit5.setUI(new RCMenuItemUI());
							mit0.setOpaque(false);
							mit1.setOpaque(false);
							mit2.setOpaque(false);
							mit3.setOpaque(false);
							mit4.setOpaque(false);
							mit5.setOpaque(false);
							mit0.setFont(BASIC_FONT);
							mit1.setFont(BASIC_FONT);
							mit2.setFont(BASIC_FONT);
							mit3.setFont(BASIC_FONT);
							mit4.setFont(BASIC_FONT);
							mit5.setFont(BASIC_FONT);
							mit0.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									File f = ((CategoryNode) (object)).getFile();
									try {
										Clip.openFile(f.getAbsolutePath());
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							});
							// 删除分组
							mit3.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									File file = ((CategoryNode) (object)).getFile();
									new YesNoDialog(MainFrame.getContext(), "确定删除此目录（" + file.getName() + "）？", new YesNoDialog.Callback() {
										@Override
										public void callback(boolean arg0) {
											if (!arg0)
												return;
											((CategoryNode) (object)).removeFromParent();
											deleteDir(object);
											model.nodeStructureChanged(root);
										}

									});
								}

							});
							// 更换名称
							mit1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									File dirFile = ((CategoryNode) (object)).getFile();
									new InputMessageDialog(MainFrame.getContext(), "目录更名", dirFile.getName(),
											new Callback() {
												public void callback(String string) {
													String path = dirFile.getParentFile().getAbsolutePath()
															+ File.separator + string;
													dirFile.renameTo(new File(path));
													((CategoryNode) (object)).setFile(new File(path));
												}
											});
									model.nodeStructureChanged((CategoryNode) (object));
								}
							});
							mit2.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									CategoryNode node = (CategoryNode) object;
									File f = createAricle(node.getFile());
									Diary diary = new Diary();
									diary.setSort(node.getFile().listFiles().length);
									JsonUtils.saveObj(diary, f.getAbsolutePath());
									AricleNode newChild = new AricleNode(f, diary);

									((CategoryNode) (object)).insert(newChild, 0);
									model.nodeStructureChanged(node);
									catalogTree.expandPath(path);
								}
							});
							mit4.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									CategoryNode node = (CategoryNode) object;
									int childCount = node.getChildCount();
									List<AricleNode> lists = Lists.newArrayList();
									for (int i = 0; i < childCount; i++) {
										AricleNode childAt = (AricleNode) node.getChildAt(i);
										lists.add(childAt);
									}
									Collections.sort(lists, new Comparator<AricleNode>() {
										@Override
										public int compare(AricleNode o1, AricleNode o2) {
											return o1.aritcle.getTitle().compareToIgnoreCase(o2.aritcle.getTitle());
										}
									});
									StringBuilder sb = new StringBuilder();
									for (AricleNode aricleNode : lists) {
										sb.append(aricleNode.aritcle.getTitle()).append("\r\n");
										sb.append(aricleNode.aritcle.getContent()).append("\r\n");
										sb.append("--------------");
									}
									JFileChooser fileChooser = new JFileChooser();
									fileChooser.setDialogTitle("请选择保存位置");
									fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

									fileChooser.showDialog(MainFrame.getContext(), "保存");
									File selectedFile = fileChooser.getCurrentDirectory();
									if (selectedFile != null) {
										String path = selectedFile.getAbsolutePath();
										String filePath = path + File.separator + node.getFile().getName() + ".txt";
										FileUtils.writeContent(filePath, sb.toString());
										try {
											Clip.openFile(path);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
									}
								}
							});
							mit5.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									CategoryNode node = (CategoryNode) object;
									new InputMessageDialog(MainFrame.getContext(), "建立子目录", "",
											new Callback() {
												public void callback(String string) {
													String path = node.getFile().getAbsolutePath() + File.separator + string;
													if (!new File(path).exists()) {
														FileUtils.createDirectory(path);
														DiaryCatalogPanel.getContext().addNode(node,new File(path));
														DiaryCatalog diaryCatalog = new DiaryCatalog();
														diaryCatalog.setName(string);
														diaryCatalog.setUserId(Launcher.currentUser.getUserId());
//														addCatalogServer(diaryCatalog);
													} else {
														MainFrame.getContext().alert("已存在该目录！");
													}
												}
											});
									model.nodeStructureChanged((CategoryNode) (object));
									catalogTree.expandPath(path);
								}
							});
							pm.add(mit0);
							pm.add(mit1);
							pm.add(mit2);
							pm.add(mit3);
							pm.add(mit4);
							pm.add(mit5);
							pm.show(catalogTree, e.getX(), e.getY());
						}
						if (object instanceof AricleNode) {
							JPopupMenu pm = new JPopupMenu();
							pm.setBorder(new LineBorder(Colors.SCROLL_BAR_TRACK_LIGHT));
							pm.setBackground(Colors.FONT_WHITE);
							pm.setBorder(LIGHT_GRAY_BORDER);
							JMenuItem mit0 = new JMenuItem("更换名称");
							JMenuItem mit1 = new JMenuItem("打开文件");
							JMenuItem mit2 = new JMenuItem("删除文章");
							mit0.setUI(new RCMenuItemUI());
							mit1.setUI(new RCMenuItemUI());
							mit2.setUI(new RCMenuItemUI());
							// mit0.setOpaque(false);
							mit0.setFont(BASIC_FONT);
							// mit0.setFont(BASIC_FONT);
							mit2.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									String title = ((AricleNode) (object)).aritcle.getTitle();
									new YesNoDialog(MainFrame.getContext(), "确定删除此文章？----" + title, new YesNoDialog.Callback() {
										@Override
										public void callback(boolean arg0) {
											// TODO Auto-generated method stub
											if (!arg0)
												return;
											TreeNode parent2 = ((AricleNode) (object)).getParent();
											((AricleNode) (object)).removeFromParent();
											delAricle(((AricleNode) (object)).getFile());
											model.nodeStructureChanged(parent2);
										}

									});

								}
							});
							mit1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									File f = ((AricleNode) (object)).getFile();
									try {
										Clip.openFile(f.getAbsolutePath());
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							});
							mit0.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									final File aritcleFile = ((AricleNode) (object)).getFile();
									new InputMessageDialog(MainFrame.getContext(), "文章更名", aritcleFile.getName(),
											new Callback() {
												public void callback(String string) {
													String path = aritcleFile.getParentFile().getAbsolutePath()
															+ File.separator + string + DiaryPanel.diarySuffix;
													aritcleFile.renameTo(new File(path));
												}
											});
									model.nodeStructureChanged(((AricleNode) (object)).getParent());
								}
							});
							pm.add(mit0);
							pm.add(mit1);
							pm.add(mit2);
							pm.show(catalogTree, e.getX(), e.getY());
						}
					}
				}
			}
		});
		catalogTree.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				int x = (int) arg0.getPoint().getX();
				int y = (int) arg0.getPoint().getY();
				// TreePath path = catalogTree.getPathForLocation(x, y);
				catalogTree.getComponentAt(x, y).repaint();
				TreeNodeRenderer.mouseRow = catalogTree.getRowForLocation(x, y);
				catalogTree.repaint();
			}
		});
		catalogTree.setTransferHandler(new MyJTreeTransferHandler());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setViewportView(catalogTree);
		getViewport().setOpaque(false);
		// jScrollPane.getViewport().setBackground( UI.backColor);
		// jScrollPane.setBackground(UI.backColor);
		getVerticalScrollBar().setBackground(UI.backColor);
		// jScrollPane.getVerticalScrollBar().setVisible(false);
		getVerticalScrollBar().setUI(new cn.harry12800.j2se.style.ui.MyScrollBarUI());
		// 屏蔽横向滚动条
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	private void loadNode() {
		File file = new File(dirPath);
		File[] listFiles = file.listFiles(filter);
		int x = 0;
		if (Objects.isNull(listFiles) || listFiles.length == 0) {
			String test = dirPath + File.separator + "Test";
			new File(test).mkdirs();
		}
		listFiles = file.listFiles(filter);
		for (final File f : listFiles) {
			if (f.isDirectory()) {
				CategoryNode node = new CategoryNode(f);
				root.add(node);
				int i = 1;
				File[] listFiles2 = f.listFiles(filter);

				for (File file2 : listFiles2) {
					Diary a = new Diary();
					a.setSort(i);
					AricleNode newChild = new AricleNode(file2, a);
					boolean mark = true;
					if (i > 1) {
						String title2 = newChild.aritcle.getTitle();
						AricleNode child = (AricleNode) node.getFirstChild();
						int index = 0;
						while (mark && child != null) {
							String title = child.aritcle.getTitle();
							if (title.compareTo(title2) < 0) {
								node.insert(newChild, index);
								mark = false;
							}
							index++;
							child = (AricleNode) node.getChildAfter(child);
						}
					}
					if (mark)
						node.add(newChild);
					i++;
				}
			} else {
				x++;
				Diary a = new Diary();
				a.setSort(x);
				AricleNode node = new AricleNode(f, a);
				root.add(node);
			}
		}
	}

	private void deleteDir(Object object) {
		File file = ((CategoryNode) (object)).getFile();
		new Thread(new Runnable() {
			@Override
			public void run() {
				File[] listFiles = file.listFiles(new FileFilter() {

					@Override
					public boolean accept(File file) {
						return file.getName().endsWith("dir");
					}
				});
				if (listFiles != null && listFiles.length == 1) {
					try {
						DiaryCatalog dc = JsonUtils.string2Json(listFiles[0], DiaryCatalog.class);
						String path = Contants.getPath(Contants.diaryCatalogDelUrl);
						String string = HttpUtil.get(path + "?id=" + dc.getId());
						System.out.println(string);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				FileUtils.deleteDir(file);
			}
		}).start();

	}

	// 微软雅黑
	public static Font BASIC_FONT = new Font("微软雅黑", Font.PLAIN, 12);
	public static Font BASIC_FONT2 = new Font("微软雅黑", Font.TYPE1_FONT, 12);
	// 楷体
	public static Font DIALOG_FONT = new Font("楷体", Font.PLAIN, 16);

	public static Border GRAY_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	public static Border ORANGE_BORDER = BorderFactory.createLineBorder(Color.ORANGE);
	public static Border LIGHT_GRAY_BORDER = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	public void addNode(File file) {
		CategoryNode node = new CategoryNode(file);
		// System.out.println("-:" + root.getChildCount());
		root.insert(node, root.getChildCount());
		if (root.getChildCount() == 1) {
			model = new DefaultTreeModel(root);
			catalogTree.setModel(model);
		}
		model.reload();
		// catalogTree.putClientProperty("JTree.lineStyle", "None");
	}
	public void addNode(CategoryNode node,File file) {
		CategoryNode child = new CategoryNode(file);
		// System.out.println("-:" + root.getChildCount());
		node.insert(child, 0);
		if (root.getChildCount() == 1) {
			model = new DefaultTreeModel(root);
			catalogTree.setModel(model);
		}
		model.reload();
		// catalogTree.putClientProperty("JTree.lineStyle", "None");
	}
	/**
	 * 
	 * @param file
	 * @return
	 */
	public File createAricle(File file) {
		String pathString = file.getAbsolutePath() + File.separator + StringUtils.getUUID() + ".properties";
		FileUtils.createFile(pathString);
		return new File(pathString);
	}

	public void delAricle(File file) {
		if (catalogTree != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					deleteFromServer(file);
				}
			}).start();

			file.delete();
		}
	}

	private void deleteFromServer(File file) {
		try {
			Diary d = JsonUtils.string2Json(file, Diary.class);
			String path = Contants.getPath(Contants.diaryDelUrl);
			String string = HttpUtil.get(path + "?id=" + d.getId());
			System.out.println(string);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setCurrNode(DefaultMutableTreeNode node) {
		this.currNode = node;
	}

	public String currPath;

	public void setCurrTree(JTree catalogTree) {
		this.catalogTree = catalogTree;
	}

	public JTree getCatalogTree() {
		return catalogTree;
	}

	public DefaultTreeModel getModel() {
		return model;
	}

	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}

	public void setCatalogTree(JTree catalogTree) {
		this.catalogTree = catalogTree;
	}

	public String getCurrPath() {
		return currPath;
	}

	public void setCurrPath(String currPath) {
		this.currPath = currPath;
	}

	public DefaultMutableTreeNode getCurrNode() {
		return currNode;
	}

	public void reload() {
		root.removeAllChildren();
		model = new DefaultTreeModel(root);
		catalogTree.setModel(model);
		loadNode();
		model.reload();
	}
}
