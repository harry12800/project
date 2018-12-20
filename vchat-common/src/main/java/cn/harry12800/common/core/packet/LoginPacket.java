
package cn.harry12800.common.core.packet;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.config.SysConstant;
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

//    private Logger logger = Logger.getLogger(LoginPacket.class);
    static Header loginHeader;
    static {
        HeaderBodyMap.register(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_REQ_USERLOGIN+1,LoginPacket.LoginResponse.class);
        loginHeader = new Header();
        //loginHeader.setFlag((short) SysConstant.PROTOCOL_FLAG);
        loginHeader.setServiceId(ProtocolConstant.SID_LOGIN);
        loginHeader.setCommandId(ProtocolConstant.CID_LOGIN_REQ_USERLOGIN);
        loginHeader.setVersion((short) SysConstant.PROTOCOL_VERSION);
        //loginHeader.setError((short) SysConstant.PROTOCOL_ERROR);
        loginHeader.setLength(SysConstant.PROTOCOL_HEADER_LENGTH );

    }
    public LoginPacket() {

    }
    
    public LoginPacket(String _user_id_url, String _user_token, int _online_status,
            int _client_type, String _client_version) {
        requestPacket = new RequestPacket();
        requestPacket.header = loginHeader;
        requestPacket.body = new LoginRequest(_user_id_url, _user_token, _online_status, _client_type,
                _client_version);
        short seqNo = SequenceNumberMaker.getInstance().make();
        requestPacket.header.setReserved(seqNo);
        requestPacket.body.setNeedMonitor(true);
    }

    public static class LoginRequest extends ReqBody {
        @Tag(2)
        private String user_id_url;
        @Tag(3)
        private String user_token;
        @Tag(4)
        private int online_status;
        @Tag(5)
        private int client_type;
        @Tag(6)
        private String client_version;

        @Override
        public String toString() {
            return "LoginRequest{" +
                    "user_id_url='" + user_id_url + '\'' +
                    ", user_token='" + user_token + '\'' +
                    ", online_status=" + online_status +
                    ", client_type=" + client_type +
                    ", client_version='" + client_version + '\'' +
                    '}';
        }

        public LoginRequest(){}
        public LoginRequest(String _user_id_url, String _user_token, int _online_status,
                            int _client_type, String _client_version) {
            user_id_url = _user_id_url;
            user_token = _user_token;
            online_status = _online_status;
            client_type = _client_type;
            client_version = _client_version;

        }

        public String getClient_version() {
            return client_version;
        }

        public void setClient_version(String client_version) {
            this.client_version = client_version;
        }

        public int getOnline_status() {
            return online_status;
        }

        public void setOnline_status(int online_status) {
            this.online_status = online_status;
        }

        public int getClient_type() {
            return client_type;
        }

        public void setClient_type(int client_type) {
            this.client_type = client_type;
        }

        public String getUser_id_url() {
            return user_id_url;
        }

        public void setUser_id_url(String user_id_url) {
            this.user_id_url = user_id_url;
        }

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }
    }

    public static class LoginResponse extends RespBody {
        @Tag(2)
        public int server_time;
        @Tag(3)
        public int result;
        @Tag(4)
        public int online_status;
        @Tag(5)
        public String userId;
        @Tag(6)
        public String nickname;
        @Tag(7)
        public String avatar_url;
        @Tag(8)
        public String title;
        @Tag(9)
        public String position;
        @Tag(10)
        public int roleStatus;
        @Tag(11)
        public int sex;
        @Tag(12)
        public String departId;
        @Tag(13)
        public int jobNumber;
        @Tag(14)
        public String telphone;
        @Tag(15)
        public String email;
        @Tag(16)
        public String token;


        public LoginResponse() {

        }

        public User getUser() {
            User user = new User();
            user.setOnlineStatus(online_status);
            user.setUserId(userId);
            user.setNickName(nickname);
            user.setAvatarUrl(avatar_url);
            user.setTitle(title);
            user.setPosition(position);
            user.setRoleStatus(roleStatus);
            user.setSex(sex);
            user.setDepartId(departId);
            user.setJobNum(jobNumber);
            user.setTelphone(telphone);
            user.setEmail(email);
            user.setToken(token);

            return user;
        }

        public int getServer_time() {
            return server_time;
        }

        public void setServer_time(int server_time) {
            this.server_time = server_time;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getOnline_status() {
            return online_status;
        }

        public void setOnline_status(int online_status) {
            this.online_status = online_status;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getRoleStatus() {
            return roleStatus;
        }

        public void setRoleStatus(int roleStatus) {
            this.roleStatus = roleStatus;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getDepartId() {
            return departId;
        }

        public void setDepartId(String departId) {
            this.departId = departId;
        }

        public int getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(int jobNumber) {
            this.jobNumber = jobNumber;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "LoginResponse{" +
                    "server_time=" + server_time +
                    ", result=" + result +
                    ", online_status=" + online_status +
                    ", userId='" + userId + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", avatar_url='" + avatar_url + '\'' +
                    ", title='" + title + '\'' +
                    ", position='" + position + '\'' +
                    ", roleStatus=" + roleStatus +
                    ", sex=" + sex +
                    ", departId='" + departId + '\'' +
                    ", jobNumber=" + jobNumber +
                    ", telphone='" + telphone + '\'' +
                    ", email='" + email + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
