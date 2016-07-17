package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.SubMerchant;

public interface ISubMerchantDao {

	public List<SubMerchant> getAllUnaprovedSubMerchants();
	
	public void approveSubmerchant(SubMerchant subMerchant);
	
	public SubMerchant getSubMerchantById(Long id);
	
	public SubMerchant saveOrUpdateSubMerchant(SubMerchant subMerchant);
	
	public boolean checkSubMerchant(String code);
	
	public List<SubMerchant> getAllSubMerchants();
	
	public SubMerchant findSubMerchantByCode(String code);
}
