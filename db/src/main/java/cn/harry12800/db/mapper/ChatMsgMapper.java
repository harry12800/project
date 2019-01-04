package cn.harry12800.db.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.harry12800.db.entity.ChatMsg;

@Mapper
public interface ChatMsgMapper {

	@Select("")
	public ChatMsg getChatMsgById(long id);
	@Insert("insert into chat_msg(id,arrive,come,go,data,dataType,sendTime) "
			+ "values(#{id},#{arrive},#{come},#{go},#{data},#{dataType},#{sendTime})")
	public int createChatMsg(ChatMsg msg);
	public List<ChatMsg> getUnReadInfo(long userid) ;
	public void setReadInfo(long userid);
	public List<ChatMsg> findByIds(Set<?> set);
	public List<ChatMsg> findAll();
	public int save(ChatMsg t);
	public int deleteByIds(Set<?> set);
	public int update(ChatMsg t);
}
