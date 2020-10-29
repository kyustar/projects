package com.ssafy.happyhouse.repo;

import java.util.List;

import com.ssafy.happyhouse.dto.AptInfo;
import com.ssafy.happyhouse.dto.DealHelper;
import com.ssafy.happyhouse.dto.DealInfo;
import com.ssafy.happyhouse.dto.DistrictInfo;
import com.ssafy.happyhouse.dto.GeocodingError;

public interface DealRepo {
	
	//단어로 검색 요청시
	List<DistrictInfo> selectListDistrictInfoByKeyword(String keyword, Integer offset, Integer records);
	//페이징용 전체 쿼리 수
	Integer selectCountDistrictInfoByKeyword(String keyword);
	//위에서 생성된 리스트 중 하나 골랐을 시
	List<AptInfo> selectListAptInfoByDistrictCode(Long districtCode, Integer offset, Integer records);
	//페이징용 전체 쿼리 수
	Integer selectCountAptInfoByDistrictCode(Long districtCode);
	//마커중 하나를 골랐을시
	List<DealInfo> selectListDealInfoByAptId(Integer aptId, Integer offset, Integer records);
	//페이징용 전체 쿼리 수
	Integer selectCountDealInfoByAptId(Integer aptId);
	//지오코딩 에러로그 추가
	int insertGeocodingError(GeocodingError geocodingError);
	
	//추천검색어 반환
	List<DistrictInfo> selectListDistrictInfoByHangul(String hangul);
	
	//거래금액 통계자료 반환
	List<DealHelper> selectListStatisticsByAptId(Integer aptId);
}
