package cn.harry12800.db.entity;

import java.util.Objects;

public class Int64SequenceEntity {
	private String sequenceName = "";
	private Long sequenceValue = 0L;

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName == null ? "" : sequenceName.trim();
	}

	public Long getSequenceValue() {
		return sequenceValue;
	}

	public void setSequenceValue(Long sequenceValue) {
		if (Objects.nonNull(sequenceValue)) {
			this.sequenceValue = sequenceValue;
		}
	}
}