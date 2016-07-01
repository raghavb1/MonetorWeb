package com.champ.core.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.enums.Property;

@Cache(name = "propertyMapCache")
public class PropertyMapCache {

	private Map<String, Object> propertiesMap = new LinkedHashMap<String, Object>();

	public Map<String, Object> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, Object> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	public void addProperty(String key, String value) {
		propertiesMap.put(key, value);
	}

	public String getPropertyString(Property property) {
		return (String) propertiesMap.get(property.getName());
	}

	public Integer getPropertyInteger(Property property) {
		return Integer.parseInt((String) propertiesMap.get(property.getName()));
	}

	public Boolean getPropertyBoolean(Property property) {
		return Boolean.parseBoolean((String) propertiesMap.get(property.getName()));
	}
}
