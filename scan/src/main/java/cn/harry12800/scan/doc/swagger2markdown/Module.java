package cn.harry12800.scan.doc.swagger2markdown;

import java.util.UUID;

/**
 * 
 * @author zr0014
 * 2018年8月23日
 */
public class Module {

	/**
	 * 主键
	 */
	public String id;
	/**
	 * 名称
	 */
	public String name;
	/**
	 * 描述
	 */
	public String desc;
	public String markdown;

	public Module() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Module other = (Module) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
