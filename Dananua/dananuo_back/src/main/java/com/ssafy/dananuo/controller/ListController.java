package com.ssafy.dananuo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dananuo.dto.BasicResponse;
import com.ssafy.dananuo.dto.Goods;
import com.ssafy.dananuo.service.ListService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/list")
public class ListController {

	@Autowired
	ListService listService;

	@ApiOperation("내부데이터를 가져온다.")
	@GetMapping("")
	public ResponseEntity<?> getInnerData() throws Exception {
		List<Goods> goodsList = listService.getGoodsList();
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		if(goodsList.isEmpty()) {
			result.status = false;
			result.data = "NO_CONTENT";
			response = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
		} else {
			result.status = true;
			result.data = "success";
			result.object = goodsList;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
//
//	@ApiOperation("자신이 포스팅한 게시글을 삭제한다.")
//	@DeleteMapping("/{postno}")
//	public ResponseEntity deletePost(@PathVariable("postno") int postno) throws Exception {
//		ResponseEntity response = null;
//		final BasicResponse result = new BasicResponse();
//		int deleteRes = postService.deletePost(postno);
//		if (deleteRes != 0) {
//			result.status = true;
//			result.data = "success";
//			response = new ResponseEntity<>(result, HttpStatus.OK);
//		}
//		return response;
//	}
//
//	@ApiOperation("좋아요, 싫어요를 삭제한다.")
//	@DeleteMapping("/like/{userno}/{postno}")
//	public ResponseEntity deleteLike(@PathVariable("userno") int userno, @PathVariable("postno") int postno)
//			throws Exception {
//		ResponseEntity response = null;
//		final BasicResponse result = new BasicResponse();
//		postService.deleteLike(userno, postno);
//		result.status = true;
//		result.data = "success";
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
//
//	@ApiOperation("자신이 작성한 게시글의 내용을 수정한다.")
//	@PutMapping("")
//	public ResponseEntity modifyPost(@RequestPart("thumbnail") MultipartFile thumbnail,
//			@RequestParam("postno") int postno, @RequestParam("userno") int userno, @RequestParam("title") String title,
//			@RequestParam("subtitle") String subtitle, @RequestParam("usetags") String[] tags,
//			@RequestParam("content") String content, @RequestParam("save") int save) throws Exception {
//		Post post = new Post();
//		post.setPostno(postno);
//		post.setContent(content);
//		post.setSave(save);
//		post.setSubtitle(subtitle);
//		post.setTags(tags);
//		post.setUserno(userno);
//		post.setTitle(title);
//
//		SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmss");
//		String time1 = format1.format(new Date());
//
//		String filename = "http://i3b208.p.ssafy.io:8080/blog/images/thumbnails/" + userno + time1
//				+ thumbnail.getOriginalFilename();
////		String filename = "http://localhost:8080/blog/images/" + userno + time1 + thumbnail.getOriginalFilename();
//		post.setThumbnail(filename);
//
//		String fileUrl = "/home/ubuntu/images/thumbnails/" + userno + time1 + thumbnail.getOriginalFilename();
//
////		String fileUrl = "/C:/images/" + userno + time1
////				+ thumbnail.getOriginalFilename();
//		File dest = new File(fileUrl);
//		thumbnail.transferTo(dest);
//
//		ResponseEntity response = null;
//		final BasicResponse result = new BasicResponse();
//
//		int modRes = postService.modifyPost(post, tags, save);
//
//		result.status = true;
//		result.data = "success";
//		response = new ResponseEntity<>(result, HttpStatus.OK);
//		return response;
//	}
}
