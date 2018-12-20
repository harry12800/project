package cn.harry12800.common.module.user.dto;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.serial.Serializer;
import cn.harry12800.common.module.chat.dto.ResourceDto;

public class PullResouceResponse extends Serializer {
	List<ResourceDto> resources = new ArrayList<ResourceDto>();

	@Override
	protected void read() {
		resources = readList(ResourceDto.class);
	}

	@Override
	protected void write() {
		writeList(resources);
	}

	public List<ResourceDto> getResources() {
		return resources;
	}

	public void setResources(List<ResourceDto> resources) {
		this.resources = resources;
	}

}
