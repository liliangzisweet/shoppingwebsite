package com.llz.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.llz.project.meta.Product;

public interface ProductDao {

	@Select("select a.id,a.title, b.price as buyPrice, b.time as buyTime from content a left join trx b on a.id=b.contentId where personid =#{personId}")
	public List<Product> getProductByAccount(int personId);

}
