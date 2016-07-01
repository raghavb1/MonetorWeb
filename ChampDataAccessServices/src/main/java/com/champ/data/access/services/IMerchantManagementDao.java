package com.champ.data.access.services;

import java.util.List;

import com.champ.core.entity.Merchant;

public interface IMerchantManagementDao {

	public List<Merchant> getAllMerchants();

	public void saveOrUpdateMerchant(Merchant merchant);

	public Merchant findMerchantById(Long id);

	public void enableMerchant(Long id);

	public void disableMerchant(Long id);

	public boolean checkMerchant(String name);

}
