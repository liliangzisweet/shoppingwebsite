package com.llz.project.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llz.project.dao.ContentDao;
import com.llz.project.dao.ProductDao;
import com.llz.project.meta.Content;
import com.llz.project.meta.Person;
import com.llz.project.meta.Product;
import com.llz.project.meta.Transaction;
import com.llz.project.service.ImageService;
import com.llz.project.service.ProductService;
import com.llz.project.service.TransactionService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ContentDao contentDao;

	@Autowired
	private ImageService imageService;

	public List<Product> getUserProductList(List<Content> contentUserList,
			List<Content> contentList, int userType) {
		List<Product> productList = new ArrayList<Product>();
		for (Content c : contentList) {
			Product product = new Product();
			product.setId(c.getId());
			product.setTitle(c.getTitle());
			product.setPrice((double)c.getPrice()/100);
			product.setImage("/images/" + c.getId() + ".jpg");
			for (Content cb : contentUserList) {
				if (c.getId() == cb.getId()) {
					if (userType == 0) { // 判断用户类型
						product.setIsBuy("true");
					} else {
						product.setIsSell("true");
					}
				}
			}
			productList.add(product);
		}
		return productList;
	}

	public List<Product> getVisitorProductList(List<Content> contentList) {
		List<Product> productList = new ArrayList<Product>();
		for (Content c : contentList) {
			Product product = new Product();
			product.setId(c.getId());
			product.setTitle(c.getTitle());
			product.setPrice((double)c.getPrice()/100);
			product.setImage("/images/" + c.getId() + ".jpg");
			productList.add(product);
		}
		return productList;

	}

	public Product getProduct(Content content, Person person) {
		Product product = new Product();
		product.setId(content.getId());
		product.setTitle(content.getTitle());
		product.setSummary(content.getSummary());
		product.setPrice((double)content.getPrice()/100);
		product.setImage("/images/" + content.getId() + ".jpg");
		try {
			String detail = new String(content.getText(), "UTF-8");
			product.setDetail(detail);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (person != null) {
			if (person.getUserType() == 0) {
				Transaction trx = transactionService
						.getBuyerTransactionByPersonId(person.getId(),
								content.getId());
				if (trx != null) {
					product.setIsBuy("true");
					product.setBuyPrice((double)trx.getPrice()/100);
				}
			}
			if (person.getUserType() == 1) {
				Transaction trx = transactionService
						.getSellerTransactionByContentId(content.getId());
				if (trx != null) {
					product.setIsSell("true");
				}
			}
		}
		return product;

	}

	public List<Product> getProductByAccount(int personId) {
		List<Product> accountProductList = productDao
				.getProductByAccount(personId);
		for (Product p : accountProductList) {
			p.setPrice((double)p.getPrice()/100);
			p.setImage("/images/" + p.getId() + ".jpg");
		}
		return accountProductList;
	}

	public Product getProductById(int productId) {
		Content content = contentDao.getContentById(productId);
		Product product = new Product();
		product.setId(content.getId());
		product.setTitle(content.getTitle());
		product.setImage("http://localhost:8080/images/" + productId + ".jpg");
		product.setPrice((double)content.getPrice()/100);
		product.setSummary(content.getSummary());
		try {
			String detail = new String(content.getText(), "UTF-8");
			product.setDetail(detail);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return product;
	}

}
