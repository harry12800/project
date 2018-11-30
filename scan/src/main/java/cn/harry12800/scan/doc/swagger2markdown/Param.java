package cn.harry12800.scan.doc.swagger2markdown;

public class Param {
	public String name = "path";
	public String in = "query";
	public String description = "path";
	public boolean required = true;
	public String type = "string";
	public String format;
	public String ref;
	public String refType;
	public boolean isRef;

	@Override
	public String toString() {
		return "Param [name=" + name + ", in=" + in + ", description=" + description + ", required=" + required + ", type=" + type + ", format=" + format + ", ref=" + ref + "]";
	}

}