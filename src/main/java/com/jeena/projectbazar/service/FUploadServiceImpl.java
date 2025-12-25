package com.asiya.projectbazar.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FUploadServiceImpl implements FUploadService {
	
	public boolean uploadProductImage(MultipartFile image) {
		File uploadDir= new File(UPLOAD_DIR+"//"+"product_image");
		if(!uploadDir.exists()) {
			uploadDir.mkdir();
			}
		String uploadPath =UPLOAD_DIR+"//"+"product_image"+"//"+image.getOriginalFilename();
		File uploadFile = new File(uploadPath);
		try {
			image.transferTo(uploadFile);
			return true;
		} catch (IllegalStateException | IOException  e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	

}
