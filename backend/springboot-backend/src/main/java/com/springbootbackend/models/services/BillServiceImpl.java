package com.springbootbackend.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootbackend.models.dao.IBillDao;
import com.springbootbackend.models.entity.Bill;

@Service
public class BillServiceImpl implements IBillService {
	
	@Autowired
	private IBillDao billDao;

	@Override
	@Transactional(readOnly = true)
	public Bill findById(Long id) {
		return billDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Bill save(Bill bill) {
		return billDao.save(bill);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		billDao.deleteById(id);
	}

}
