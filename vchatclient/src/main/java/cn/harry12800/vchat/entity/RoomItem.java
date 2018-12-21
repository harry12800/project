package cn.harry12800.vchat.entity;

/**
 * 消息列表中显示的房间条目 Created by harry12800 on 24/03/2017.
 */

public class RoomItem implements Comparable<RoomItem> {
	private long roomId;
	private String title;
	private String lastMessage;
	private int unreadCount;
	private long timestamp;
	private String type;

	public RoomItem() {
	}

	public RoomItem(long roomId, String title, String lastMessage, int unreadCount, long timestamp, String type) {
		this.roomId = roomId;
		this.title = title;
		this.lastMessage = lastMessage;
		this.unreadCount = unreadCount;
		this.timestamp = timestamp;
		this.type = type;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "RoomItem{" + "roomId='" + roomId + '\'' + ", title='" + title + '\'' + ", lastMessage='" + lastMessage
				+ '\'' + ", unreadCount=" + unreadCount + ", timestamp='" + timestamp + '\'' + '}';
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(RoomItem o) {
		// 注意，不能强制转int, 两个时间相差太远时有可能溢出
		// 忽略结果为0的情况，两个item必有先后，没有相同
		long ret = o.getTimestamp() - this.getTimestamp();
		return ret > 0 ? 1 : -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (roomId ^ (roomId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomItem other = (RoomItem) obj;
		if (roomId != other.roomId)
			return false;
		return true;
	}

	 
}
