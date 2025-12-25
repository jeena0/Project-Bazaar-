package com.asiya.projectbazar.service;

import org.springframework.web.multipart.MultipartFile;

public interface FUploadService {
	public static final String UPLOAD_DIR="C:\\Users\\HP\\Documents\\images";
//    private static final String UPLOAD_DIR = "src/main/resources/static/images";

	public boolean uploadProductImage(MultipartFile image);
	
}
