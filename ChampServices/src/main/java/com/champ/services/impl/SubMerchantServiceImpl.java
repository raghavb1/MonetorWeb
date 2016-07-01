package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Merchant;
import com.champ.core.entity.SubMerchant;
import com.champ.data.access.services.IMerchantManagementDao;
import com.champ.data.access.services.ISubMerchantDao;
import com.champ.services.ISubMerchantService;

@Service("subMerchantService")
@Transactional
public class SubMerchantServiceImpl implements ISubMerchantService {

	@Autowired
	IMerchantManagementDao merchantManagementDao;

	@Autowired
	ISubMerchantDao subMerchantDao;

	public List<SubMerchant> getAllUnaprovedSubMerchants() {
		return subMerchantDao.getAllUnaprovedSubMerchants();
	}

	public boolean approveSubmerchant(Long subMerchantId, Long merchantId) {
		SubMerchant subMerchant = subMerchantDao.getSubMerchantById(subMerchantId);
		if (subMerchant != null) {
			Merchant merchant = merchantManagementDao.findMerchantById(merchantId);
			subMerchant.setMerchant(merchant);
			subMerchantDao.approveSubmerchant(subMerchant);
			return true;
		}
		return false;
	}

	public SubMerchant findSubMerchantById(Long id) {
		return subMerchantDao.getSubMerchantById(id);
	}

	public void saveOrUpdateSubMerchant(SubMerchant subMerchant) {
		subMerchantDao.saveOrUpdateSubMerchant(subMerchant);
	}

	public boolean checkSubMerchant(String code) {
		return subMerchantDao.checkSubMerchant(code);
	}

}
