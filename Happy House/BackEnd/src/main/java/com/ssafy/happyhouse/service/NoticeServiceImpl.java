package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.Notice;
import com.ssafy.happyhouse.repo.NoticeRepo;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRepo repo;

	@Override
	public List<Notice> getListNotice(Integer pageNum) {
		return repo.selectListNotice((pageNum - 1) * 10, 10);
	}

	@Override
	public Integer getCountNotice() {
		return repo.selectCountNotice();
	}

	@Override
	public Notice selectNoticeByNo(int no) {
		return repo.selectNoticeByNo(no);
	}

	@Override
	public boolean insertNotice(Notice notice) {
		return repo.insertNotice(notice);
	}

	@Override
	public boolean updateNotice(Notice notice) {
		return repo.updateNotice(notice);
	}

	@Override
	public boolean deleteNotice(int no) {
		return repo.deleteNotice(no);
	}

}