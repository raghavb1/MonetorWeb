package com.champ.core.utility;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConverterUtils {

	/**
	 * This method is to be used to generate List of a single field present in a
	 * list of DTO
	 **/
	public static List<?> getFieldListFromDTOList(Collection<?> dtoList, String fieldName, Class<?> dtoType)
			throws Exception {
		List<Object> requiredList = new ArrayList<Object>();
		if (dtoList != null && dtoList.size() > 0) {
			PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, dtoType);
			Method getterMethod = dtoType.getMethod(descriptor.getReadMethod().getName());
			for (Object dto : dtoList) {
				Object value = getterMethod.invoke(dto);
				if (value != null) {
					requiredList.add(value);
				}
			}
		}
		return requiredList;
	}

	/**
	 * This method is to be used to generate Map from List of Objects. Key of
	 * map is the key specified in input to this method and value is complete
	 * DTO Object
	 **/
	public static Map<?, ?> getMapFromDTOList(Collection<?> dtoList, String fieldName, Class<?> dtoType)
			throws Exception {
		Map<Object, Object> requiredMap = new HashMap<Object, Object>(0);
		if (dtoList != null && dtoList.size() > 0) {
			PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, dtoType);
			Method getterMethod = dtoType.getMethod(descriptor.getReadMethod().getName());
			for (Object dto : dtoList) {
				Object value = getterMethod.invoke(dto);
				if (value != null) {
					requiredMap.put(value, dto);
				}
			}
		}
		return requiredMap;
	}

}
