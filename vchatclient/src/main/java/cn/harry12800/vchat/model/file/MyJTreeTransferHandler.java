//package cn.harry12800.vchat.model.file;
//
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.Transferable;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JComponent;
//import javax.swing.JTree;
//import javax.swing.TransferHandler;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;
//import javax.swing.tree.MutableTreeNode;
//import javax.swing.tree.TreePath;
//
//import cn.harry12800.tools.Lists;
//
//public class MyJTreeTransferHandler extends TransferHandler {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private OpenFilePanel openFilePanel;
//
//	public MyJTreeTransferHandler(OpenFilePanel openFilePanel) {
//		this.openFilePanel = openFilePanel;
//	}
//
//	public int getSourceActions(JComponent c) {
//		return TransferHandler.MOVE;
//	}
//
//	@Override
//	protected Transferable createTransferable(JComponent c) {
//		JTree tree = (JTree) c;
//		TreePath[] paths = tree.getSelectionPaths();
//		List<DefaultMutableTreeNode> nodes = Lists.newArrayList();
//		for (TreePath path : paths) {
//			nodes.add((DefaultMutableTreeNode) path.getLastPathComponent());
//		}
//		return new JTreeTransferable(nodes);
//	}
//
//	@Override
//	protected void exportDone(JComponent source, Transferable data, int action) {
//	}
//
//	@Override
//	public boolean canImport(TransferSupport support) {
//		if (support.isDataFlavorSupported(JTreeTransferable.FLAVOR)) {
//			if (support.getDropAction() == MOVE)
//				return true;
//		}
//		return true;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean importData(TransferSupport support) {
//		JTree tree = (JTree) support.getComponent();
//		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
//		Transferable transfer = support.getTransferable();
//		try {
//			List<DefaultMutableTreeNode> nodes = (List<DefaultMutableTreeNode>) transfer.getTransferData(JTreeTransferable.FLAVOR);
//			JTree.DropLocation location = (JTree.DropLocation) support
//					.getDropLocation();
//			DefaultMutableTreeNode newParent = (DefaultMutableTreeNode) location
//					.getPath().getLastPathComponent();
//			for (DefaultMutableTreeNode node : nodes) {
//				MutableTreeNode parent = (MutableTreeNode) newParent.getParent();
//				int index = parent.getIndex(newParent);
//				model.removeNodeFromParent(node);
//				model.insertNodeInto(node, parent, index);
//				((FileNode) node).panel.drag(((FileNode) node).file, index);
//
//			}
//		} catch (Exception e) {
//			//			e.printStackTrace();
//			transfer = support.getTransferable();
//			List<File> list;
//			boolean dataFlavorSupported = transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
//			if (dataFlavorSupported) {
//				try {
//					list = (List<File>) (transfer
//							.getTransferData(DataFlavor.javaFileListFlavor));
//					for (File file : list) {
//						System.out.println(file);
//					}
//					openFilePanel.addSave(list);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			} else {
//
//			}
//			return false;
//		}
//		return true;
//	}
//}
//
//class JTreeTransferable implements Transferable {
//	public static DataFlavor FLAVOR = null;
//	private List<DefaultMutableTreeNode> nodes;
//
//	public JTreeTransferable(List<DefaultMutableTreeNode> nodes) {
//		try {
//			FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
//					+ ";class=\"" + ArrayList.class.getName() + "\"");
//			this.nodes = nodes;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	public List<DefaultMutableTreeNode> getTransferData(DataFlavor flavor)
//			throws UnsupportedFlavorException, IOException {
//		return nodes;
//	}
//
//	public DataFlavor[] getTransferDataFlavors() {
//		return new DataFlavor[] { FLAVOR };
//	}
//
//	public boolean isDataFlavorSupported(DataFlavor flv) {
//		return FLAVOR.equals(flv);
//	}
//}
