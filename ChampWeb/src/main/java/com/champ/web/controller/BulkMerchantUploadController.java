package com.champ.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.champ.core.cache.CategoryCache;
import com.champ.core.cache.PropertyMapCache;
import com.champ.core.dto.BulkUploadError;
import com.champ.core.dto.FileBean;
import com.champ.core.entity.Category;
import com.champ.core.entity.Merchant;
import com.champ.core.enums.Property;
import com.champ.core.utility.CacheManager;
import com.champ.services.IMerchantManagementService;

@Controller
@RequestMapping("/Bulk/Merchant")
public class BulkMerchantUploadController {

	@Autowired
	private IMerchantManagementService	merchantManagementService;
	
	private static final Logger LOG = LoggerFactory.getLogger(BulkMerchantUploadController.class);
	
	@RequestMapping("/create")
	public String createFile(ModelMap map) {
		map.put("fileBean", new FileBean());
		map.put("error", false);
		return "MerchantUpload/bulkMerchants";
	}
	
	@RequestMapping("/upload")
	public String uploadFile(@ModelAttribute("fileBean") FileBean file,ModelMap map) {
		MultipartFile postedFile = file.getFile();
		if(postedFile != null && isNameValid(postedFile.getOriginalFilename()))
		{
			InputStream inputStream;
			try {
				inputStream = postedFile.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = "";
				String splitBy = "\t";
				int header = 1;
				List<BulkUploadError> errors = new ArrayList<BulkUploadError>();
				while ((line = bufferedReader.readLine()) != null)
				{
					String [] data = line.split(splitBy);
					if(data.length > 0)
					{
						if(header == 1)
						{
							header = -1;
							continue;
						}
						else{
							Merchant merchant = new Merchant();
							merchant.setName(data[0]);
							merchant.setImageUrl(data[1]);
							Category category = CacheManager.getInstance().getCache(CategoryCache.class).getCategoryByName(data[2]);
							if(category != null){
								merchant.setCategory(category);
								try{
									merchantManagementService.saveOrUpdateMerchant(merchant);	
								}catch (Exception e) {
									LOG.error("Failed to save merchant with name {}",merchant.getName());
									errors.add(new BulkUploadError(merchant.getName(),"Duplicate Entry"));
								}
							}else{
								LOG.info("Category not found for merchant {}",merchant.getName());
								errors.add(new BulkUploadError(merchant.getName(), "Category not found"));
							}
						}
					}
				}
				if(errors.size() > 0){
					map.put("error", true);
					map.put("message","Failed to process complete file");
					map.put("errors", errors);
				}else{
					map.put("error", false);
					map.put("message","File Uploaded Successfully.");
				}
			} catch (IOException e) {
				LOG.error("IO exception occurred.",e);
				map.put("message", "Invalid File Uploaded");
			}catch (Exception e) {
				LOG.error("Exception in reading file",e);
				map.put("message", "Invalid File Uploaded");
			}
		}
		else {
			map.put("message", "Empty File Uploaded.");
		}
		map.put("fileBean", new FileBean());
		return "MerchantUpload/bulkMerchants";
	}
	
	private boolean isNameValid(String name) {
		String namePart[] = name.split("\\.");
		List<String> allowedExtensions = CacheManager.getInstance().getCache(PropertyMapCache.class)
				.getPropertyList(Property.ALLOWED_FILE_EXTENSIONS);
		if (namePart != null && namePart.length == 2 && allowedExtensions != null
				&& allowedExtensions.contains(namePart[1])) {
			return true;
		} else {
			return false;
		}
	}	
}
