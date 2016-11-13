package com.llz.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llz.project.dao.TransactionDao;
import com.llz.project.meta.Transaction;
import com.llz.project.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionDao dao;

	public Transaction getBuyerTransactionByPersonId(int personId, int contentId) {
		return dao.getBuyerTransactionByPersonId(personId, contentId);
	}

	public Transaction getSellerTransactionByContentId(int contentId) {
		return dao.getSellerTransactionByContentId(contentId);
	}

	public void insertTransaction(Transaction transaction) {
		dao.insertTransaction(transaction);

	}

}
