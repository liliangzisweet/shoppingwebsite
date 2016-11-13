package com.llz.project.service;

import com.llz.project.meta.Transaction;

public interface TransactionService {
	public Transaction getBuyerTransactionByPersonId(int personId,int contentId);

	public Transaction getSellerTransactionByContentId(int contentId);
	
	public void insertTransaction(Transaction transaction);

}
