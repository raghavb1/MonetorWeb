package com.champ.core.cache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.dto.PropertyMap;
import com.champ.core.enums.Property;

@Cache(name = "propertyMapCache")
public class PropertyMapCache {

	private Map<String, PropertyMap> propertyMap = new HashMap<String, PropertyMap>();
	private static final String separator = "~";
	private static final String listSeparator = ",";

	public PropertyMapCache(Map<String, PropertyMap> propertyMap) {
		super();
		this.propertyMap = propertyMap;
	}

	public PropertyMapCache() {
		super();
	}

	@SuppressWarnings("unchecked")
	public void putProperty(String key, Object value) {
		if (key != null) {
			String[] keyLabels = key.split(separator);
			PropertyMap parentObject = null;
			Map<String, PropertyMap> internalMap = propertyMap;
			for (int i = 0; i < keyLabels.length; i++) {
				if (internalMap != null && internalMap.containsKey(keyLabels[i])) {
					parentObject = internalMap.get(keyLabels[i]);
					if (parentObject == null) {
						parentObject = new PropertyMap(keyLabels[i], null, new HashMap<String, PropertyMap>(), null,
								null);
					}
				} else {
					PropertyMap newObject = new PropertyMap(keyLabels[i], null, new HashMap<String, PropertyMap>(),
							null, null);
					internalMap.put(keyLabels[i], newObject);
					parentObject = newObject;
				}
				internalMap = parentObject.getMap();
				if (i == keyLabels.length - 1) {
					if (value instanceof String) {
						parentObject.setValue((String) value);
					} else if (value instanceof Map<?, ?>) {
						parentObject.setValueMap((Map<String, String>) value);
					} else if (value instanceof List<?>) {
						parentObject.setValueList((List<String>) value);
					}
					break;
				}
			}
		}
	}

	public PropertyMap getProperty(String key) {
		PropertyMap value = null;
		if (key != null) {
			String[] keyValues = key.split(separator);
			PropertyMap parentObject = null;
			Map<String, PropertyMap> internalMap = propertyMap;
			for (int i = 0; i < keyValues.length; i++) {
				if (i == keyValues.length - 1) {
					value = internalMap.get(keyValues[i]);
					break;
				}
				parentObject = internalMap.get(keyValues[i]);
				if (parentObject == null) {
					value = null;
					break;
				} else {
					internalMap = parentObject.getMap();
				}
			}
		}
		return value;
	}

	public Map<String, String> getMapValue(Property property) {
		PropertyMap valueObject = getProperty(property.getName());
		if (valueObject != null) {
			return valueObject.getValueMap();
		} else {
			return null;
		}
	}

	public List<String> getListValue(Property property) {
		PropertyMap valueObject = getProperty(property.getName());
		if (valueObject != null) {
			return valueObject.getValueList();
		} else {
			return null;
		}
	}

	public String getPropertyString(Property property) {
		PropertyMap valueObject = getProperty(property.getName());
		if (valueObject != null) {
			return valueObject.getValue();
		} else {
			return null;
		}
	}

	public Integer getPropertyInteger(Property property) {
		PropertyMap valueObject = getProperty(property.getName());
		if (valueObject != null) {
			return Integer.parseInt(valueObject.getValue());
		} else {
			return null;
		}
	}

	public Boolean getPropertyBoolean(Property property) {
		PropertyMap valueObject = getProperty(property.getName());
		if (valueObject != null) {
			return Boolean.parseBoolean(valueObject.getValue());
		} else {
			return null;
		}
	}

	public List<String> getPropertyList(Property property) {
		PropertyMap valueObject = getProperty(property.getName());
		if (valueObject != null && valueObject.getValue() != null && !valueObject.getValue().equals("")) {
			return Arrays.asList(valueObject.getValue().split(listSeparator));
		} else {
			return null;
		}
	}
}
