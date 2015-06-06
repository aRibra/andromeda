package com.couponsystem.exceptions;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CouponSystemExceptionsProperties {

	private Map<String, String> exceptionsMap = new HashMap<String, String>();

	public Map<String, String> getExceptionsFromProperties() throws Exception {

		Properties properties = new Properties();
		String propFileName = "resources/coupon_system_exceptions.properties";

		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(propFileName);

		if (inputStream != null) {
			properties.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName
					+ "' not found in the classpath");
		}

		// loading properties in hashmap
		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			exceptionsMap.put(key, value);
		}
		
		return exceptionsMap;

	}

	public Map<String, String> getExceptionsMap() {
		return exceptionsMap;
	}

}
