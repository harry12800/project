
package cn.harry12800.common.module.packet;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class LoginPacket extends GoBackPacket {

	// public Logger logger = Logger.getLogger(LoginPacket.class);
	static Header reqHeader;
	static Header respHeader;
	static {
		HeaderBodyMap.register(ProtocolConstant.SID_LOGIN, ProtocolConstant.CID_LOGIN_REQ_USERLOGIN,Request.class);
		HeaderBodyMap.register(ProtocolConstant.SID_LOGIN, ProtocolConstant.CID_LOGIN_REQ_USERLOGIN+1,Response.class);
		respHeader = new Header(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_REQ_USERLOGIN+1);
	}
	public LoginPacket() { }

	@SuppressWarnings("unchecked")
	public LoginPacket(String userName, String passward) {
		requestPacket = new RequestPacket();
		requestPacket.header = new Header(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_REQ_USERLOGIN);
		requestPacket.body = new Request(userName, passward);
		short seqNo = SequenceNumberMaker.getInstance().make();
		requestPacket.header.reserved = seqNo;
		requestPacket.body.setNeedMonitor(true);
	}

	public static class Request extends ReqBody {
		@Tag(2)
		/**
		 * 用户名
		 */
		public String userName;
		/**
		 * 密码
		 */
		@Tag(3)
		public String passward;

		public Request() {
		}

		public Request(String userName, String passward) {
			this.userName = userName;
			this.passward = passward;
		}

	}

	public static class Response extends RespBody {
		/**
		 * id
		 */
		@Tag(2)
		public long id;
		/**
		 * 用户名
		 */
		@Tag(3)
		public String userId;

		/**
		 * 昵称
		 */
		@Tag(4)
		public String nickName;
		/**
		 * 真实姓名
		 */
		@Tag(5)
		public String realName;
		/**
		 * 头像路径
		 */
		@Tag(6)
		public String avatarUrl;
		/**
		 * 创建时间。
		 */
		@Tag(7)
		public Long createTime;
		@Tag(8)
		public String phone;
		@Tag(9)
		public String sex;
		@Tag(10)
		public String mail;

		public Response() { }

		@Override
		public String toString() {
			return "LoginResponse [id=" + id + ", userId=" + userId + ", nickName=" + nickName + ", realName="
					+ realName + ", avatarUrl=" + avatarUrl + ", createTime=" + createTime + ", phone=" + phone
					+ ", sex=" + sex + ", mail=" + mail + "]";
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}

		public Long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

	 
	}

	public static Header copyHeader() {
		return new Header(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_REQ_USERLOGIN);
	}
}
