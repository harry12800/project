package cn.harry12800.scan;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import cn.harry12800.api.DESUtils;

/** 
 * 继承PropertyPlaceholderConfigurer定义支持密文版属性的属性配置器 
 *  
 * @author moziqi 
 *  
 */
@Component
public class EncryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private String[] encryptPropNames = { "userName", "password" };

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		System.out.println(propertyName + "解密内容:" + propertyValue);
		if (isEncryptProp(propertyName)) {
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			System.out.println(propertyName + "解密内容:" + decryptValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

	/** 
	 * 判断是否是加密的属性 
	 * @param propertyName 
	 * @return 
	 */
	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
}