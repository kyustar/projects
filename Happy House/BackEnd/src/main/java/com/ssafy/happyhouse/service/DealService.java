package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.AptInfo;
import com.ssafy.happyhouse.dto.DealHelper;
import com.ssafy.happyhouse.dto.DealInfo;
import com.ssafy.happyhouse.dto.DistrictInfo;
import com.ssafy.happyhouse.dto.GeocodingError;

public interface DealService {
	
	//단어로 검색 요청시
	List<DistrictInfo> getListDistrictInfoByKeyword(String keyword, Integer pageNum);
	//페이징용 전체 쿼리 수
	Integer getCountDistrictInfoByKeyword(String keyword);	
	//위에서 생성된 리스트 중 하나 골랐을 시
	List<AptInfo> getListAptInfoByDistrictCode(Long districtCode, Integer pageNum);
	//페이징용 전체 쿼리 수
	Integer getCountAptInfoByDistrictCode(Long districtCode);	
	//마커중 하나를 골랐을시
	List<DealInfo> getListDealInfoByAptId(Integer aptId, Integer pageNum);
	//페이징용 전체 쿼리 수
	Integer getCountDealInfoByAptId(Integer aptId);
	//지오코딩 에러로그 추가
	boolean addGeocodingError(GeocodingError geocodingError);
	
	//추천검색어 반환
	List<DistrictInfo> getListRecommandDistrict(String hangul);

	//거래금액 통계자료 반환
	List<DealHelper> getListStatisticsByAptId(Integer aptId);
}
