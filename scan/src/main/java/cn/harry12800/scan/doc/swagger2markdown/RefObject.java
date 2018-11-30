package cn.harry12800.scan.doc.swagger2markdown;

import java.util.ArrayList;
import java.util.List;

public class RefObject {
	public String type = "";
	public String format = "";
	public String description = "";
	public List<Param> properties = new ArrayList<>();
	public ArrayList<String> required;

	@Override
	public String toString() {
		return "RefObject [type=" + type + ", format=" + format + ", description=" + description + ", properties="
				+ properties + ", required=" + required + "]";
	}

}
