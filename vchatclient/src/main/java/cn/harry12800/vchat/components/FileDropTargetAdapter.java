package cn.harry12800.vchat.components;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.io.File;
import java.util.List;

import cn.harry12800.j2se.dialog.MessageDialog;
import cn.harry12800.vchat.components.message.FileEditorThumbnail;
import cn.harry12800.vchat.frames.MainFrame;

public class FileDropTargetAdapter extends DropTargetAdapter {

//	private DropTarget dt1;
	private RCTextEditor editor;

	public FileDropTargetAdapter(RCTextEditor editor) {
//		this.dt1 = dt1;
		this.editor = editor;
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		//		Object source = dtde.getSource();
		try {
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) // 如果拖入的文件格式受支持
			{
				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
				// 接收拖拽来的数据
				@SuppressWarnings("unchecked")
				List<File> list = (List<File>) (dtde.getTransferable()
						.getTransferData(DataFlavor.javaFileListFlavor));

				for (File file : list) {
					if (file.isDirectory()) {
						new MessageDialog(MainFrame.getContext(), "提示", "目前不支持发送文件夹！sorry");
						return;
					}
				}
				for (File file : list) {
					FileEditorThumbnail thumbnail = new FileEditorThumbnail(file.getAbsolutePath());
					editor.insertComponent(thumbnail);
				}
			} else {
				dtde.rejectDrop();// 否则拒绝拖拽来的数据
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		super.dragEnter(dtde);
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		super.dragExit(dte);
	}
}
