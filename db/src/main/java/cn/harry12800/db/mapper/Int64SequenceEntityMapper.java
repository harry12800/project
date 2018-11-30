package cn.harry12800.db.mapper;

import org.apache.ibatis.annotations.Select;

import cn.harry12800.db.entity.Int64SequenceEntity;

public interface Int64SequenceEntityMapper {
	final String NEW_SEQUENCE_STATEMENT = "SELECT fingerchat.func_sequence(#{sequenceName}) AS NewSequence";
	final String NEXT_SEQUENCE_STATEMENT = "UPDATE int64_sequence SET sequence_value=LAST_INSERT_ID(sequence_value +1) WHERE sequence_name=#{sequenceName}; "
			+ "SELECT LAST_INSERT_ID();";

	Int64SequenceEntity selectByPrimaryKey(String sequenceName);

	Long getNextSequence(String sequenceName);

	@Select(NEW_SEQUENCE_STATEMENT)
	Long newSequence(String sequenceName);

	@Select(NEXT_SEQUENCE_STATEMENT)
	Long nextSequence(String sequenceName);
}