package cn.harry12800.vchat.panels;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cn.harry12800.j2se.style.ui.Colors;
import cn.harry12800.vchat.adapter.RoomItemViewHolder;
import cn.harry12800.vchat.adapter.search.SearchResultItemsAdapter;
import cn.harry12800.vchat.components.GBC;
import cn.harry12800.vchat.components.RCListView;
import cn.harry12800.vchat.entity.SearchResultItem;

/**
 * 左侧搜索结果列表 Created by harry12800 on 17-6-21.
 */
@SuppressWarnings("serial")
public class SearchResultPanel extends ParentAvailablePanel {
	private static SearchResultPanel context;
	private final SearchResultItemsAdapter searchResultItemsAdapter;

	private RCListView resultItemsListView;
	private List<SearchResultItem> searchResultItems = new ArrayList<>();
	private JLabel tipLabel;

	public SearchResultPanel(JPanel parent) {
		super(parent);
		context = this;

		initComponents();
		initView();
		// initData();
		searchResultItemsAdapter = new SearchResultItemsAdapter(searchResultItems);
		resultItemsListView.setAdapter(searchResultItemsAdapter);
	}

	private void initComponents() {
		resultItemsListView = new RCListView();
		this.setBackground(Colors.DARK);

		tipLabel = new JLabel("无搜索结果");
		tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tipLabel.setForeground(Colors.FONT_GRAY);
		tipLabel.setVisible(false);
	}

	private void initView() {
		setLayout(new GridBagLayout());
		resultItemsListView.setContentPanelBackground(Colors.DARK);
		add(tipLabel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10, 0, 0, 0));
		add(resultItemsListView, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1000));
	}

	public JLabel getTipLabel() {
		return tipLabel;
	}

	public void setData(List<SearchResultItem> data) {
		searchResultItems.clear();
		searchResultItems.addAll(data);
		/*
		 * List<Room> rooms = roomService.findAll();
		 * 
		 * for (Room room : rooms) { SearchResultItem item = new
		 * SearchResultItem(room.getRoomId(), room.getName(), room.getType());
		 * searchResultItems.add(item); }
		 */
	}

	/**
	 * 重绘整个列表
	 */
	public void notifyDataSetChanged(boolean keepSize) {
		// initData();
		resultItemsListView.notifyDataSetChanged(keepSize);
	}

	/**
	 * 设置每个房间项目的背影色
	 *
	 * @param holder
	 * @param color
	 */
	public void setItemBackground(RoomItemViewHolder holder, Color color) {
		holder.setBackground(color);
		holder.nameBrief.setBackground(color);
		holder.timeUnread.setBackground(color);
	}

	public static SearchResultPanel getContext() {
		return context;
	}

	public void setKeyWord(String keyWord) {
		this.searchResultItemsAdapter.setKeyWord(keyWord);
	}

	public void setSearchMessageOrFileListener(
			SearchResultItemsAdapter.SearchMessageOrFileListener searchMessageOrFileListener) {
		if (this.searchResultItemsAdapter == null) {
			throw new RuntimeException("请先设置adapter!");
		}

		this.searchResultItemsAdapter.setSearchMessageOrFileListener(searchMessageOrFileListener);
	}

	public void moveToNextItem() {

	}
}
