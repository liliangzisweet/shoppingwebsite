package com.llz.project.service;

import java.util.List;

import com.llz.project.meta.Content;
import com.llz.project.meta.Person;
import com.llz.project.meta.Product;

public interface ProductService {

	public List<Product> getUserProductList(List<Content> contentUserList,
			List<Content> contentList, int userType);

	public List<Product> getVisitorProductList(List<Content> contentList);
	
	public List<Product> getProductByAccount(int personId);
	
	public Product getProductById(int productId);
	
	public Product getProduct(Content content, Person person);
}
