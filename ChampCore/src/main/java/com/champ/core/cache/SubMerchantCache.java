package com.champ.core.cache;

import java.util.HashMap;
import java.util.Map;

import com.champ.core.annotation.Cache;
import com.champ.core.entity.SubMerchant;

@Cache(name = "subMerchantCache")
public class SubMerchantCache {

	private Map<String, SubMerchant> codeToSubmerchant = new HashMap<String, SubMerchant>();

	public void addSubMerchant(SubMerchant subMerchant) {
		codeToSubmerchant.put(subMerchant.getCode().toUpperCase(), subMerchant);
	}

	public SubMerchant getSubMerchantByCode(String code) {
		return codeToSubmerchant.get(code.toUpperCase());
	}
}
