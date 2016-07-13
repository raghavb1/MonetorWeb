package com.champ.services;

import java.util.List;

import com.champ.core.entity.SubMerchant;

public interface ISubMerchantService {

	public List<SubMerchant> getAllUnaprovedSubMerchants();

	public boolean approveSubmerchant(Long subMerchantId, Long merchantId);

	public SubMerchant findSubMerchantById(Long id);
	
	public SubMerchant saveOrUpdateSubMerchant(SubMerchant subMerchant);
	
	public boolean checkSubMerchant(String code);
	
	public List<SubMerchant> getAllSubMerchants();
}
