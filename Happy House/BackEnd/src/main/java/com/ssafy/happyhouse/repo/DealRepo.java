package com.ssafy.happyhouse.repo;

import java.util.List;

import com.ssafy.happyhouse.dto.AptInfo;
import com.ssafy.happyhouse.dto.DealHelper;
import com.ssafy.happyhouse.dto.DealInfo;
import com.ssafy.happyhouse.dto.DistrictInfo;
import com.ssafy.happyhouse.dto.GeocodingError;

public interface DealRepo {

	List<DistrictInfo> selectListDistrictInfoByKeyword(String keyword, Integer offset, Integer records);

	Integer selectCountDistrictInfoByKeyword(String keyword);

	List<AptInfo> selectListAptInfoByDistrictCode(Long districtCode, Integer offset, Integer records);

	Integer selectCountAptInfoByDistrictCode(Long districtCode);

	List<DealInfo> selectListDealInfoByAptId(Integer aptId, Integer offset, Integer records);

	Integer selectCountDealInfoByAptId(Integer aptId);

	int insertGeocodingError(GeocodingError geocodingError);

	List<DistrictInfo> selectListDistrictInfoByHangul(String hangul);

	List<DealHelper> selectListStatisticsByAptId(Integer aptId);
}
