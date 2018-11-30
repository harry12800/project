package cn.harry12800.vchat.server.module.entity;

import java.util.Date;

public class ChatMsg {
	private long id;
	private long come;
	private long go;
	private Date sendTime;
	private int dataType;
	private byte[] data;
	private int arrive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCome() {
		return come;
	}

	public void setCome(long fromId) {
		this.come = fromId;
	}

	public long getGo() {
		return go;
	}

	public void setGo(long toId) {
		this.go = toId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getArrive() {
		return arrive;
	}

	public void setArrive(int arrive) {
		this.arrive = arrive;
	}

}
