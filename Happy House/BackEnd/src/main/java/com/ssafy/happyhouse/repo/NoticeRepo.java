package com.ssafy.happyhouse.repo;

import java.util.List;

import com.ssafy.happyhouse.dto.Notice;

public interface NoticeRepo {

	List<Notice> selectListNotice(Integer offset, Integer records);

	Integer selectCountNotice();

	Notice selectNoticeByNo(int no);

	boolean insertNotice(Notice notice);

	boolean updateNotice(Notice notice);

	boolean deleteNotice(int no);

}