//package cn.harry12800.vchat.model.file;
//
//import java.awt.datatransfer.DataFlavor;
//import java.awt.dnd.DnDConstants;
//import java.awt.dnd.DropTarget;
//import java.awt.dnd.DropTargetDragEvent;
//import java.awt.dnd.DropTargetDropEvent;
//import java.awt.dnd.DropTargetEvent;
//import java.awt.dnd.DropTargetListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.io.File;
//import java.util.List;
//
//import javax.swing.JLayeredPane;
//
//import cn.harry12800.j2se.component.ClickAction;
//import cn.harry12800.j2se.component.ExitBtn;
//import cn.harry12800.j2se.component.LabelButton;
//import cn.harry12800.j2se.component.LabelButton.Builder;
//import cn.harry12800.j2se.dialog.InputMessageDialog;
//import cn.harry12800.j2se.dialog.InputMessageDialog.Callback;
//import cn.harry12800.j2se.style.UI;
//import cn.harry12800.j2se.utils.Clip;
//
//public class ContentPane extends JLayeredPane implements DropTargetListener {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -2305407977923915933L;
//	private int width = 130;
//	private int height = 20;
//	private int size;
//	public boolean drag = false;
//	private OpenFilePanel openFilePanel;
//
//	public ContentPane(final OpenFilePanel openFilePanel, List<OpenFileType> openFiles) {
//		this.openFilePanel = openFilePanel;
//		this.size = openFiles.size();
//		setSize(150, size * height);
//		setBackground(UI.backColor);
//		setLayout(null);
//		setOpaque(false);
//		DragSlideListener dragSlideListener = new DragSlideListener(this);
//		int i = 0;
//		for (final OpenFileType of : openFiles) {
//			Builder builder = LabelButton.createBgColorBuilder(UI.foreColor, new File(of.getPath()));
//			builder.hasTip = false;
//			final LabelButton fileBtn = new LabelButton(of.getName(), width, height, builder);
//			dragSlideListener.addCom(fileBtn);
//
//			fileBtn.addMouseListener(new ClickAction(fileBtn) {
//				public void leftClick(MouseEvent e) {
//					try {
//						if (!drag)
//							Clip.openFile(of.getPath());
//					} catch (Exception e1) {
//						openFilePanel.alert(e1.getMessage());
//					}
//				}
//
//				@Override
//				public void rightClick(MouseEvent e) {
////					new InputMessageDialog(openFilePanel.getContext().getFrame(), "重命名", of.getName(), new Callback() {
//						public void callback(String string) {
//							of.setName(string);
//							fileBtn.setName(string);
//							fileBtn.repaint();
////							openFilePanel.saveConfigObject();
//						}
//					});
//				}
//			});
//			//			dragSlideListener.addCom(fileBtn);
//			fileBtn.setBounds(1, height * i, width, height);
//			cn.harry12800.j2se.component.ExitBtn.Builder createBgColorBuilder = ExitBtn.createBgColorBuilder(UI.backColor);
//			createBgColorBuilder.align = 2;
//			ExitBtn exit = new ExitBtn(15, height, createBgColorBuilder);
//			final int x = i;
//			exit.addMouseListener(new ClickAction(fileBtn) {
//				@Override
//				public void leftClick(MouseEvent e) {
//					openFilePanel.deleteOpenFile(x);
//				}
//			});
//			exit.setBounds(width, height * i, 15, height);
//			i++;
//			add(fileBtn);
//			add(exit);
//		}
//		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
//		addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				System.out.println(e.getKeyCode());
//			}
//		});
//	}
//
//	@Override
//	public void drop(DropTargetDropEvent dtde) {
//		try {
//			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) // 如果拖入的文件格式受支持
//			{
//				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
//				// 接收拖拽来的数据
//				@SuppressWarnings("unchecked")
//				List<File> list = (List<File>) (dtde.getTransferable()
//						.getTransferData(DataFlavor.javaFileListFlavor));
//				openFilePanel.addSave(list);
//			} else {
//				dtde.rejectDrop();// 否则拒绝拖拽来的数据
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
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
//}
