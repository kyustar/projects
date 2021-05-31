package com.ssafy.dananuo.service;

import java.util.List;

import com.ssafy.dananuo.dto.Goods;

public interface IListService {
	public List<Goods> getGoodsList() throws Exception;
}
