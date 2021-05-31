package com.ssafy.dananuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dananuo.dto.Goods;

@Mapper
public interface ListDAO {
	public List<Goods> getGoodsList() throws Exception;
}
