package com.champ.core.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyMap {

	private String key;
	private String value;
	private Map<String, PropertyMap> map = new HashMap<String, PropertyMap>();
	private Map<String, String> valueMap;
	private List<String> valueList;

	public PropertyMap(String key, String value, Map<String, PropertyMap> map, Map<String, String> valueMap,
			List<String> valueList) {
		super();
		this.key = key;
		this.value = value;
		this.map = map;
		this.valueMap = valueMap;
		this.valueList = valueList;
	}

	public boolean equals(Object anObject) {
		if (anObject == null) {
			return false;
		}
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof PropertyMap) {
			PropertyMap anotherEntity = (PropertyMap) anObject;
			return anotherEntity.getKey().equals(this.key);
		}
		return false;
	}

	public int hashCode() {
		if (this.key != null) {
			return this.key.hashCode();
		}
		return super.hashCode();
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}

	public List<String> getValueList() {
		return valueList;
	}

	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, PropertyMap> getMap() {
		return map;
	}

	public void setMap(Map<String, PropertyMap> map) {
		this.map = map;
	}

}
