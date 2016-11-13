package com.llz.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llz.project.dao.ContentDao;
import com.llz.project.meta.Content;
import com.llz.project.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentDao dao;

	public void insertRecord(Content content) {
		dao.insertRecord(content);
	}

	public void updateRecord(Content content) {
		dao.updateRecord(content);
	}

	public List<Content> getBuyerContentByPersonId(int personId) {
		return dao.getBuyerContentByPersonId(personId);
	}

	public List<Content> getContent() {
		return dao.getContent();
	}

	public List<Content> getSellerContent() {
		return dao.getSellerContent();
	}

	public Content getContentById(int id) {
		Content content = dao.getContentById(id);
		return content;
	}

	public int deleteContentById(int id) {
		return dao.deleteContentById(id);
		
	}

}
