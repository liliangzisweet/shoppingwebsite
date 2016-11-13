package com.llz.project.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.llz.project.meta.Transaction;

public interface TransactionDao {

	@Select("select a.* from trx a left join content b on b.id= a.contentId where a.personId =#{personId} and a.contentId=#{contentId}")
	public Transaction getBuyerTransactionByPersonId(
			@Param("personId") int personId, @Param("contentId") int contentId);

	@Select("select a.* from trx a left join content b on b.id= a.contentId where b.id=#{contentId}")
	public Transaction getSellerTransactionByContentId(int contentId);

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into trx (contentId,personId,price,time) values(#{contentId},#{personId},#{price},#{time})")
	public void insertTransaction(Transaction transaction);

	
}
