package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.AptInfo;
import com.ssafy.happyhouse.dto.DealHelper;
import com.ssafy.happyhouse.dto.DealInfo;
import com.ssafy.happyhouse.dto.DistrictInfo;
import com.ssafy.happyhouse.dto.GeocodingError;
import com.ssafy.happyhouse.repo.DealRepo;

@Service
public class DealServiceImpl implements DealService {

	@Autowired
	private DealRepo repo;

	@Override
	public List<DistrictInfo> getListDistrictInfoByKeyword(String keyword, Integer pageNum) {
		return repo.selectListDistrictInfoByKeyword(keyword, (pageNum - 1) * 10, 10);
	}

	@Override
	public Integer getCountDistrictInfoByKeyword(String keyword) {
		return repo.selectCountDistrictInfoByKeyword(keyword);
	}

	@Override
	public List<AptInfo> getListAptInfoByDistrictCode(Long districtCode, Integer pageNum) {
		return repo.selectListAptInfoByDistrictCode(districtCode, (pageNum - 1) * 10, 10);
	}

	@Override
	public Integer getCountAptInfoByDistrictCode(Long districtCode) {
		return repo.selectCountAptInfoByDistrictCode(districtCode);
	}

	@Override
	public List<DealInfo> getListDealInfoByAptId(Integer aptId, Integer pageNum) {
		return repo.selectListDealInfoByAptId(aptId, (pageNum - 1) * 10, 10);
	}

	@Override
	public Integer getCountDealInfoByAptId(Integer aptId) {
		return repo.selectCountDealInfoByAptId(aptId);
	}

	@Override
	public boolean addGeocodingError(GeocodingError geocodingError) {
		return repo.insertGeocodingError(geocodingError) == 1;
	}

	@Override
	public List<DistrictInfo> getListRecommandDistrict(String hangul) {
		return repo.selectListDistrictInfoByHangul(hangul);
	}

	@Override
	public List<DealHelper> getListStatisticsByAptId(Integer aptId) {
		return repo.selectListStatisticsByAptId(aptId);
	}

}
