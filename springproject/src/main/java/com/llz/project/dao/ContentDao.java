package com.llz.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.llz.project.meta.Content;

public interface ContentDao {

	@Insert("insert into content(price,title,icon,abstract,text) values(#{price},#{title},#{icon},#{summary},#{text})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insertRecord(Content content);

	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "price", column = "price"),
			@Result(property = "title", column = "title"),
			@Result(property = "icon", column = "icon"),
			@Result(property = "summary", column = "abstract"),
			@Result(property = "text", column = "text") })
	@Update("update content set price=#{price},title=#{title},icon=#{icon},abstract=#{summary},text=#{text} where id =#{id}")
	public void updateRecord(Content content);

	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "price", column = "price"),
			@Result(property = "title", column = "title"),
			@Result(property = "icon", column = "icon"),
			@Result(property = "summary", column = "abstract"),
			@Result(property = "text", column = "text") })
	@Select("select a.* from content a left join trx b on a.id= b.contentId where personId =#{personId}")
	public List<Content> getBuyerContentByPersonId(int personId);

	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "price", column = "price"),
			@Result(property = "title", column = "title"),
			@Result(property = "icon", column = "icon"),
			@Result(property = "summary", column = "abstract"),
			@Result(property = "text", column = "text") })
	@Select("select * from content")
	public List<Content> getContent();

	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "price", column = "price"),
			@Result(property = "title", column = "title"),
			@Result(property = "icon", column = "icon"),
			@Result(property = "summary", column = "abstract"),
			@Result(property = "text", column = "text") })
	@Select("select b.* from trx a left join content b on a.contentId= b.id")
	public List<Content> getSellerContent();

	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "price", column = "price"),
			@Result(property = "title", column = "title"),
			@Result(property = "icon", column = "icon"),
			@Result(property = "summary", column = "abstract"),
			@Result(property = "text", column = "text") })
	@Select("select * from content where id =#{id}")
	public Content getContentById(int id);

	@Select("select * from content a left join trx b on a.id= b.contentId where b.personId =1")
	public Content geBuyertContentByPersonId(int id);
	
	@Delete("delete from content where id=#{id}")
	public int deleteContentById(int id);

}
