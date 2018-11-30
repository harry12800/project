package cn.harry12800.vchat.model.diary;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Lists;

public class MyJTreeTransferHandler extends TransferHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyJTreeTransferHandler() {
	}

	public int getSourceActions(JComponent c) {
		return MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		JTree tree = (JTree) c;
		TreePath[] paths = tree.getSelectionPaths();
		List<DefaultMutableTreeNode> nodes = Lists.newArrayList();
		for (TreePath path : paths) {
			nodes.add((DefaultMutableTreeNode) path.getLastPathComponent());
		}
		return new JTreeTransferable(nodes);
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		// if (action!=MOVE) return;
		// try {
		// TreePath[]
		// paths=(TreePath[])data.getTransferData(JTreeTransferable.FLAVOR);
		// JTree tree=(JTree)source;
		// DefaultTreeModel model=(DefaultTreeModel)tree.getModel();
		//
		// for (TreePath path:paths){
		//
		// DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		// path.getLastPathComponent();
		// // model.nodesWereRemoved(node.getParent(),new int[]{ 2},new
		// DefaultMutableTreeNode[]{node});
		// model.nodeStructureChanged(node);
		// model.removeNodeFromParent(node);
		//
		// }
		// } catch (Exception e) {
		// e.printStackTrace(); //To change body of catch statement use File |
		// Settings | File Templates.
		// }
	}

	@Override
	public boolean canImport(TransferSupport support) {
		if (support.isDataFlavorSupported(JTreeTransferable.FLAVOR)) {
			// Component component = support.getComponent();
			// System.out.println(component);
			// DataFlavor[] dataFlavors = support.getDataFlavors();
			// for (DataFlavor dataFlavor : dataFlavors) {
			// System.out.println(dataFlavor);
			// }
			JTree.DropLocation location = (JTree.DropLocation) support.getDropLocation();
			DefaultMutableTreeNode newParent = (DefaultMutableTreeNode) location.getPath().getLastPathComponent();

			if (newParent instanceof AricleNode) {
				return false;
			}
			if (support.getDropAction() == MOVE)
				return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean importData(TransferSupport support) {
		JTree tree = (JTree) support.getComponent();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		Transferable transfer = support.getTransferable();
		try {
			List<DefaultMutableTreeNode> nodes = (List<DefaultMutableTreeNode>) transfer
					.getTransferData(JTreeTransferable.FLAVOR);
			JTree.DropLocation location = (JTree.DropLocation) support.getDropLocation();
			DefaultMutableTreeNode newParent = (DefaultMutableTreeNode) location.getPath().getLastPathComponent();
			for (DefaultMutableTreeNode node : nodes) {
				if (node instanceof CategoryNode)
					continue;
				model.removeNodeFromParent(node);
				model.insertNodeInto(node, newParent, newParent.getChildCount());
				File file = ((AricleNode) node).getFile();
				FileUtils.moveFile(file, ((CategoryNode) newParent).getFile().getAbsolutePath());
				// ((AricleNode)node).setFile(new
				// File(((CategoryNode)newParent).getFile().getAbsolutePath()+File.separator+file.getName())
				// );

				// tree.expandRow(row);
			}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

class JTreeTransferable implements Transferable {
	public static DataFlavor FLAVOR = null;
	private List<DefaultMutableTreeNode> nodes;

	public JTreeTransferable(List<DefaultMutableTreeNode> nodes) {
		try {
			FLAVOR = new DataFlavor(
					DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + ArrayList.class.getName() + "\"");
			this.nodes = nodes;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<DefaultMutableTreeNode> getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		return nodes;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { FLAVOR };
	}

	public boolean isDataFlavorSupported(DataFlavor flv) {
		return FLAVOR.equals(flv);
	}
}
