package com.llz.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llz.project.dao.PersonDao;
import com.llz.project.meta.Person;
import com.llz.project.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao dao;
	public Person checkUser(String userName, String password) {	
		return dao.checkUser(userName, password);
	}

}
