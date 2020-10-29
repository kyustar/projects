package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.AptInfo;
import com.ssafy.happyhouse.dto.DealHelper;
import com.ssafy.happyhouse.dto.DealInfo;
import com.ssafy.happyhouse.dto.DistrictInfo;
import com.ssafy.happyhouse.dto.GeocodingError;
import com.ssafy.happyhouse.service.DealService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api")
public class DealController {
	
	@Autowired
	private DealService service;
		
    @ApiOperation(value = "키워드로 행정구역 검색", response = List.class)
    @GetMapping("/list/district")
    public ResponseEntity<List<DistrictInfo>> getListDistrictInfoByKeyword(String keyword, Integer pageNum) throws Exception {
    	return new ResponseEntity<List<DistrictInfo>>(service.getListDistrictInfoByKeyword(keyword, pageNum), HttpStatus.OK);
    }
    
    @ApiOperation(value = "행정구역 검색 결과의 레코드수 반환", response = Integer.class)
    @GetMapping("count/district")
    public ResponseEntity<Integer> getCountDistrictInfoByKeyword(String keyword) throws Exception {
    	return new ResponseEntity<Integer>(service.getCountDistrictInfoByKeyword(keyword), HttpStatus.OK);
    }
    
    @ApiOperation(value = "행정구역으로 아파트 검색", response = List.class)
    @GetMapping("/list/apt")
    public ResponseEntity<List<AptInfo>> getListAptInfoByDistrictCode(Long districtCode, Integer pageNum) throws Exception {
    	return new ResponseEntity<List<AptInfo>>(service.getListAptInfoByDistrictCode(districtCode, pageNum), HttpStatus.OK);
    }
    
    @ApiOperation(value = "아파트 검색 결과의 레코드수 반환", response = Integer.class)
    @GetMapping("count/apt")
    public ResponseEntity<Integer> getCountAptInfoByDistrictCode(Long districtCode) throws Exception {
    	return new ResponseEntity<Integer>(service.getCountAptInfoByDistrictCode(districtCode), HttpStatus.OK);
    }
    
    @ApiOperation(value = "아파트id로 거래내역 검색", response = List.class)
    @GetMapping("/list/deal")
    public ResponseEntity<List<DealInfo>> getListDealInfoByAptId(Integer aptId, Integer pageNum) throws Exception {
    	return new ResponseEntity<List<DealInfo>>(service.getListDealInfoByAptId(aptId, pageNum), HttpStatus.OK);
    }
    
    @ApiOperation(value = "거래내역 검색 결과의 레코드수 반환", response = Integer.class)
    @GetMapping("count/deal")
    public ResponseEntity<Integer> getCountDealInfoByAptId(Integer aptId) throws Exception {
    	return new ResponseEntity<Integer>(service.getCountDealInfoByAptId(aptId), HttpStatus.OK);
    }
    
    @ApiOperation(value = "지오코딩 에러로그 추가", response = String.class)
    @PostMapping("/error/geocoding")
    public ResponseEntity<String> addGeocodingError(@RequestBody GeocodingError geocodingError) throws Exception {
    	if(service.addGeocodingError(geocodingError)) {
    		return new ResponseEntity<String>("success", HttpStatus.OK);
    	} else {
    		return new ResponseEntity<String>("fail", HttpStatus.OK);
    	}
    }
    
    @ApiOperation(value = "입력값에 따른 검색어 추천", response = List.class)
    @GetMapping("/list/recommand")
    public ResponseEntity<List<DistrictInfo>> getListRecommandDistrict(String hangul) throws Exception {
    	return new ResponseEntity<List<DistrictInfo>>(service.getListRecommandDistrict(hangul), HttpStatus.OK);
    }
    
    @ApiOperation(value = "아파트id로 거래금액 통계 검색", response = List.class)
    @GetMapping("/util/amount/{aptId}")
    public ResponseEntity<List<DealHelper>> selectListStatisticsByAptId(@PathVariable Integer aptId) throws Exception {
    	return new ResponseEntity<List<DealHelper>>(service.getListStatisticsByAptId(aptId), HttpStatus.OK);
    }
    
}
