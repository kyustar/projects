package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.AptInfo;
import com.ssafy.happyhouse.dto.DealHelper;
import com.ssafy.happyhouse.dto.DealInfo;
import com.ssafy.happyhouse.dto.DistrictInfo;
import com.ssafy.happyhouse.dto.GeocodingError;

public interface DealService {

	List<DistrictInfo> getListDistrictInfoByKeyword(String keyword, Integer pageNum);

	Integer getCountDistrictInfoByKeyword(String keyword);

	List<AptInfo> getListAptInfoByDistrictCode(Long districtCode, Integer pageNum);

	Integer getCountAptInfoByDistrictCode(Long districtCode);

	List<DealInfo> getListDealInfoByAptId(Integer aptId, Integer pageNum);

	Integer getCountDealInfoByAptId(Integer aptId);

	boolean addGeocodingError(GeocodingError geocodingError);

	List<DistrictInfo> getListRecommandDistrict(String hangul);

	List<DealHelper> getListStatisticsByAptId(Integer aptId);
}
