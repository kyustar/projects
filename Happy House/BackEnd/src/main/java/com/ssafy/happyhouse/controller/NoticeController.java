package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.Notice;
import com.ssafy.happyhouse.service.NoticeService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NoticeService noticeService;

//	@ApiOperation(value = "모든 공지사항 정보를 반환한다.", response = List.class)
//	@GetMapping
//	public ResponseEntity<List<Notice>> selectAllNotice() throws Exception {
//		logger.debug("selectNotice - 호출");
//		System.out.println("모든글 반환");
//		return new ResponseEntity<List<Notice>>(noticeService.selectAllNotice(), HttpStatus.OK);
//	}

	@ApiOperation(value = "(pageNum - 1) * 10을 offset으로 하는 10개의 레코드를 반환", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<Notice>> splitList(Integer pageNum) throws Exception {
//		logger.debug("splitList - 호출");
//		System.out.println("게시글  반환");
		return new ResponseEntity<List<Notice>>(noticeService.getListNotice(pageNum), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항의 전체 개수를 반환한다.", response = List.class)
	@GetMapping("/count")
	public ResponseEntity<Integer> allCount() throws Exception {
//		logger.debug("allCount - 호출");
//		System.out.println("게시글  반환");
		return new ResponseEntity<Integer>(noticeService.getCountNotice(), HttpStatus.OK);
	}

	@ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = Notice.class)
	@GetMapping("{no}")
	public ResponseEntity<Notice> selectNoticeByNo(@PathVariable int no) {
		logger.debug("selectNoticeByNo - 호출");

		return new ResponseEntity<Notice>(noticeService.selectNoticeByNo(no), HttpStatus.OK);
	}

	@ApiOperation(value = "새로운 공지사항을 등록한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> insertNotice(@RequestBody Notice notice) {
		logger.debug("insertNotice - 호출");
		if (noticeService.insertNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다.", response = String.class)
	@PutMapping()
	public ResponseEntity<String> updateNotice(@RequestBody Notice notice) {
		logger.debug("updateNotice - 호출");
		logger.debug("" + notice);
		System.out.println(notice + " update!");
		if (noticeService.updateNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 공지사항을 삭제한다.", response = String.class)
	@DeleteMapping("{no}")
	public ResponseEntity<String> deleteNotice(@PathVariable int no) {
		logger.debug("deleteNotice - 호출");
		if (noticeService.deleteNotice(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}