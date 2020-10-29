package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Notice;


public interface NoticeService {
//	public List<Notice> selectAllNotice();
	
	List<Notice> getListNotice(Integer pageNum);
	Integer getCountNotice();
	
	public Notice selectNoticeByNo(int no);
	public boolean insertNotice(Notice notice);
	public boolean updateNotice(Notice notice);
	public boolean deleteNotice(int no);
}
