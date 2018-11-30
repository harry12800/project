package cn.harry12800.vchat.server.module.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.harry12800.vchat.server.module.entity.ChatMsg;

@Mapper
public interface ChatMsgMapper {

	@Select("")
	public ChatMsg getChatMsgById(long id);
	@Insert("insert into chat_msg(id,arrive,come,go,data,dataType,sendTime) "
			+ "values(#{id},#{arrive},#{come},#{go},#{data},#{dataType},#{sendTime})")
	public int createChatMsg(ChatMsg msg);
	public List<ChatMsg> getUnReadInfo(long userid) ;
	public void setReadInfo(long userid);
}
