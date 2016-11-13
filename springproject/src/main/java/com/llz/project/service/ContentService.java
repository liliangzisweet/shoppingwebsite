package com.llz.project.service;

import java.util.List;

import com.llz.project.meta.Content;

public interface ContentService {

	public void insertRecord(Content content);
	public void updateRecord(Content content);
	public List<Content> getBuyerContentByPersonId(int personId);
	public List<Content> getContent();
	public List<Content> getSellerContent();
	public Content getContentById(int id);
	public int deleteContentById(int id);
}
