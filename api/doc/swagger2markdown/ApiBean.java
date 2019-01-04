package cn.harry12800.api.doc.swagger2markdown;

import java.util.ArrayList;
import java.util.List;

public class ApiBean {
	public String summary = "";
	public String path = "";
	public String methods = "";
	public String operationId = "";
	public List<Param> params = new ArrayList<>();
	public String produces;
	public String consumes;

	@Override
	public String toString() {
		return "ApiBean [summary=" + summary + ", path=" + path + ", methods=" + methods + ", operationId=" + operationId + ", params=" + params + "]";
	}
}