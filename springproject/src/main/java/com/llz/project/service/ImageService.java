package com.llz.project.service;

import java.io.InputStream;

public interface ImageService {

	public byte[] imageUrlToByte(String imageUrl) throws Exception;
	public void setImageFile(int id,byte[] imageArray, boolean isEdited);
	public boolean deleteImageFileById(int id);
}
