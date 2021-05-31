package com.ssafy.dananuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dananuo.dao.ListDAO;
import com.ssafy.dananuo.dto.Goods;

@Service
public class ListService implements IListService {
	
	@Autowired
	ListDAO listDao; 

	@Override
	public List<Goods> getGoodsList() throws Exception {
		return listDao.getGoodsList();
	}

}
