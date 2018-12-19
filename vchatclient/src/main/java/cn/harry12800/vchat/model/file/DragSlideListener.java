//package cn.harry12800.vchat.model.file;
//
//import java.awt.Cursor;
//import java.awt.Image;
//import java.awt.Point;
//import java.awt.Toolkit;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.StringSelection;
//import java.awt.datatransfer.Transferable;
//import java.awt.dnd.DnDConstants;
//import java.awt.dnd.DragGestureEvent;
//import java.awt.dnd.DragGestureListener;
//import java.awt.dnd.DragSource;
//import java.awt.dnd.DropTarget;
//import java.awt.dnd.DropTargetDragEvent;
//import java.awt.dnd.DropTargetDropEvent;
//import java.awt.dnd.DropTargetEvent;
//import java.awt.dnd.DropTargetListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionListener;
//import java.util.List;
//
//import javax.swing.JComponent;
//
//import cn.harry12800.tools.Lists;
//
//public class DragSlideListener {
//
//	private ContentPane panel;
//	private List<JComponent> list = Lists.newArrayList();
//	//	private JComponent component;
//
//	public DragSlideListener(ContentPane contentPane) {
//		this.panel = contentPane;
//	}
//
//	public void addCom(final JComponent com) {
//		list.add(com);
//		//		new DragListener(com);
//		DropTarget dt = new DropTarget(com, new DropTargetListener() {
//
//			@Override
//			public void dropActionChanged(DropTargetDragEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void drop(DropTargetDropEvent arg0) {
//				System.out.println("drop........................");
//				Transferable tt = arg0.getTransferable();
//				DataFlavor[] flavors = tt.getTransferDataFlavors();
//				for (int i = 0; i < flavors.length; i++) {
//					DataFlavor dataFlavor = flavors[i];
//					System.out.println("[" + i + "]" + dataFlavor);
//					try {// 看看这里的数据是否有需要的
//						System.out.println("\t"
//								+ tt.getTransferData(dataFlavor));
//					} catch (Throwable e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//			@Override
//			public void dragOver(DropTargetDragEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void dragExit(DropTargetEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void dragEnter(DropTargetDragEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		com.setDropTarget(dt);
//		DragSource dragSource = DragSource.getDefaultDragSource(); // 将srcLabel转换成拖放源，它能接受复制、移动两种操作
//		dragSource.createDefaultDragGestureRecognizer(com,
//				DnDConstants.ACTION_COPY_OR_MOVE, new DragGestureListener() {
//					public void dragGestureRecognized(DragGestureEvent event) { // 将JLabel里的文本信息包装成Transferable对象
//						String txt = "ad";
//						Transferable transferable = new StringSelection(txt);
//						// 继续拖放操作,拖放过程中使用手状光标
//						Image createImage = com.createImage(com.getWidth(), com.getHeight());
//						Toolkit tk = Toolkit.getDefaultToolkit();
//						//						Image image = new ImageIcon(url).getImage();
//						Cursor cursor = tk.createCustomCursor(createImage, new Point(0, 0), "cursor");
//
//						panel.setCursor(cursor); //panel 也可以是其他组件
//						event.startDrag(cursor, transferable);
//					}
//				});
//		com.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				panel.drag = false;
//				com.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //panel 也可以是其他组件
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//			}
//		});
//		com.addMouseMotionListener(new MouseMotionListener() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//			}
//
//			@Override
//			public void mouseDragged(MouseEvent e) {
//				panel.drag = true;
//				//				System.out.println("aa");
//				//				Image createImage = com.createImage(com.getWidth(),com.getHeight());
//				//				Toolkit tk = Toolkit.getDefaultToolkit(); 
//				////				Image image = new ImageIcon(url).getImage();
//				//				Cursor cursor = tk.createCustomCursor(createImage, new Point(10, 10), "norm"); 
//				//				panel.setCursor(cursor); //panel 也可以是其他组件
//			}
//		});
//	}
//}
