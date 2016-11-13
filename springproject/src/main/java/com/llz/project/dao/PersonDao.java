package com.llz.project.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.llz.project.meta.Person;

public interface PersonDao {
	@Results({ @Result(property = "id", column = "id"),
			@Result(property = "userName", column = "userName"),
			@Result(property = "password", column = "password"),
			@Result(property = "nickName", column = "nickName"),
			@Result(property = "userType", column = "userType") })
	@Select("select * from person where userName=#{userName} and password=#{password}")
	public Person checkUser(@Param("userName") String userName, @Param("password")String password);

}
