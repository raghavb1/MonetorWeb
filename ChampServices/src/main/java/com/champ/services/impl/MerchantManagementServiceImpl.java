package com.champ.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champ.core.entity.Merchant;
import com.champ.data.access.services.IMerchantManagementDao;
import com.champ.services.IMerchantManagementService;

@Service("merchantManagementService")
@Transactional
public class MerchantManagementServiceImpl implements IMerchantManagementService {

	@Autowired
	IMerchantManagementDao merchantManagementDao;

	public List<Merchant> getAllMerchants() {
		return merchantManagementDao.getAllMerchants();
	}

	public void saveOrUpdateMerchant(Merchant merchant) {
		merchantManagementDao.saveOrUpdateMerchant(merchant);
	}

	public Merchant findMerchantById(Long id) {
		return merchantManagementDao.findMerchantById(id);
	}

	public void enableMerchant(Long id) {
		merchantManagementDao.enableMerchant(id);
	}

	public void disableMerchant(Long id) {
		merchantManagementDao.disableMerchant(id);
	}

	public boolean checkMerchant(String name) {
		return merchantManagementDao.checkMerchant(name);
	}

}
